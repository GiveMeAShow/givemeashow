<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create a new season</title>
</head>
<body>
	<jsp:include page="../../shared/navBar.jsp"></jsp:include>
	<fieldset>
	<div class="mainContent">
		<form:form method="post" modelAttribute="lang"
			action="${pageContext.request.contextPath}/admin/lang/insert"
			id="standardForm">
			<div class="row">
				<div class="col-md-2">
					<form:label path="language">Language : </form:label>
				</div>
				<div class="col-md-4">
					<form:input path="language"></form:input>
				</div>
			</div>
			<div class="row">
				<div class="col-md-2">
					<form:label path="iso" >iso : </form:label>
				</div>
				<div class="col-md-2">
					<form:input path="iso" id="isoInput"></form:input>
				</div>
			</div>
			<img id="flagImg16" style="height: 16px; width: 16px;"/>
			<img id="flagImg24" style="height: 24px; width: 24px;"/>
			<img id="flagImg32" style="height: 32px; width: 32px;"/>
			<img id="flagImg48" style="height: 48px; width: 48px;"/>
			<img id="flagImg64" style="height: 64px; width: 64px;"/>
			<img id="flagImgicns"/>
			<img id="flagImgico"/>
			<%-- <div class="row">
				<div class="col-md-1">
					<form:label path="flagUrl">FlagUrl : </form:label>
				</div>
				<div class="col-md-1">
					<img id="flagImg" style="width:16px; height: 16px;"></img>
				</div>
				<div class="col-md-4">
					<form:input id="flagUrlInput" path="flagUrl"></form:input>
				</div>
			</div> --%>
			<div class="row-fluid">
				<form:button>Add</form:button>
			</div>
		</form:form>
		</div>
	</fieldset>

	<script type="text/javascript">
		$("#adminDropDown").addClass("active");
		$("#adminLangNewMenu").addClass("active");
		var isoInput = $("#isoInput");
		var flagImg16 = $("#flagImg16");
		var flagImg24 = $("#flagImg24");
		var flagImg32 = $("#flagImg32");
		var flagImg48 = $("#flagImg48");
		var flagImg64 = $("#flagImg64");
		
		$("#isoInput").change(function(e){
			var urlPrefix = "<c:url value='/resources/img/flags/";
			var urlSuffix = ".png'/>";
		
			var url = urlPrefix + "16/" + isoInput.val().toUpperCase() + urlSuffix;
			flagImg16.attr("src", url);
			url = urlPrefix + "24/" + isoInput.val().toUpperCase() + urlSuffix;
			flagImg24.attr("src", url);
			url = urlPrefix + "32/" + isoInput.val().toUpperCase() + urlSuffix;
			flagImg32.attr("src", url);
			url = urlPrefix + "48/" + isoInput.val().toUpperCase() + urlSuffix;
			flagImg48.attr("src", url);
			url = urlPrefix + "64/" + isoInput.val().toUpperCase() + urlSuffix;
			flagImg64.attr("src", url);
		});

		function updateToolTip() {
			$("#showIcon").attr("title", $("#nameInput").val());
		}

		function updateImageSrc() {
			$("#showIcon").attr("src", $("#iconUrlInput").val());
		}
	</script>
</body>
</html>