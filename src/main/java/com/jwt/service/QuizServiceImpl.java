package com.jwt.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jwt.dao.QuizDAO;
import com.jwt.model.Quiz;
import com.jwt.model.Quiz_questions;

@Service
@Transactional
public class QuizServiceImpl implements QuizService{

	@Autowired
	private QuizDAO qdao;
	
	public void addQuiz(Quiz quiz) {

		qdao.addQuiz(quiz);

	}

	public void addQuizQuestions(Quiz_questions quizQuestions) {
		qdao.addQuizQuestions(quizQuestions);
		
	}

	public List<Quiz> getListQuiz() {
		return qdao.getListQuiz(); 
	}

	public Quiz getQuiz(int id) {
		return qdao.getQuiz(id);
	}

	public List<String> getListQuestions(int id) {
		return qdao.getListQuestions(id);
	}

	public void run_file(String filePath) throws IOException {
		qdao.runFile(filePath);
	}

	public Quiz_questions getQuestionById(int id) {
		return qdao.getQuestionById(id);
	}


	@Override
	public List<Quiz_questions> getListAnswers(int id) {
		return qdao.getListAnswers(id);
	}

	@Override
	public int executeSqlQuery(String query) {
		return qdao.executeSqlQuery(query);
	}



	@Override
	public void deleteQuestion(int questionId) {
			qdao.deleteQuestion(questionId);
	}

	@Override
	public void deleteQuiz(int quizId) {

		List<Quiz_questions> list=qdao.getListAnswers(quizId);
		for(int i=0 ; i<list.size();i++){
			qdao.deleteQuestion(list.get(i).getId());
		}
		qdao.deleteQuizById(quizId);
	}

	

	@Override
	public List<Object[]> executeSelectSqlQuery(String query) {
		return qdao.executeSelectSqlQuery(query);
	}

	@Override
	public void runFile(String filePath) throws IOException {
		qdao.runFile(filePath);
	}

	@Override
	public void addQuizQestions(Quiz_questions quizQuestions) {
		qdao.addQuizQuestions(quizQuestions);
		
	}

	







    

}
