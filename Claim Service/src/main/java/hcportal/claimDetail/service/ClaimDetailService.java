package hcportal.claimDetail.service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import hcportal.claimDetail.dto.ClaimDto;
import hcportal.claimDetail.dto.Employee;
import hcportal.claimDetail.entity.ClaimDetail;
import hcportal.claimDetail.repository.ClaimDetailRepository;

/**
 * @author biprajeet
 * 
 *         This is claim detail service class providing rest services like
 *         finding claimdetail by its ID or finding list of claims for given
 *         employee Id
 * 
 *         This service class is demonstrating fallback functionality of Hystrix
 *         i.e circuit breaker and caching .
 *
 */
@Service
public class ClaimDetailService {

	private static Logger log = LoggerFactory.getLogger(ClaimDetailService.class);
	private static String restUrl = "employee/";

	@Autowired
	private ClaimDetailRepository repository;

	@Autowired
	private RestTemplate restTemplate;


	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	// To find claim detail from claim detail id. Also caching the retrived
	// object

	@Cacheable("claimDetailById")
	public ClaimDto getClaimDetailById(int claimDetail) {
		try {

			ClaimDetail detail = this.repository.findById(claimDetail).get();

			if (detail != null) {
				ClaimDto dto = new ClaimDto();
				dto.setClaimDetailId(detail.getClaimDetailId());
				dto.setAdjudicated(detail.getAdjudicated());
				dto.setDeniedAmount(detail.getDeniedAmount());
				dto.setEmployeeId(detail.getEmployeeId());
				dto.setPaidAmount(detail.getPaidAmount());
				dto.setRequestedAmount(detail.getRequestedAmount());

				return dto;
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}

		return null;
	}

	// To find list of claim details associated with given member from member Id
	//@HystrixCommand(fallbackMethod = "getClaimDetailsByEmployeeIdFallback", commandProperties = {
		//	@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "180000") })

	public List<ClaimDto> getClaimDetailsByEmployeeId(int employeeId) throws Exception {

		
		List<ClaimDetail> claimDetails = new ArrayList<ClaimDetail>();
		List<ClaimDto> dtos = new ArrayList<ClaimDto>();
		String baseUrl = loadProps();
		String url = baseUrl + ClaimDetailService.restUrl + employeeId;

		Employee employee = this.restTemplate.getForObject(url, Employee.class);

		if (employee != null) {
			// System.out.println(employee.getFirstName());
			claimDetails = this.repository.findByEmployeeId(employeeId);

			for (ClaimDetail detail : claimDetails) {

				ClaimDto dto = new ClaimDto();
				dto.setClaimDetailId(detail.getClaimDetailId());
				dto.setAdjudicated(detail.getAdjudicated());
				dto.setDeniedAmount(detail.getDeniedAmount());
				dto.setEmployeeId(detail.getEmployeeId());
				dto.setPaidAmount(detail.getPaidAmount());
				dto.setRequestedAmount(detail.getRequestedAmount());

				dtos.add(dto);

			}

		}

		return dtos;

	}

	// Fallback method for claim details
	public List<ClaimDto> getClaimDetailsByEmployeeIdFallback(int employeeId) {
		log.info("inside getClaimDetailsByEmployeeIdFallback");
		List<ClaimDto> claimDetails = new ArrayList<ClaimDto>();
		return claimDetails;
	}
	
	
	public String loadProps()
	{
		Properties prop = new Properties();
		InputStream input = null;
		
		try {
			input = new FileInputStream("..\\hcportal.claimDetail\\src\\main\\resources\\config.properties");
			prop.load(input);
			return prop.getProperty("employeehost");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			
		}
		
		return null;
		
	}
}
