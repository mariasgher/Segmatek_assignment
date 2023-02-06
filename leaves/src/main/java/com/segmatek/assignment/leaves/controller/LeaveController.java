package com.segmatek.assignment.leaves.controller;

import com.segmatek.assignment.leaves.data.dto.NoOfLeavesDto;
import com.segmatek.assignment.leaves.data.model.Leaves;
import com.segmatek.assignment.leaves.service.LeavesService;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class LeaveController {

    @Autowired
    private LeavesService leavesService;

    @PostMapping(path = "/leave")// to save obj
    public Mono<ResponseEntity<Leaves>> insertLeave(@RequestBody Leaves leaves) {
        return leavesService.saveLeave(leaves).map(leave -> {
            return new ResponseEntity<>(leave, HttpStatus.CREATED);
        });
    }

    @GetMapping(path = "/leave/{id}")
    public Mono<ResponseEntity<Leaves>> getLeave(@PathVariable Long id) {
        return leavesService.getLeave(id).map(leave -> {
            return new ResponseEntity<>(leave, HttpStatus.OK);
        });
    }

    @GetMapping(path = "/leaves")
    public Flux<Leaves> getLeave() {
        return leavesService.getLeaves();
    }

    @PutMapping(path = "/leave/{id}")
    public Mono<ResponseEntity<Leaves>> updateLeave(@PathVariable Long id, @RequestBody Leaves leaves) {

        return leavesService.getLeave(id)
                .switchIfEmpty(Mono.error(new Exception("Leave: " + id + " not found")))
                .flatMap(foundLeave -> {
                    foundLeave.setEid(leaves.getEid());
                    foundLeave.setType(leaves.getType());
                    foundLeave.setDate(leaves.getDate());

                    return leavesService.saveLeave(foundLeave).map(leave -> {
                        return new ResponseEntity<>(leave, HttpStatus.ACCEPTED);
                    });
                });
    }

    @DeleteMapping(path = "/leave")
    public Mono<ResponseEntity<Void>> deleteLeave(@PathVariable Long id) {

        return leavesService.getLeave(id)
                .switchIfEmpty(Mono.error(new Exception("Leave: " + id + " not found")))
                .flatMap(foundLeave -> {
                    return leavesService.deleteLeaves(id).map(leave -> {
                        return new ResponseEntity<>(leave, HttpStatus.OK);
                    });
                });
    }

    @GetMapping(path = "/leave-count")
    public Flux<NoOfLeavesDto> getNoOfLeaves() {
        return leavesService.getNoOfLeaves();
    }
}
