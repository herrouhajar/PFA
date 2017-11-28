<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:wrapper title="Liste Quiz">
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
		              <i class="material-icons align-icon">link</i> 
		              <a href="<c:url value="/quiz-page/${quiz.id_Quiz}"/>" class="red-text text-lighten-1">Passer ce quiz</a>
		            </div>
		          </div>
		        </div>
   			</c:forEach>
	      </div>
    </jsp:body>
</t:wrapper>
