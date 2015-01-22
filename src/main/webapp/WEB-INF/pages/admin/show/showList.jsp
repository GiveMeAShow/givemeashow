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
	<div class="mainContent">
	<div class="row">
		<div class="col-md-8">
			<c:forEach items="${showList}" var="show">
				<div class="row">
					<div class="col-md-2">${show.name}</div>
					<div class="col-md-2"><img src="${show.iconUrl}"/></div>
					<div class="col-md-2">
						<a href="${pageContext.request.contextPath}/admin/show/${show.id}">
							<div class="btn btn-default">
								<span class="glyphicon glyphicon-arrow-right"></span>
							</div>
						</a>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
	</div>
	<script type="text/javascript">
		$("#adminDropDown").addClass("active");
		$("#adminShowListMenu").addClass("active");
	</script>
</body>
</html>