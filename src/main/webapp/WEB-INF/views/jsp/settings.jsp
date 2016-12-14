<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../../../resources/layout/taglib.jsp"%>

<div>

	<form:form commandName="user" cssClass="form-horizontal settingsForm">

		<h3 class="text-center">
		    Settings
		</h3>

		<h2 class="text-center">
		    User Information
		</h2>
		<hr size="6">

		<div class="form-group">
			<label for="userName" class="col-sm-2 control-label">
			    User Name:
			</label>
			<div class="col-sm-10">
				<p>
				    ${user.name}
				</p>
			</div>
		</div>

		<div class="form-group">
			<label for="userEmail" class="col-sm-2 control-label">
			    Email:
			</label>
			<p>
			    ${user.email}
			</p>
			<form:input path="email" type="hidden" value="${user.email}" />
		</div>

		<h2 class="text-center">
		    Change Password
		</h2>
		<hr size="6">

		<div class="form-group">
			<label for="userPassword" class="col-sm-2 control-label">
			    Current Password:
			</label>
			<div class="col-sm-10">
				<form:password path="password" cssClass="form-control" maxlength="30" />
				<form:errors path="password" />
			</div>
		</div>

		<div class="form-group">
			<label for="newPassword" class="col-sm-2 control-label">
			    New Password:
			</label>
			<div class="col-sm-10">
				<form:password path="newPassword" cssClass="form-control" maxlength="30" />
				<form:errors path="newPassword" />
			</div>
		</div>

		<div class="form-group">
			<label for="newPassword" class="col-sm-2 control-label">
			    New Password again:
			</label>
			<div class="col-sm-10">
				<input type="password" name="password_again" id="password_again" class="form-control" maxlength="30" />
			</div>
		</div>

		<c:if test="${success eq true }">
			<div class="alert alert-success">
			    Saved!
			</div>
		</c:if>
		<c:if test="${success eq false }">
			<div class="alert alert-danger">
			    Sorry! Something went wrong. Please try again later.
			</div>
		</c:if>

		<div class="form-group">
			<label class="col-md-4 control-label" for="singlebutton"></label>
			<div class="col-md-4 center-block">
				<button id="singlebutton" name="singlebutton" class="btn btn-primary center-block">
				    Save
				</button>
			</div>
		</div>
		
	</form:form>
	
</div>

<script type="text/javascript">

	jQuery.validator.addMethod("notEqual", function(value, element, param) {
		return this.optional(element) || value != $(param).val();
	}, "New Password must be differente to Current Password");

    $(document).ready( function() {
	    $(".settingsForm").validate({
            rules : {
			    password : {
				    required : true,
					minlength : 6,
					remote : {
					    url : "<spring:url value='/settings/checkpassword.html' />",
						type : "post",
						data : {
						    userEmail : function() { return $("#email").val(); },
						    userPassword : function() { return $("#password").val(); }
						}
					}
                },
			    newPassword : {
			        required : true,
				    minlength : 6,
				    notEqual : "#password"
			    },
			    password_again : {
			        required : true,
				    minlength : 6,
				    notEqual : "#password",
				    equalTo : "#newPassword"
			    }
		    },
		    highlight : function(element) {
		        $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
		    },
		    unhighlight : function(element) {
		        $(element).closest('.form-group').removeClass('has-error').addClass('has-success');
		    },
		    messages : {
		        password : {
			        remote : "Incorrect Password!"
			    }
		    }
	    });
    })

</script>