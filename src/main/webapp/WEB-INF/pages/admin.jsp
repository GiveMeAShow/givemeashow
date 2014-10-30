<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add a video</title>
        
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/video-js.css'/>" />
<script src="<c:url value='/resources/js/videojs/video.js'/>"></script>
<script src="<c:url value='/resources/js/keyListener.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/angular/lib/angular/angular.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/angular/lib/angular-ui-router.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/angular/lib/angular-animate.js'/>"></script>

    
    
<script type="text/javascript" src="<c:url value='/resources/js/angular/Video/Video.js'/>"></script>  
<script type="text/javascript" src="<c:url value='/resources/js/angular/Video/VideoPlayer.js'/>"></script>  
     

<script type="text/javascript" src="<c:url value='/resources/js/angular/app.js'/>"></script>


</head>
<body>
	<h1>Title : ${title}</h1>
	<h1>Message : ${message}</h1>
 
	<c:url value="/j_spring_security_logout" var="logoutUrl" />
 
	<!-- csrt for log out-->
	<form action="${logoutUrl}" method="post" id="logoutForm">
	  <input type="hidden" 
		name="${_csrf.parameterName}"
		value="${_csrf.token}" />
	</form>
 
	<script>
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>
 
	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<h2>
			Welcome : ${pageContext.request.userPrincipal.name} | <a
				href="javascript:formSubmit()"> Logout</a>
		</h2>
	</c:if>
 
</body>
</html>