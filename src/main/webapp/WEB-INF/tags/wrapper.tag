<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@attribute name="title" required="true" fragment="false" %>
<%@attribute name="header" fragment="true" required="false" %>
<html>
<head>
    <title>Agent SQL  : ${title}</title>
    <jsp:include page="../pages/head.jsp" />
    <jsp:invoke fragment="header"/>
    <style>
        ul.side-nav.fixed li.no-hover {
            background-color: transparent;
        }

        ul.side-nav.fixed li {
            width: 100%;
        }
    </style>
</head>
<body>
<header>
    <nav class="top-nav">
        <div class="container">
            <div class="nav-wrapper"><a class="page-title">${title}</a></div>
        </div>
    </nav>
    <ul id="nav-mobile" class="side-nav fixed" style="width: 240px;">
        <li class="center no-hover"><img class="logo" src="<c:url value="/resources/imgs/logo.png"/>"></li>
        <li class="center no-hover"><h5>Agent SQL</h5></li>
        <li class="waves-effect waves-red"><a href="<c:url value="/"/>">Ajouter Evaluation</a></li>
        <li class="waves-effect waves-red"><a href="<c:url value="/listQuiz"/>">Lister Evaluation</a></li>
        <li class="waves-effect waves-red"><a href="<c:url value="/EditerSupprimerQuiz"/>">Supprimer Evaluation	</a></li>
        <li class="waves-effect waves-red"><a href="<c:url value="/"/>">Mes résultat</a></li>
    </ul>
</header>
<main>
    <div class="container" style="padding-top: 1rem;">
        <jsp:doBody />
    </div>
</main>
</body>
</html>