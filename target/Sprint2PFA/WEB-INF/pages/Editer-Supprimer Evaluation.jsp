<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

    
    
<t:wrapper title="Editer/Supprimer Quiz">
	<jsp:body>
		<div class="row">
			<c:forEach var="quiz" items="${quizs}">
      			<div class="col s12 m6">
		          <div class="card light-blue lighten-4">
		            <div class="card-content">
		              <span class="card-title">${quiz.name}</span>
		              <p>
						<i class="material-icons circle align-icon">description</i>
						<span class="title">${quiz.description}</span>
					  </p>
		            </div>
		            <div class="card-action">
		        	  <a href="<c:url value="/SupprimerQuiz/${quiz.id_Quiz}"/>" class="waves-effect waves-light btn">Supprimer</a>
		            </div>
		          </div>
		        </div>
   			</c:forEach>	
	    </div>
    </jsp:body>
</t:wrapper>