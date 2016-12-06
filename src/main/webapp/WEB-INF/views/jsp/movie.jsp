<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../../../resources/layout/taglib.jsp"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<link rel="stylesheet" type="text/css" href="/resources/css/rating.min.css" />
<script src="../../../resources/js/rating.js"></script>

<div>

	<h1>
		<img src="<c:out value="${movie.image}" />" alt="img"
			style="float: left; width: 100px; height: 120px;">
		<c:out value="${movie.title}" />
		<font color="gray">
		    (<c:out value="${movie.year}" />)
		</font>
	</h1>
	
	<form:form commandName="user" class="voteForm">
		<div id="stars">
			<ul class="c-rating"></ul>
		</div>
	</form:form>
	
	<div id="rating" style="visibility: hidden; display: inline;">
		<c:out value="${movie.rating}" />
	</div>
	
	<br/>
	<div id="message" style="position: absolute; left: 220px;"></div>
	<br/> <br/>
	
	<div>
		<!-- Nav tabs -->
		<ul class="nav nav-tabs" role="tablist">
			<li role="presentation" class="active">
			    <a href="#synopsis" aria-controls="synopsis" role="tab" data-toggle="tab">
			        Synopsis
			    </a>
			</li>
			<li role="presentation">
			    <a href="#reviews" aria-controls="reviews" role="tab" data-toggle="tab">
			        Reviews
			    </a>
			</li>
			<li role="presentation">
			    <a href="#trailer" aria-controls="trailer" role="tab" data-toggle="tab">
			         Trailer
			    </a>
			</li>
		</ul>

		<!-- Tab panes -->
		<div class="tab-content">
			<div role="tabpanel" class="tab-pane active" id="synopsis">
				<h2>
			        Synopsis
				</h2>
				<p>
					<c:out value="${movie.synopsis}" />
				</p>
			</div>
			<div role="tabpanel" class="tab-pane" id="reviews">
				<br><br>
				<!-- Button trigger modal -->
				<security:authorize access="isAuthenticated()">
					<button type="button" class="btn btn-primary btn-lg"
						data-toggle="modal" data-target="#myModal">
						Add Review
					</button>
				</security:authorize>

				<br><br>
				<h2>
				    Reviews
				</h2>
				
				<table class="table table-striped table-hover">
					<thead>
						<tr>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${movie.reviews}" var="review">
							<tr>
								<td>
								    <b>
								        <c:out value="${review.title}" />
								    </b> 
								    <br> 
								    <fmt:formatDate type="date" value="${review.publishedDate}" /> 
									| By: 
									<a href="<spring:url value="/users/${review.profile.user.code}.html" />">
									    <c:out value="${review.profile.user.name}" />
								    </a> 
								    <br> 
								    <c:out value="${review.comment}" />
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			
			<div role="tabpanel" class="tab-pane" id="trailer">
				<iframe width="420" height="315"
					src="<c:out value="${movie.trailer}" />"> 
				</iframe>
			</div>
		</div>
	</div>
	
	<div>
	    <form:form commandName="review" cssClass="form-horizontal movieForm">
		    <!-- Modal -->
			<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			    <div class="modal-dialog" role="document">
			        <div class="modal-content">
					    <div class="modal-header">
						    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
							    <span aria-hidden="true">
								    &times;
								</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">
							    Add Review
							</h4>
						</div>
						<div class="modal-body">
						    <div class="form-group">
							    <input type="text" class="form-control input-sm" id="movieTitle" name="movieTitle"
								    value="<c:out value="${movie.title}" />" readonly> 
								<br>
								<form:input path="movie.code" type="hidden" value="${movie.code}" id="movieCode" />
								<form:input path="code" type="hidden" id="code" value="" />
							</div>
							<div class="form-group">
								<label for="title" class="col-sm-2 control-label">
								    Title:
								</label>
								<div class="col-sm-10">
							        <form:input path="title" cssClass="form-control"  placeholder="Title" maxlength="50" />
									<form:errors path="title" />
								</div>
							</div>
							<div class="form-group">
							    <label for="comment" class="col-sm-2 control-label">
								    Comment:
								</label>
								<div class="col-sm-10">
									<form:textarea path="comment" cssClass="form-control" placeholder="Comment" 
									    rows="3" maxlength="350" />
									<form:errors path="comment" />
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">
							    Close
							</button>
							<input type="submit" class="btn btn-primary" value="Add Review" />
						</div>
					</div>
				</div>
			</div>
		</form:form>
	</div>
	
</div>

<script type="text/javascript">

	$(document).ready( function() {
	    $(".movieForm").validate({
		    rules : {
			    title : {
				    required : true,
					minlength : 3
				},
				comment : {
					required : true,
					minlength : 5
				}
			},
			highlight : function(element) {
				$(element).closest('.form-group').removeClass('has-success').addClass('has-error');
			},
			unhighlight : function(element) {
				$(element).closest('.form-group').removeClass('has-error').addClass('has-success');
			}
		});
    })

	var el = document.querySelector('.c-rating');
	var currentRating = document.getElementById('rating').innerHTML;
	var maxRating = 5;
	var movieCode = new String(document.getElementById('movieCode').value);

	var callback = function(rating) {
		var code = movieCode;
		$.ajax({
		    url : "<spring:url value='${pageContext.request.contextPath}/rateMovie.html' />",
			data : {
				rating : rating,
				code : code
			},
			success : function(data) {
			    if (data == "/login.html" || data == "") {
			        window.location.href = data;
			    } else if (data != 0) {
			        $("#message").html("<font face='verdana' color='green'>Thanks for your vote!</font>");
				    $("#message").delay(3000).fadeOut("slow");
			    } else {
			        $("#message").html("<font face='verdana' color='red'>" + data + "</font>");
				    $("#message").delay(3000).fadeOut("slow");
			    }
		    }
		});
	};
	
	var myRating = rating(el, currentRating, maxRating, callback);

</script>