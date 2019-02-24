package hcportal.microservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hcportal.microservice.entity.Employee;

@Repository
public interface EmployeeRespository extends CrudRepository<Employee,Integer>{
	
	

}
