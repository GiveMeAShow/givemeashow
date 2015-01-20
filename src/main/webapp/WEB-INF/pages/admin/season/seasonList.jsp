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
	<div class="row">
		<div class="col-md-8">
			<c:forEach items="${seasonList}" var="season">
				<div class="row">
				<div class="col-md-1">${season.id}</div>
					<div class="col-md-2">${season.name}</div>
					<div class="col-md-2"><img src="${season.position}"/></div>
					<div class="col-md-2">
						<a href="${pageContext.request.contextPath}/admin/season/${season.id}">
							<div class="btn btn-default">
								<span class="glyphicon glyphicon-arrow-right"></span>
							</div>
						</a>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
	<script type="text/javascript">
		$("#adminDropDown").addClass("active");
		$("#adminSeasonListMenu").addClass("active");
	</script>
</body>
</html>