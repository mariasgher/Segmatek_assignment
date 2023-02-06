package com.segmatek.assignment.leaves.consumer;

import com.segmatek.assignment.leaves.data.model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name="employee-service")
public interface EmployeeRestConsumer {

      @GetMapping("/employee/{id}")
      public Mono<ResponseEntity<Employee>> getEmployee(@PathVariable Long id);

}