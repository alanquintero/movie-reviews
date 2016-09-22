<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../../../resources/layout/taglib.jsp"%>

<form:form commandName="user" cssClass="form-horizontal">
	
	<c:if test="${param.success eq true }">
		<div class="alert alert-success">Registration Successful!</div>
	</c:if>
	
	<h3 class="text-center">Register <span class="label label-default">Free</span></h3>
	
	<div class="form-group">
		<label for="userName" class="col-sm-2 control-label">User Name:</label>
		<div class="col-sm-10">
			<form:input path="name" cssClass="form-control" placeholder="userName" />
		</div>
	</div>
	
	<div class="form-group">
		<label for="email" class="col-sm-2 control-label">Email:</label>
		<div class="col-sm-10">
			<form:input path="email" cssClass="form-control" placeholder="someone@example.com" />
		</div>
	</div>
	
	<div class="form-group">
		<label for="password" class="col-sm-2 control-label">Password:</label>
		<div class="col-sm-10">
			<form:password path="password" cssClass="form-control" />
		</div>
	</div>
	
	<div class="form-group">
   		<label class="col-md-4 control-label" for="singlebutton"></label>
   		<div class="col-md-4 center-block">
      		<button id="singlebutton" name="singlebutton" class="btn btn-primary center-block">
          		Save
       		</button>
   		</div>  
	</div>
	
</form:form>