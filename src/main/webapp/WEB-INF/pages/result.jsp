<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:wrapper title="Score Quiz">
	<jsp:body>
	
		<div class="row">
      		<div class="col s5 push-s4">
        		<div class="card-panel blue">
          			<span class="white-text">${msg }</span>
        		</div>
      		</div>
    	</div>
		
   		<div>
   			<c:forEach items="${results}" var="var">
   		
   				<c:if test="${var.value}">
					<div class="row">
   						<div class="col s10">Question : ${var.key}</div>     
						<div class="col s2 card-panel teal white-text">Correct</div>
   					</div>
				</c:if>
				<c:if test="${!var.value}">
					<div class="row">
   						<div class="col s10">Question : ${var.key}</div>     
						<div class="col s2 card-panel red white-text">Wrong</div>
   					</div>
				</c:if>
			</c:forEach>
		</div>
    </jsp:body>
</t:wrapper>
