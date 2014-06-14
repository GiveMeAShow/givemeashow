<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create a new show</title>

<script src="<c:url value='/resources/video-js-4.6.2/video-js/video.js'/>"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/video-js-4.6.2/video-js/video-js.css'/>" />
<script>
  videojs.options.flash.swf = "http://example.com/path/to/video-js.swf"
</script>
</head>
<body>
	<jsp:include page="../../shared/navBar.jsp"></jsp:include>
	<fieldset>
		<form:form method="post" modelAttribute="video" action="${pageContext.request.contextPath}/admin/show/addShow" id="standardForm">
			<div class="row">
				<div class="col-md-2">
					<form:label path="title">Title </form:label>
				</div>
				<div class="col-md-4">
					<form:input id="titleInput" path="title"/>
				</div>
			</div>
			<div class="row">
				<div class="col-md-2">
					<form:label path="showId">Show </form:label>
				</div>
				<div class="col-md-4">
					<form:select id="showSelect" path="showId" items="${shows}" itemLabel="name" itemValue="id"></form:select>
				</div>
			</div>
			<div class="row">
				<div class="col-md-2">
					<form:label path="seasonId">Show </form:label>
				</div>
				<div class="col-md-4">
					<form:select id="seasonSelect" path="seasonId" items="${shows}" itemLabel="name" itemValue="id"></form:select>
				</div>
			</div>
			<div class="row">
				<div class="col-md-2">
					<form:label path="relativePath">Relative path </form:label>
				</div>
				<div class="col-md-4">
					<form:input id="relativePathInput" path="relativePath"/>
				</div>
			</div>
            <div class="row">
                <div class="col-md-2">
                    <form:label path="url">Url </form:label>
                </div>
                <div class="col-md-4">
                    <form:input id="urlInput" path="url"/>
                </div>
            </div>
            <div class="row">
                <div class="col-md-2">
                    <form:label path="position">Position </form:label>
                </div>
                <div class="col-md-4">
                    <form:select path="position" items="${positionList}"></form:select>
                </div>
            </div>
		</form:form>
	</fieldset>
	<video id="videoClip" class="video-js vjs-default-skin"
	  controls preload="auto" width="640" height="264"
	  poster="http://video-js.zencoder.com/oceans-clip.png"
	  data-setup='{"example_option":true}'>
	 	<!--  <source src="http://video-js.zencoder.com/oceans-clip.mp4" type='video/mp4' />-->
	 	<source src="" type='video/webm' />
	 	<!-- <source src="http://video-js.zencoder.com/oceans-clip.ogv" type='video/ogg' /> -->
	 	<p class="vjs-no-js">To view this video please enable JavaScript, and consider upgrading to a web browser that <a href="http://videojs.com/html5-video-support/" target="_blank">supports HTML5 video</a></p>
	</video>

	<script type="text/javascript">
		$("#adminDropDown").addClass("active");
		$("#adminShowNewMenu").addClass("active");
		var seasonSelector = $("#seasonSelect");
		
		var getSeasonForShowId = function(showId) 
		{
			console.log("showId " + showId);
			$.getJSON(
					'${pageContext.request.contextPath}/webservices/season/getByShowId/'
							+ showId, function(seasonList) {
						seasonSelector.empty();
						
						for(var i = 0; i < seasonList.length; i++)
						{
							var option = $("<option></option>").attr("value", seasonList[i].id).text(seasonList[i].name);
							seasonSelector.append(option);
						}
						
						console.log(seasonList);
					});
			
			
			
			console.log("selected show " + showId);
		}
		
		$("#showSelect").change(function(e){
			// var optionSelected = ("option:selected", this);
			var showId = this.value;
			getSeasonForShowId(showId);
		});
		
		var videoPlayer = videojs('videoClip');
		videoPlayer.src({type: "video/webm", src: "${video.url}"});
		
		getSeasonForShowId($('#showSelect').find(":selected").val());
		/* videoPlayer.ready(function(){
	        var vp = this;
	        vp.play();
	    }) */
	    
	    
	</script>
</body>
</html>