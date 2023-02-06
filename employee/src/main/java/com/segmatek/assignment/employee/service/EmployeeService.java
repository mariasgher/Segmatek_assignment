package com.segmatek.assignment.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.segmatek.assignment.employee.data.model.Employee;
import com.segmatek.assignment.employee.data.repository.EmployeeRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	public Mono<Employee> saveEmployee (Employee employee) {
		return employeeRepository.save(employee);
	}

	public Mono<Employee> getEmployee (Long id) {
		 return employeeRepository.findById(id);
	}
	
	public Flux<Employee> getEmployees () {
		 return employeeRepository.findAll();
	}
	
	public Mono<Void> deleteEmployee(Long id) {
		return employeeRepository.deleteById(id);
	}
}
