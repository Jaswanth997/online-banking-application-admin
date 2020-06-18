package com.capgemini.springboot.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sequence")
public class SequenceNumberGeneration {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;



public SequenceNumberGeneration() {
	
}



public long getId() {
	return id;
}



public void setId(long id) {
	this.id = id;
}

}