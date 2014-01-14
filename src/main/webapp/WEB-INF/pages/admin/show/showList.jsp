<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Complete Show List</title>
</head>
<body>
	<jsp:include page="../../shared/navBar.jsp"></jsp:include>
	<div class="row-fluid">
		<div class="span8">
			<c:forEach items="${showList}" var="show">
				<div class="row-fluid">
					<div class="span2">${show.name}</div>
					<div class="span2">${show.iconUrl}</div>				
				</div>
			</c:forEach>
		</div>
	</div>
	<script type="text/javascript">
		$("#adminDropDown").addClass("active");
		$("#adminShowListMenu").addClass("active");
	</script>
</body>
</html>