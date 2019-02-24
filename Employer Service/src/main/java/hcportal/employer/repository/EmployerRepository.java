package hcportal.employer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hcportal.employer.entity.Employer;

@Repository
public interface EmployerRepository extends CrudRepository<Employer, Integer>{

}
