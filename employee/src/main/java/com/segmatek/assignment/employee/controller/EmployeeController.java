package com.segmatek.assignment.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.segmatek.assignment.employee.data.model.Employee;
import com.segmatek.assignment.employee.service.EmployeeService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class EmployeeController {
	


	@Autowired
	private EmployeeService employeeService;
	
	@PostMapping(path = "/employee")// to save obj
	public Mono<ResponseEntity<Employee>> insertEmployee(@RequestBody Employee employee) {
		return employeeService.saveEmployee(employee).map(emp -> {
			return new ResponseEntity<>(emp, HttpStatus.CREATED);
		});
	}
	@GetMapping(path = "/employee/{id}")
	public Mono<ResponseEntity<Employee>> getEmployee(@PathVariable Long id) {
		return employeeService.getEmployee(id).map(employee -> {
			return new ResponseEntity<>(employee, HttpStatus.OK);
		});
	}
	@GetMapping(path = "/employees")
	public Flux<Employee> getEmployees() {
		return employeeService.getEmployees();
	}
	
	@PutMapping(path = "/employee/{id}")
	public Mono<ResponseEntity<Employee>> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {

		return employeeService.getEmployee(id)
				.switchIfEmpty(Mono.error(new Exception("Employee: " + id + " not found")))
				.flatMap(foundEmployee -> {
					foundEmployee.setName(employee.getName());
					foundEmployee.setGender(employee.getGender());
					foundEmployee.setPhone(employee.getPhone());

					return employeeService.saveEmployee(foundEmployee).map(emp -> {
						return new ResponseEntity<>(emp, HttpStatus.ACCEPTED);
					});
				});
	}
	@DeleteMapping(path = "/employee")
	public Mono<ResponseEntity<Void>> deleteEmployee(@PathVariable Long id) {

		return employeeService.getEmployee(id)
				.switchIfEmpty(Mono.error(new Exception("Employee: " + id + " not found")))
				.flatMap(foundEmployee -> {


					return employeeService.deleteEmployee(id).map(emp -> {
						return new ResponseEntity<>(emp, HttpStatus.OK);
					});
				});
	}

}
