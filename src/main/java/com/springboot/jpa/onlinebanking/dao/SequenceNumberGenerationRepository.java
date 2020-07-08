package com.springboot.jpa.onlinebanking.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.jpa.onlinebanking.entity.SequenceNumberGeneration;

@Repository
public interface SequenceNumberGenerationRepository extends JpaRepository<SequenceNumberGeneration, Integer> {

}
