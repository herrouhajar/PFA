<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<t:wrapper title="Ajouter Quiz">
	<jsp:attribute name="header">
        <script src="<c:url value="/resources/js/addItem.js"/>"></script>
        <script type="text/javascript">
        function removeLine(i) {
        	 var elem = document.getElementById(i);
        	 elem.parentNode.removeChild(elem);
        	}
	</script>
    </jsp:attribute>
	<jsp:body>
		<div class="row" ng-app="myApp">
	        <form id="addQuiz" class="col s12" action="add_quiz" method="post" enctype="multipart/form-data">	
	        	<div class="row">
	        		<div class="input-field col s12">
	             	<label for="name"  >Nom</label>
	             	<input id="name" type="text" class="validate" name="name" required="" oninvalid="this.setCustomValidity('A name is required')"
  oninput="setCustomValidity('')">
	             	</div>
	             </div>
	             
	           	<div class="file-field input-field">
			      <div class="btn">
			        <span>Fichier SQL</span>
			        <input type="file" name="SQL_file">
			      </div>
			      <div class="file-path-wrapper">
			        <input class="file-path validate" type="text" required data-required-error="the name is required">
			      </div>
			    </div>
	             
	             <div class="row">
	             	<div class="input-field col s12">
	             	<label for="description">Description</label>
	             	<textarea id="textarea1" class="materialize-textarea" name="description" required="" oninvalid="this.setCustomValidity('A Description is required')"
  oninput="setCustomValidity('')"></textarea>
	             	</div>
	             </div>
	             
	    		<div ng-controller="AddInput" class="row">
	    			<span  data-ng-repeat="item in items">
	    				<div class="input-field col s5">
		    				<label for="question">Question</label>
		             		<input id="name" type="text" ng-model="item.question" name="question" required="" oninvalid="this.setCustomValidity('Should at least enter one question')"
  oninput="setCustomValidity('')">
		    			</div>
		     			<div class="input-field col s5">
							<label for="answer">Réponse</label>
		             		<input id="name" type="text" ng-model="item.answer" name="answer" " required="" oninvalid="this.setCustomValidity('Should at least enter one question')"
  oninput="setCustomValidity('')">
						</div>
						<div class="input-field col s1">
							<a ng-show="$last" ng-click="removeItem()"class="btn-floating waves-effect waves-light red"><i class="material-icons">remove</i></a>
						</div>
	    			</span>
	    				<div class="input-field col s1">
							<a ng-click="addNewItem()" class="btn-floating waves-effect waves-light red"> <i class="material-icons">add</i></a>
						</div>
	    		</div>
	
	   			<button class="waves-effect waves-light btn" type="submit" value="Upload">Enregistrer</button>
	    	</form>
		</div>

    </jsp:body>
</t:wrapper>
