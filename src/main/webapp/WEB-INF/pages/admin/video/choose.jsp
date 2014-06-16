<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Choose a video</title>
</head>
<body>
	<jsp:include page="../../shared/navBar.jsp"></jsp:include>
	<div class="adminContent">
        <form:form method="post" modelAttribute="selectedVideo" action="${pageContext.request.contextPath}/admin/video/select">
            <fieldset>
                <div class="row">
                    <div class="col-md-12">
                        <div class="row">
                            <div class="col-md-2">${showId}</div>
                            <div class="col-md-2">${seasonId}</div>
                            <a>
                                <button disabled="true" type="button" class="col-md-3 btn btn-primary">Choose the video to add</button>
                            </a>
                        </div>
                    </div>
                </div>
            </fieldset>
            <div class="row">
                <button type="button" class="col-md-2 btn btn-default leftHeader">Type</button>
                <button type="button" class="col-md-9 btn btn-default middHeader">Name
                    </button>
                    <button type="button" class="col-md-1 btn btn-default rightHeader">Add
                    </button>
            </div>
            <div id="fileChooser">
            </div>
                <form:input type="text" hidden="true" id="videoTitle" path="title"/>
                <form:input type="text" hidden="true" id="videoPath" path="path" />
                <input type="submit" value="Valid"/>
        </form:form>
	</div>
	<script type="text/javascript">
		$("#adminDropDown").addClass("active");
		$("#adminVideoNewMenu").addClass("active");
		var videoTitleInput = $("#videoTitle");
		var videoPathInput = $("#videoPath");
		var fileChooser = $("#fileChooser");

		var fillInputs = function(movieName, moviePath) {
            videoTitleInput.val(movieName);
            videoPathInput.val(moviePath);
		};

        var folderElementAsString = "<div class='row'>"
        								+ "<div class='col-md-2'>" + ":isAVideo" + "</div>"
                                        + "<div class='col-md-9' style=\"cursor: pointer\" onClick=\"requestAndFill('" + ":id" +"')\">" + ":name" + "</div>"
        								+ "<div class='col-md-1'>" + "</div>"
        								+ "</div>"
        								+ "</div>";

        var videoElementAsString = "<div class='row'>"
                + "<div class='col-md-2'>" + ":isAVideo" + "</div>"
                + "<div class='col-md-9'" + " style=\"cursor: pointer\" onClick=\"fillInputs('" + ":name" + "','" + ":path" + "');\">" + ":name" + "</div>"
                + "<div class='col-md-1'>" + "+" + "</div>"
                + "</div>"
                + "</div>";

		var requestAndFill = function (directoryId)
		{
			$.getJSON(
					'${pageContext.request.contextPath}/admin/webservices/video/listVideos/'
							+ directoryId, function(movies) {
						fileChooser.empty();
                        for (var i = 0; i < movies.length; i++) {
                            var movie = movies[i];
                            var element = folderElementAsString;
                            if (movie.avideo)
                            {
                            	element = videoElementAsString;
                            	element = element.replace(":isAVideo", "Video");
                            	element = element.replace(":name", movie.name);
                            	element = element.replace(":name", movie.name);
                                element = element.replace(":path", movie.htmlPath);
                            	
                            }
                            else
                            {
                                
                                element = element.replace(":isAVideo", "Directory");
                                element = element.replace(":name", movie.name);
                                element = element.replace(":id", movie.folderId);
                            }
                            fileChooser.append(element);
                        }
					});
		};
		requestAndFill(1);
	</script>
</body>
</html>