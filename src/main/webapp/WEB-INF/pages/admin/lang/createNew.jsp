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
		<form:form method="post" modelAttribute="lang"
			action="${pageContext.request.contextPath}/admin/lang/insert"
			id="standardForm">
			<div class="row">
				<div class="col-md-2">
					<form:label path="language">Lang : </form:label>
				</div>
				<div class="col-md-4">
					<form:input path="language"></form:input>
				</div>
			</div>
			<div class="row-fluid">
				<form:button>Add</form:button>
			</div>
		</form:form>
	</fieldset>

	<script type="text/javascript">
		$("#adminDropDown").addClass("active");
		$("#adminShowNewMenu").addClass("active");

		function updateToolTip() {
			$("#showIcon").attr("title", $("#nameInput").val());
		}

		function updateImageSrc() {
			$("#showIcon").attr("src", $("#iconUrlInput").val());
		}
	</script>
</body>
</html>