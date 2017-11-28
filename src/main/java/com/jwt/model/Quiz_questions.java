package com.jwt.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table
public class Quiz_questions {

	@Id
	@GeneratedValue
	int id;
	
	@ManyToOne
	@JoinColumn(name = "id_Quiz", nullable = false)
	private Quiz quiz ;
	
	@Column(length = 3000)
	String question;
	
	@Column(length = 3000)
	String answer;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_Quiz() {
		return quiz.getId_Quiz();
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}
