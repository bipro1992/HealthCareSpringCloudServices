package hcportal.microservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hcportal.microservice.dto.EmployeeDto;
import hcportal.microservice.dto.Employer;
import hcportal.microservice.service.EmployeeService;

/**
 * @author biprajeet
 * 
 *         This is employee rest controller class
 *
 */
@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService service; // autowiring employee service

	// GET operation to fetch member details from member Id
	@RequestMapping(method = RequestMethod.GET, value = "/employee/{id}", produces = "application/json")
	public EmployeeDto getEmployee(@PathVariable("id") int employeeId) {
		return this.service.getEmployeeById(employeeId);
	}

	// GET operation to fetch employer object and obtain election amount
	// provided by employer to given member
	@RequestMapping(method = RequestMethod.GET, value = "/employee/election/{id}", produces = "application/json")
	public Employer getElectionAmount(@PathVariable("id") int employeeId) {
		return this.service.getElectionAmount(employeeId);
	}

}
