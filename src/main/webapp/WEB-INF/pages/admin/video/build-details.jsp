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

	<script type="text/javascript">
		$("#adminDropDown").addClass("active");
		$("#adminShowNewMenu").addClass("active");
	</script>
</body>
</html>