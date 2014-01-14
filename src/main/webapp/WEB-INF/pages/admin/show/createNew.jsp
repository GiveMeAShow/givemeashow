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
</head>
<body>
	<jsp:include page="../../shared/navBar.jsp"></jsp:include>
	<fieldset>
		<form:form method="post" action="${pageContext.request.contextPath}/admin/show/addShow" id="standardForm">
			<div class="row-fluid">
				<div class="span1">
					<form:label path="name">Name: </form:label>
				</div>
				<div class="span4">
					<form:input id="nameInput" path="name" onchange="updateToolTip();"/>
				</div>
			</div>
			<div class="row-fluid">
				<div class="span1">
					<form:label path="iconUrl">Icon url: </form:label>
				</div>
				<div class="span4">
					<form:input id="iconUrlInput" path="iconUrl" onchange="updateImageSrc()"/>
				</div>
			</div>
			<div class="row-fluid">
				<img id="showIcon" alt="" height="20px" width="200px" src=""/>
			</div>
			<div class="row-fluid">
				<form:button>Add</form:button>
			</div>
		</form:form>
	</fieldset>

	<script type="text/javascript">
		$("#adminDropDown").addClass("active");
		$("#adminShowNewMenu").addClass("active");
		
		function updateToolTip()
		{
			$("#showIcon").attr("title", $("#nameInput").val());
		}
		
		function updateImageSrc()
		{
			$("#showIcon").attr("src", $("#iconUrlInput").val());
		}
	</script>
</body>
</html>