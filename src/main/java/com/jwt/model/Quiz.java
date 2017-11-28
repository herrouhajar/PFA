package com.jwt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table
public class Quiz {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id_Quiz;
	
	@Column
	String name;
	
	@Column
	String SQL_file;

	@Column
	String description;
	

	public int getId_Quiz() {
		return id_Quiz;
	}
	public void setId_Quiz(int id_Quiz) {
		this.id_Quiz = id_Quiz;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSQL_file() {
		return SQL_file;
	}
	public void setSQL_file(String sQL_file) {
		SQL_file = sQL_file;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
