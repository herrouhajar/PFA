package com.jwt.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.jwt.model.Quiz;
import com.jwt.model.Quiz_questions;
import com.jwt.service.QuizService;

@Controller
public class QuizController {

	@Autowired
	public QuizService qservice;

	File serverFile;
	String message;
	String msg;
	private static final String DELETE = "DELETE";
	private static final String DROP = "DROP";


	@RequestMapping(value = "/")
	public ModelAndView addQuiz(ModelAndView model) throws IOException {
		model.setViewName("ajouter-evaluation");
		model.addObject("quizID", 0);
		return model;
	}

	@RequestMapping(value = "/listQuiz")
	public ModelAndView listQuiz(ModelAndView model) throws IOException {
		
		model.addObject("quizs",qservice.getListQuiz());
		model.setViewName("list-quiz");
		return model;
	}

	@RequestMapping(value="/EditerSupprimerQuiz")
	public ModelAndView editerSupprimerQuiz(ModelAndView model)throws IOException{
		
		model.addObject("quizs",qservice.getListQuiz());
		model.setViewName("Editer-Supprimer Evaluation");
		return model;
	}
	
	@RequestMapping(value = "/quiz-page/{id}")
	public ModelAndView quizPage(ModelAndView model, @PathVariable("id") int id) throws IOException {
		
		try{
		qservice.runFile(qservice.getQuiz(id).getSQL_file());
		model.addObject("quiz",qservice.getQuiz(id));
		model.addObject("questions",qservice.getListQuestions(id));

		model.setViewName("quiz-page");
		} catch(SQLGrammarException e){
			model.setViewName("error");
		}
		
		

		return model;
	}

	@RequestMapping(value = "/quiz-page/take-quiz/{id}", method = RequestMethod.POST)
	public ModelAndView testForm(ModelAndView model, @PathVariable("id") int id,
			@RequestParam("student_answer") String[] studentAnswers) throws IOException {

		List<Quiz_questions> answers = qservice.getListAnswers(id);
		Map<Integer, Boolean> questionToResult = new HashMap<>();
		msg="";

		if (answers.size() == studentAnswers.length) {
			for (int i = 0; i < answers.size(); i++) {
				Quiz_questions answer = answers.get(i);
				if (answer.getAnswer().toUpperCase().trim().startsWith("SELECT")
						&& studentAnswers[i].toUpperCase().trim().startsWith("SELECT")) {
					 
					
					try{
						
					List<Object[]> rows1 = qservice.executeSelectSqlQuery(answer.getAnswer());
					List<Object[]> rows2 = qservice.executeSelectSqlQuery(studentAnswers[i]);
					

					boolean answerResult = true;
					if (rows1.size() == rows2.size()) {
						for (int k = 0; k < rows1.size(); k++) {
							if (!answerResult) {
								break;
							}
							Object result1 = rows1.get(k);
							Object result2 = rows2.get(k);
							if (result1 instanceof Object[] && result2 instanceof Object[]) {
								Object[] row1 = (Object[]) result1;
								Object[] row2 = (Object[]) result2;

								if (row1 != null && row2 != null && row1.length == row2.length) {
									for (int l = 0; l < row1.length; l++) {
										if (!row1[l].equals(row2[l])) {
											answerResult = false;
											break;
										}
									}
								}
							} else if (result1 != null && result2 != null && !result1.equals(result2)) {
								answerResult = false;
								break;
							}
						}
					} else {
						msg = "Quiz KO";
						answerResult=false;
					}

					questionToResult.put(answer.getId(), answerResult);

					}
					catch(SQLGrammarException e){
						
						questionToResult.put(answer.getId(), false);

					}
				} else if (studentAnswers[i] == null || studentAnswers[i].isEmpty()){
					questionToResult.put(answer.getId(), false);
				} else if ((answer.getAnswer().toUpperCase().trim().startsWith("UPDATE")
						&& studentAnswers[i].toUpperCase().trim().startsWith("UPDATE"))
						||(answer.getAnswer().toUpperCase().trim().startsWith(DELETE)
								&& studentAnswers[i].toUpperCase().trim().startsWith(DELETE))
						|| (answer.getAnswer().toUpperCase().trim().startsWith("INSERT")
								&& studentAnswers[i].toUpperCase().trim().startsWith("INSERT"))
						||(answer.getAnswer().toUpperCase().trim().startsWith(DROP)
								&& studentAnswers[i].toUpperCase().trim().startsWith(DROP))
						
						){
					
					boolean answerResult=true;

					if((answer.getAnswer().toUpperCase().trim().startsWith(DELETE)
								&& studentAnswers[i].toUpperCase().trim().startsWith(DELETE))
							||(answer.getAnswer().toUpperCase().trim().startsWith(DROP)
									&& studentAnswers[i].toUpperCase().trim().startsWith(DROP))
							){
						
						if(!answer.getAnswer().toUpperCase().trim().equals(studentAnswers[i].toUpperCase().trim())){
							answerResult=false;
						}
						questionToResult.put(answer.getId(), answerResult);

					}
					else{
						
						try{
					int studentRes =qservice.executeSqlQuery(studentAnswers[i]);
					int res = qservice.executeSqlQuery(answer.getAnswer());
										
					if(studentRes!=res){
						answerResult = false;
					}
					    questionToResult.put(answer.getId(), answerResult);
						}
						catch(SQLGrammarException e){
							
							answerResult=false;
							questionToResult.put(answer.getId(), answerResult);
							
						}
					}
				}
				else{
					questionToResult.put(answer.getId(),false);
				}
			}
		} else {
			msg = "number of answers is not equaled";
		}
		
		// Calculate score
		long correctAnswers = questionToResult.entrySet().stream().filter(q -> q.getValue()).count();
		if(correctAnswers>=(questionToResult.size()/2)){
			msg="Quiz passed";
		}
		else{
			msg="Quiz failed";
		}
		model.addObject("msg",msg+" => Your Score is : "+correctAnswers + "/"+questionToResult.size());
		model.setViewName("result");
		
		Map<String, Boolean> results = new HashMap<>();
		for (Map.Entry<Integer, Boolean> entry : questionToResult.entrySet()) {
			String question = qservice.getQuestionById(entry.getKey()).getQuestion();
			results.put(question,entry.getValue());
		}
		model.addObject("results",results);
		return model;
	}

