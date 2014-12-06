<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
.error {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
}

.msg {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
}

#login-box {
	width: 300px;
	margin: 100px auto;
	-moz-box-shadow: 0px 0px 5px 0px #656565;
	-webkit-box-shadow: 0px 0px 5px 0px #656565;
	-o-box-shadow: 0px 0px 5px 0px #656565;
	box-shadow: 0px 0px 5px 0px #656565;
	filter: progid:DXImageTransform.Microsoft.Shadow(color=#656565,
		Direction=NaN, Strength=5);
}

#boxTitleBand {
	background-color: #333;
	color: #fff;
	height: 40px;
}

#boxtitle {
	margin-left: 10px;
	line-height: 40px;
}

#boxContent {
	padding: 20px;
	background: #fff;
}

.fieldLabel {
	width: 150px;
}

.fieldInput {
	width: 150px;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register</title>
</head>
<body>
	<div id="login-box">
		<div id="boxTitleBand">
			<h3 id="boxtitle">Givemeashow - Register</h3>
		</div>

		<div id="boxContent">
			<form:form method="POST" action="/register/${inviteCode.inviteCode}" modelAttribute="user">
				<table>
					<tr>
						<td><form:label path="login">Login</form:label></td>
						<td><form:input path="login" /></td>
					</tr>
					<tr>
						<td><form:label path="email">Email</form:label></td>
						<td><form:input path="email" /></td>
					</tr>
					<tr>
						<td><form:label path="password">Password</form:label></td>
						<td><form:input type="password" path="password" /></td>
					</tr>
					<tr>
						<td colspan="2"><input type="submit" value="Submit" /></td>
					</tr>
				</table>
			</form:form>
		</div>
	</div>


</body>
</html>