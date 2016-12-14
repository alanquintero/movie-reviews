<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../../../resources/layout/taglib.jsp"%>

<head>
    <link rel="stylesheet" type="text/css" href="resources/css/signin.css" />
</head>

<div class="container">

	<spring:url value="/login" var="loginUrl" />

	<form class="form-signin" action="${loginUrl}" method="POST">
	
		<h2 class="form-signin-heading">Please sign in</h2>
		<label for="inputUserName" class="sr-only">
		    User Name
		</label> 
		<input type="text" name="username" class="form-control"
			placeholder="User Name" maxlength="30" autocomplete="off" required autofocus> 
		
		<label for="inputPassword" class="sr-only">
		    Password
		</label> 
		
		<input type="password" name="password" class="form-control"
			placeholder="Password" maxlength="30" required>
			
		<button class="btn btn-lg btn-primary btn-block" type="submit">
		    Sign in
		 </button>
		 
	</form>

</div>