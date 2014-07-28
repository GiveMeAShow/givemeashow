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

<script
	src="<c:url value='/resources/video-js-4.6.2/video-js/video.js'/>"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/video-js-4.6.2/video-js/video-js.css'/>" />
<script>
	videojs.options.flash.swf = "http://example.com/path/to/video-js.swf"
</script>
</head>
<body>
	<jsp:include page="../../shared/navBar.jsp"></jsp:include>
	<fieldset>
		<form:form method="post" modelAttribute="video"
			action="${pageContext.request.contextPath}/admin/video/add"
			id="standardForm">
			<div class="row">
				<div class="col-md-2">
					<form:label path="title">Title </form:label>
				</div>
				<div class="col-md-4">
					<form:input id="titleInput" path="title" />
				</div>
			</div>
			<div class="row">
				<div class="col-md-2">
					<form:label path="showId">Show </form:label>
				</div>
				<div class="col-md-4">
					<form:select id="showSelect" path="showId" items="${shows}"
						itemLabel="name" itemValue="id"></form:select>
				</div>
			</div>
			<div class="row">
				<div class="col-md-2">
					<form:label path="seasonId">Season </form:label>
				</div>
				<div class="col-md-4">
					<form:select id="seasonSelect" path="seasonId" items="${shows}"
						itemLabel="name" itemValue="id"></form:select>
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
			<div class="row">
				<div class="col-md-2">
					<form:label path="relativePath">Relative path </form:label>
				</div>
				<div class="col-md-4">
					<form:input id="relativePathInput" path="relativePath" />
				</div>
			</div>
			<div class="row">
				<div class="col-md-2">
					<form:label path="url">Url </form:label>
				</div>
				<div class="col-md-4">
					<form:input id="urlInput" path="url" />
				</div>
			</div>
			<div class="row">
				<div class="col-md-2">
					<form:label path="language">Language </form:label>
				</div>
				<div class="col-md-4">
					<form:select path="language" items="${langList}"  itemLabel="language" itemValue="iso"></form:select>
				</div>
			</div>
			<div class="row">
				<div class="col-md-2">
					<form:label path="url">Url </form:label>
				</div>
				<div class="col-md-4">
					<form:input id="urlInput" path="url" />
				</div>
			</div>
			<div class="row">
				<div class="col-md-2">
					<form:label path="isTransition">Is a transition </form:label>
				</div>
				<div class="col-md-4">
					<form:checkbox path="isTransition" />
				</div>
			</div>
			<div class="row">
				<div class="col-md-2">
					<form:label path="endIntroTime">End intro </form:label>
				</div>
				<div class="col-md-2">
					<form:input path="endIntroTime" id="endIntroInput" />
				</div>
				<div class="col-md-4">
					<button type="button" onclick="setEndIntroTime();">Set</button>
				</div>
			</div>
			<div class="row">
				<div class="col-md-2">
					<form:label path="startOutroTime">Start outro </form:label>
				</div>
				<div class="col-md-2">
					<form:input path="startOutroTime" id="startOutroInput" />
				</div>
				<div class="col-md-4">
					<button type="button" onclick="setStartOutroTime();">Set</button>
				</div>
			</div>
			<div class="row-fluid">
				<form:button>Add</form:button>
			</div>
		</form:form>
	</fieldset>
	<video id="videoClip"
		class="video-js vjs-default-skin videoContainer col-xs-6 col-xs-offset-2"
		controls preload="auto" width="640px" height="360px"> </video>

	<script type="text/javascript">
		$("#adminDropDown").addClass("active");
		$("#adminShowNewMenu").addClass("active");
		var endIntroTimeEl = $("#endIntroInput");
		var startOutroTimeEl = $("#startOutroInput")
		var seasonSelector = $("#seasonSelect");
		var videoPlayer = videojs('videoClip');

		var getSeasonForShowId = function(showId) {
			console.log("showId " + showId);
			$.getJSON(
					'${pageContext.request.contextPath}/webservices/season/getByShowId/'
							+ showId, function(seasonList) {
						seasonSelector.empty();

						for (var i = 0; i < seasonList.length; i++) {
							var option = $("<option></option>").attr("value",
									seasonList[i].id).text(seasonList[i].name);
							seasonSelector.append(option);
						}
					});
		}

		var setEndIntroTime = function() {
			endIntroTimeEl.val(videoPlayer.currentTime());
		}

		var setStartOutroTime = function() {
			startOutroTimeEl.val(videoPlayer.currentTime());
		}

		$("#showSelect").change(function(e) {
			var showId = this.value;
			getSeasonForShowId(showId);
		});

		videoPlayer.src({
			type : "video/webm",
			src : "${video.url}"
		});

		getSeasonForShowId($('#showSelect').find(":selected").val());
		/* videoPlayer.ready(function(){
		    var vp = this;
		    vp.play();
		}) */
	</script>
</body>
</html>