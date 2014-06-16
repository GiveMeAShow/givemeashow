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
	<div class="adminContent">
		<fieldset>
			<div class="row">
				<div class="col-md-12">
					<div class="row">
						<div class="col-md-2">${season.name}</div>
						<div class="col-md-2">${season.id}</div>
						<img class="showIcon col-md-5" src="${season.iconUrl}" />
						<a href="${pageContext.request.contextPath}/admin/video/new/${season.showId}/${season.id}">
							<button type="button" class="col-md-3 btn btn-primary">Add en Episode</button>
						</a>
					</div>
				</div>
			</div>
		</fieldset>

	</div>
	<script type="text/javascript">
		$("#adminDropDown").addClass("active");
		$("#adminSeasonListMenu").addClass("active");
	</script>
</body>
</html>