package com.springboot.jpa.onlinebanking.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.jpa.onlinebanking.entity.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer>{

	@Query("select c from Request c where status='pending' or status='inprogress' ")
	Page<Request> pendingInProgressRequests(Pageable pageable);
	
	@Query("select c from Request c where status='Success' or status='Rejected'")
	Page<Request> successRejectedRequests(Pageable pageable);
}
