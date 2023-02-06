package com.segmatek.assignment.leaves.data.repository;

import com.segmatek.assignment.leaves.data.dto.NoOfLeavesDto;
import com.segmatek.assignment.leaves.data.model.Leaves;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface LeavesRepository extends R2dbcRepository<Leaves, Long>{

    @Query("SELECT l.eid, COUNT(l.eid) leaves FROM Leaves l GROUP BY l.eid")
    Flux<NoOfLeavesDto> findNoOfLeaves();
 
}
