<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../../../resources/layout/taglib.jsp"%>

<form:form commandName="user"
	cssClass="form-horizontal registrationForm">

	<c:if test="${param.success eq true }">
		<div class="alert alert-success">Registration Successful!</div>
	</c:if>

	<h3 class="text-center">
		Register <span class="label label-default">Free</span>
	</h3>

	<div class="form-group">
		<label for="userName" class="col-sm-2 control-label">User
			Name:</label>
		<div class="col-sm-10">
			<form:input path="name" cssClass="form-control"
				placeholder="userName" maxlength="30" />
			<form:errors path="name" />
		</div>
	</div>

	<div class="form-group">
		<label for="userEmail" class="col-sm-2 control-label">Email:</label>
		<div class="col-sm-10">
			<form:input path="email" cssClass="form-control"
				placeholder="someone@example.com" maxlength="30" />
			<form:errors path="email" />
		</div>
	</div>

	<div class="form-group">
		<label for="password" class="col-sm-2 control-label">Password:</label>
		<div class="col-sm-10">
			<form:password path="password" cssClass="form-control" maxlength="30" />
			<form:errors path="password" />
		</div>
	</div>

	<div class="form-group">
		<label for="password" class="col-sm-2 control-label">Password
			again:</label>
		<div class="col-sm-10">
			<input type="password" name="password_again" id="password_again"
				class="form-control" maxlength="30" />
		</div>
	</div>

	<div class="form-group">
		<label class="col-md-4 control-label" for="singlebutton"></label>
		<div class="col-md-4 center-block">
			<button id="singlebutton" name="singlebutton"
				class="btn btn-primary center-block">Save</button>
		</div>
	</div>

</form:form>

<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$(".registrationForm")
								.validate(
										{
											rules : {
												name : {
													required : true,
													minlength : 4,
													remote : {
														url : "<spring:url value='/register/checkusername.html' />",
														type : "get",
														data : {
															userName : function() {
																return $(
																		"#name")
																		.val();
															}
														}
													}
												},
												email : {
													required : true,
													email : true,
													remote : {
														url : "<spring:url value='/register/checkuseremail.html' />",
														type : "get",
														data : {
															userEmail : function() {
																return $(
																		"#email")
																		.val();
															}
														}
													}
												},
												password : {
													required : true,
													minlength : 6
												},
												password_again : {
													required : true,
													minlength : 6,
													equalTo : "#password"
												}
											},
											highlight : function(element) {
												$(element).closest(
														'.form-group')
														.removeClass(
																'has-success')
														.addClass('has-error');
											},
											unhighlight : function(element) {
												$(element)
														.closest('.form-group')
														.removeClass(
																'has-error')
														.addClass('has-success');
											},
											messages : {
												name : {
													remote : "Username already exists!"
												},
												email : {
													remote : "Email is already in use!"
												}
											}
										});
					})
</script>