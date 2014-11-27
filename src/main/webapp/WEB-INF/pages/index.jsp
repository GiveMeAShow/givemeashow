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
<script type="text/javascript" src="<c:url value='/resources/js/angular/lib/angular/angular-route.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/angular/lib/angular/angular-sanitize.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/angular/lib/angular/angular-animate.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/angular/lib/angular/angular-resource.js'/>"></script>
 
<script type="text/javascript" src="<c:url value='/resources/js/angular/Show/Show.js'/>"></script>  
<script type="text/javascript" src="<c:url value='/resources/js/angular/Show/ShowChooser.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/angular/Show/ShowModule.js'/>"></script>    
    
<script type="text/javascript" src="<c:url value='/resources/js/angular/Video/Video.js'/>"></script>  
<script type="text/javascript" src="<c:url value='/resources/js/angular/Video/VideoPlayer.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/angular/Video/VideoModule.js'/>"></script> 
    
<script type="text/javascript" src="<c:url value='/resources/js/angular/Index/Index.js'/>"></script> 
     

<script type="text/javascript" src="<c:url value='/resources/js/angular/app.js'/>"></script>


</head>
<body ng-app="givemeashow">
	<jsp:include page="shared/navBar.jsp"></jsp:include>
	
	<div class="mainContent row-fluid span12">
            <div class="row">
                <div id="videoTitle" class="col-xs-6 col-xs-offset-2"></div>
            </div>
            <div ng-view></div>
            
            
            
		<script type="text/javascript" language="javascript">
            var videosHystory = null;
            var path = new Array();
            var index = 0;
        </script>

</body>
</html>