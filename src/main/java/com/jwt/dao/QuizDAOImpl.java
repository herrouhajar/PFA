package com.jwt.dao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.jwt.model.Quiz;
import com.jwt.model.Quiz_questions;

@Repository
public class QuizDAOImpl implements QuizDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void addQuiz(Quiz quiz) {
		sessionFactory.getCurrentSession().saveOrUpdate(quiz);
	}

	public void addQuizQuestions(Quiz_questions quizQuestions) {
		sessionFactory.getCurrentSession().saveOrUpdate(quizQuestions);

	}

	@SuppressWarnings("unchecked")
	public List<Quiz> getListQuiz() {

		return sessionFactory.getCurrentSession().createQuery("from Quiz").list();
	}

	public Quiz getQuiz(int id) {
		return (Quiz) sessionFactory.getCurrentSession().get(Quiz.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<String> getListQuestions(int id) {
		Query query = sessionFactory.getCurrentSession()
				.createQuery("SELECT question FROM Quiz_questions where id_Quiz=:i");
		query.setParameter("i", id);
		return query.list();
	}

	public void runFile(String filePath) throws IOException {

		// retrieves the resource from class path
		FileInputStream input2 = new FileInputStream(filePath);
		@SuppressWarnings("resource")
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(input2));

		// loads the entire file
		StringBuilder stringBuilder = new StringBuilder();
		String line;
		while ((line = inputReader.readLine()) != null) {

			if (!line.startsWith("--")) {
				stringBuilder.append(line + "\n");
			}
		}
		// Remove multiline comments
		String sqlText = stringBuilder.toString();
		sqlText = sqlText.replaceAll("/\\*.*?\\*/;?", "");

		// splits the commands by semicolon
		String[] commands = sqlText.split(";\\R");

		// Execute commands
		for (String command : commands) {
			final String sql = command != null ? command.replace("\\R", "").trim() : null;
			if (!StringUtils.isEmpty(sql)) {
				sessionFactory.getCurrentSession().doWork(new Work() {
					public void execute(Connection connection) throws SQLException {
						if (sql.trim().toLowerCase().startsWith("create ")) {
							Pattern pattern = Pattern
									.compile("(?i)\\s*CREATE\\s+TABLE(?:\\s+IF\\s+NOT\\s+EXISTS)?\\s+(.+?)\\(");
							Matcher matcher = pattern.matcher(sql);

							if (matcher.find()) {
								String tableName = matcher.group(1);
								connection.prepareStatement("SET FOREIGN_KEY_CHECKS=0;").execute();
								connection.prepareStatement("DROP TABLE IF EXISTS " + tableName + " CASCADE;")
										.execute();
								connection.prepareStatement("SET FOREIGN_KEY_CHECKS=1;").execute();
							}
						}
						connection.prepareStatement(sql).execute();
					}
				});
			}
		}

	}

	@SuppressWarnings("unchecked")
	public List<Object[]> executeSelectSqlQuery(String query) {
		return sessionFactory.getCurrentSession().createSQLQuery(query).list();

	}

	public int executeSqlQuery(String query) {
		return sessionFactory.getCurrentSession().createSQLQuery(query).executeUpdate();
	}

	public Quiz_questions getQuestionById(int id) {
		Query query = sessionFactory.getCurrentSession().createQuery("FROM Quiz_questions where id=:i");
		query.setParameter("i", id);
		return (Quiz_questions) query.list().get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Quiz_questions> getListAnswers(int id) {
		Query query = sessionFactory.getCurrentSession().createQuery("FROM Quiz_questions where id_Quiz=:i");
		query.setParameter("i", id);
		return query.list();
	}


	@Override
	public void deleteQuestion(int questionId) {
		Quiz_questions quiz_question = (Quiz_questions) sessionFactory.getCurrentSession().load(Quiz_questions.class,
				questionId);
		if (null != quiz_question) {
			this.sessionFactory.getCurrentSession().delete(quiz_question);
		}

	}

	@Override
	public void deleteQuizById(int id) {
		Quiz quiz = (Quiz) sessionFactory.getCurrentSession().load(Quiz.class, id);
		if (null != quiz) {
			this.sessionFactory.getCurrentSession().delete(quiz);
		}
	}

}
