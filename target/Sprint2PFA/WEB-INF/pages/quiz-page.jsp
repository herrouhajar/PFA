<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:wrapper title="Passer Quiz">
	
	<jsp:attribute name="header">
	
		<link href="/resources/css/bootstrap.min.css" rel="stylesheet">
	
    	 <style type="text/css">
    		  @CHARSET "UTF-8";
				body {
  					background: #ffffff;
					}

				#time {
  					font-size: 2.5em;
  					padding: 10px;
  					text-align: center;
  					margin: 0 auto;
  					background: #ccc;
					}
     
    	</style>
    	 <script type="text/javascript">
 				 var timer=60;
 				 var min =0;
 				 var sec=0;
 				 
  					function StartTimer(){
	  					min=parseInt(timer/60);
	  					sec=parseInt(timer%60);
	  
	 				 if(timer<1){
		 			 //window.location="/TimerOver.jsp";
		  			//alert("your time's up!");
		 			window.history.go(-1);0
				  }
			document.getElementById("time").innerHTML="<b>Time left: </b>"+min.toString()+":"+sec.toString();
			 timer--;
			 setTimeout(function(){
		 		StartTimer();
				 },1000)
  				}
  					
  			        window.onload = StartTimer;
  
 		 </script>
    </jsp:attribute>
	
	<jsp:body>
		<div class="row" ng-app="myApp">
		
		<!--  -->
		
			<div id="time"></div>
			 
  
    <!--  -->
			<h2>${quiz.getName()}</h2>
			<p>
			<i class="material-icons circle align-icon">description</i>
			<span class="title">${quiz.getDescription()}</span>
			</p>
			
			<form id="takeQuiz" action="take-quiz/${quiz.getId_Quiz()}" method="post" class="col s12">	
	      		 <c:forEach var="question" items="${questions}">
	      		 	<div class="row">
		             	<div class="input-field col s12">
		             	<label for="student_answer">${question}</label>
		             	<textarea id="textarea1" class="materialize-textarea" name="student_answer"></textarea>
		             	</div>
	             	</div>
	            </c:forEach>
	            
   				<button class="waves-effect waves-light btn" type="submit">Envoyer</button>
    		</form>
    	</div>
    	
    	
    </jsp:body>
</t:wrapper>
