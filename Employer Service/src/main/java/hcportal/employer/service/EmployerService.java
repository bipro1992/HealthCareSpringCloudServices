package hcportal.employer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import hcportal.employer.dto.EmployerDto;
import hcportal.employer.entity.Employer;
import hcportal.employer.repository.EmployerRepository;

/**
 * @author biprajeet
 * 
 *         This is service class for employer rest service.
 *
 */
@Service
public class EmployerService {

	private static Logger log = LoggerFactory.getLogger(EmployerService.class);

	@Autowired
	private EmployerRepository repository;

	// To find employer details from its ID
	@Cacheable("getEmployerById")
	public EmployerDto getEmployerById(int employerId) {
		try {

			Employer employer = this.repository.findById(employerId).get();

			if (employer != null) {

				EmployerDto dto = new EmployerDto();
				dto.setEmployerId(employer.getEmployerId());
				dto.setElectionAmount(employer.getElectionAmount());
				dto.setEmployerName(employer.getEmployerName());

				return dto;
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}

		return null;

	}

	// To update employer details for given employer
	@Cacheable("updateEmployer")
	public Employer updateEmployer(int employerId, Employer employer) {
		try {
			Employer employerNew = this.repository.findById(employerId).get();

			if (employer != null) {
				employerNew.setEmployerName(employer.getEmployerName());
				employerNew.setElectionAmount(employer.getElectionAmount());
				return this.repository.save(employerNew);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;

		}

		return null;
	}

}
