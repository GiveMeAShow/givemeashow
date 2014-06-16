<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<jsp:include page="inclusions.jsp"></jsp:include>

<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container-fluid">
			<a class="navbar-brand" href="<c:url value="/"/>">GiveMeAShow</a>
			<div class="collapse navbar-collapse">
				<sec:authorize ifNotGranted="ROLE_USER">
					<p class="navbar-text pull-right">
						<a href="<c:url value="/login"/>" class="navbar-link">Log In</a>
					</p>
				</sec:authorize>
				<ul class="nav navbar-nav">
					<li><a href="<c:url value="/" />">Video</a></li>
					<li><a href="<c:url value="/"/>">Controls</a></li>
					<sec:authorize access="hasRole('ROLE_USER')">
						<li><a href="<c:url value="/Details"/>">My Account</a></li>
					</sec:authorize>
					<li class="dropdown" id="adminDropDown">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#">Admin</a>
						<ul class="dropdown-menu">
							<li id="adminShowListMenu"><a href="<c:url value="/admin/show/list"/>">Show List</a></li>
							<li id="adminShowNewMenu"><a href="<c:url value="/admin/show/new"/>">New Show</a></li>
							<li id="adminSeasonListMenu"><a href="<c:url value="/admin/season/list/"/>">Season List</a></li>
							<li id="adminSeasonNewMenu"><a href="<c:url value="/admin/season/new"/>">New Season</a></li>
                            <li id="adminVideoNewMenu"><a href="<c:url value="/admin/video/new"/>">New Video</a></li>
                            <li id="adminLangNewMenu"><a href="<c:url value="/admin/lang/new"/>">New Language</a></li>
						</ul></li>
				</ul>

				<sec:authorize access="hasRole('ROLE_USER')">
					<ul class="nav pull-right">
						<li><a href="<c:url value="/j_spring_security_logout"/>">Se déconnecter</a></li>
					</ul>
					<p class="navbar-text pull-right">
						<a><sec:authentication property="principal.username" /></a>
					</p>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_ADMIN')">
				<ul class="nav pull-right">
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#"> <img id="adminIcon"
							class="nav "  src="<c:url value='/resources/img/admin_icon.png'/>">
					</a>
						<ul class="dropdown-menu">
							<li><a href="<c:url value="/admin/createMovie"/>">Ajouter un film</a></li>
							<li><a href="<c:url value="/admin/showAll"/>">Liste complète</a></li>
						</ul></li>
				</ul>
				</sec:authorize>
			</div>
		</div>
	</div>
</div>
