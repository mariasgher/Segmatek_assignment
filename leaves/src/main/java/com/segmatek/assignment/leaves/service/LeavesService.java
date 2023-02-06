package com.segmatek.assignment.leaves.service;


import com.segmatek.assignment.leaves.consumer.EmployeeRestConsumer;
import com.segmatek.assignment.leaves.data.dto.NoOfLeavesDto;
import com.segmatek.assignment.leaves.data.model.Leaves;
import com.segmatek.assignment.leaves.data.repository.LeavesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class LeavesService {

	@Autowired
	private LeavesRepository leavesRepository;

	@Autowired
	private EmployeeRestConsumer employeeRestConsumer;

	public Mono<Leaves> saveLeave (Leaves leaves) {
		return leavesRepository.save(leaves);
	}


	public Mono<Leaves> getLeave (Long id) {
		 return leavesRepository.findById(id);
	}
	
	public Flux<Leaves> getLeaves () {
		 return leavesRepository.findAll();

	}
	
	public Mono<Void> deleteLeaves(Long id) {
		return leavesRepository.deleteById(id);
	}

	public Flux<NoOfLeavesDto> getNoOfLeaves(){

	Flux<NoOfLeavesDto> result = leavesRepository.findNoOfLeaves();
	result.map(noOfLeavesDto -> {
		employeeRestConsumer.getEmployee(noOfLeavesDto.getEid()).map(employee -> {
			noOfLeavesDto.setEmpName(employee.getBody().getName());
			return noOfLeavesDto;
		});
		return noOfLeavesDto;
	}).subscribe();
		return result;
	}
}