	@RequestMapping(value = "/add_quiz" ,  method = RequestMethod.POST)
    public ModelAndView addQuiz( @RequestParam("name") String name , @RequestParam("description") String description,
    							@RequestParam("SQL_file") MultipartFile file ,
    							@RequestParam("question") String[] questions , @RequestParam("answer") String[] answers) throws IOException {
		
		System.out.println("question="+questions[0]);
		for(int i=0;i<answers.length;i++){
			System.out.println("answer="+answers[i]);

		}

	  	if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				
				serverFile = new File(dir.getAbsolutePath()
						+ File.separator + name);
				
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

			} catch (Exception e) {
				message = "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
				message="You failed to upload " + name + " because the file was empty.";
		}
	  
	  			Quiz quiz=new Quiz();
	  			quiz.setName(name);
	  			quiz.setDescription(description);
	  			quiz.setSQL_file(serverFile.getAbsolutePath());
	  			qservice.addQuiz(quiz);
		
		if(questions.length==answers.length){
			for(int i=0 ; i<questions.length;i++){
				Quiz_questions quizQ = new Quiz_questions();
				quizQ.setQuestion(questions[i]);
				quizQ.setAnswer(answers[i]);
				quizQ.setQuiz(quiz);
				qservice.addQuizQestions(quizQ);
			}
			
		}

	
        return new ModelAndView("redirect:/");  
  }

	
	@RequestMapping(value = "/SupprimerQuiz/{id}")
	public ModelAndView supprimerQuiz(ModelAndView model,@PathVariable("id") int id)throws IOException{
		
		qservice.deleteQuiz(id);
		return new ModelAndView("redirect:/EditerSupprimerQuiz");
		
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String[].class, new StringArrayPropertyEditor(null));
	}

}
