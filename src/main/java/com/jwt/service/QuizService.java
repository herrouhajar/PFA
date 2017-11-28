package com.jwt.service;

import java.io.IOException;
import java.util.List;

import com.jwt.model.Quiz;
import com.jwt.model.Quiz_questions;

public interface QuizService {

	public void addQuiz(Quiz quiz);
	public void deleteQuestion(int questionId);
	public void addQuizQestions(Quiz_questions quizQuestions);
	public void deleteQuiz(int quizId);

	public List<Quiz> getListQuiz();
	public Quiz getQuiz(int id);
	public List<String> getListQuestions(int id);
	public List<Quiz_questions> getListAnswers(int id);
	public Quiz_questions getQuestionById(int id);

	
	public List<Object[]> executeSelectSqlQuery(String query);
	
	public void runFile(String filePath)throws IOException;
	public int executeSqlQuery(String query);
	

	
}
