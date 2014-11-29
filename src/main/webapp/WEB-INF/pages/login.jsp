<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login page</title>
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
}

#boxTitleBand
{
	background-color: #333;
	border-radius: 0px 10px 0 0px;
	color:#fff;	
	height: 30px;
}


#boxtitle
{
	margin-left: 10px;
}

#boxContent
{
	padding: 20px;
	background: #fff;
	-moz-box-shadow: 0px 0px 5px 0px #656565;
	-webkit-box-shadow: 0px 0px 5px 0px #656565;
	-o-box-shadow: 0px 0px 5px 0px #656565;
	box-shadow: 0px 0px 5px 0px #656565;
	filter:progid:DXImageTransform.Microsoft.Shadow(color=#656565, Direction=NaN, Strength=5);
}
</style>
</head>
<body onload='document.loginForm.username.focus();'>

 	
 	
	<div id="login-box">
		<div id="boxTitleBand">
			<h3 id="boxtitle">GiveMeAShow</h3>
		</div>
		
		<div id="boxContent">
			<%-- <c:if test="${not empty error}">
				<div class="error">${error}</div>
			</c:if>
			<c:if test="${not empty msg}">
				<div class="msg">${msg}</div>
			</c:if> --%>
	 
			<form name='loginForm' action="login" method='POST'>
	 
			<table>
				<tr>
					<td>Login</td>
					<td><input type='text' path='j_username' name='login'></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type='password' path='j_password' name='password' /></td>
				</tr>
				<tr>
					<button type="submit" class="btn btn-default">Submit</button>
				</tr>
			  </table>
	 
			</form>
		</div>
	</div>
		
 
</body>
</html>