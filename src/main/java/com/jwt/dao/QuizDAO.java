package com.jwt.dao;

import java.io.IOException;
import java.util.List;

import com.jwt.model.Quiz;
import com.jwt.model.Quiz_questions;

public interface QuizDAO {
	
	public void addQuiz(Quiz quiz);
	public void deleteQuestion(int questionId);
	public void deleteQuizById(int id);
	public void addQuizQuestions(Quiz_questions quizQuestions);
	
	
	public List<Quiz> getListQuiz();
	public Quiz getQuiz(int id);
	public List<String> getListQuestions(int id);
	public List<Quiz_questions> getListAnswers(int id);
	public Quiz_questions getQuestionById(int id);
	
	public List<Object[]> executeSelectSqlQuery(String query);

	public void runFile(String filePath)throws IOException;
	public int executeSqlQuery(String query);

	

}
