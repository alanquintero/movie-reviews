<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../../../resources/layout/taglib.jsp"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>

<%-- <%@ include file="../../../resources/js/rating.js"%> --%>
<head>
<link rel="stylesheet" type="text/css"
	href="/resources/css/rating.min.css" />
</head>

<script src="../../../resources/js/rating.js"></script>

<div>
	<h1>
		<img src="<c:out value="${movie.image}" />" alt="img"
			style="float: left; width: 100px; height: 120px;">
		<c:out value="${movie.title}" />
		<font color="gray">(<c:out value="${movie.year}" />)
		</font>
	</h1>
	<div id="stars">
		<ul class="c-rating"></ul>
	</div>
	<div id="rating" style="visibility: hidden; display: inline;">
		<c:out value="${movie.rating}" />
	</div>

	<div>
		<!-- Nav tabs -->
		<ul class="nav nav-tabs" role="tablist">
			<li role="presentation" class="active"><a href="#synopsis"
				aria-controls="synopsis" role="tab" data-toggle="tab">Synopsis</a></li>
			<li role="presentation"><a href="#reviews"
				aria-controls="reviews" role="tab" data-toggle="tab">Reviews</a></li>
			<li role="presentation"><a href="#trailer"
				aria-controls="trailer" role="tab" data-toggle="tab">Trailer</a></li>
		</ul>

		<!-- Tab panes -->
		<div class="tab-content">
			<div role="tabpanel" class="tab-pane active" id="synopsis">
				<h2>Synopsis</h2>
				<p>
					<c:out value="${movie.synopsis}" />
				</p>
			</div>
			<div role="tabpanel" class="tab-pane" id="reviews">
				<br>
				<br>
				<!-- Button trigger modal -->
				<security:authorize access="isAuthenticated()">
					<button type="button" class="btn btn-primary btn-lg"
						data-toggle="modal" data-target="#myModal">Add Review</button>
				</security:authorize>

				<form:form commandName="review" cssClass="form-horizontal movieForm">
					<!-- Modal -->
					<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
						aria-labelledby="myModalLabel">
						<div class="modal-dialog" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<h4 class="modal-title" id="myModalLabel">Review</h4>
								</div>
								<div class="modal-body">
									<div class="form-group">
										<input type="text" class="form-control input-sm"
											id="movieTitle" name="movieTitle"
											value="<c:out value="${movie.title}" />" readonly> <br>
										<form:input path="movie.id" type="hidden" value="${movie.id}" />
									</div>
									<div class="form-group">
										<label for="title" class="col-sm-2 control-label">Comment
											Title:</label>
										<div class="col-sm-10">
											<form:input path="title" cssClass="form-control"
												placeholder="Title" />
											<form:errors path="title" />
										</div>
									</div>
									<div class="form-group">
										<label for="comment" class="col-sm-2 control-label">Comment:</label>
										<div class="col-sm-10">
											<form:input path="comment" cssClass="form-control"
												placeholder="Comment" />
											<form:errors path="comment" />
										</div>
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">Close</button>
									<input type="submit" class="btn btn-primary" value="Add Review" />
								</div>
							</div>
						</div>
					</div>
				</form:form>

				<br>
				<br>
				<h2>Reviews</h2>
				<table class="table table-striped table-hover">
					<thead>
						<tr>
							<th>User Name</th>
							<th>Date</th>
							<th>Rating Given</th>
							<th>Title Comment</th>
							<th>Comment</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${movie.reviews}" var="review">
							<tr>
								<td><a
									href="<spring:url value="/users/${review.profile.user.id}.html" />">
										<c:out value="${review.profile.user.name}" />
								</a></td>
								<td><fmt:formatDate type="date"
										value="${review.publishedDate}" /></td>
								<td><c:out value="${review.rating}" /></td>
								<td><c:out value="${review.title}" /></td>
								<td><c:out value="${review.comment}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

			</div>
			<div role="tabpanel" class="tab-pane" id="trailer">
				<iframe width="420" height="315"
					src="<c:out value="${movie.trailer}" />"> </iframe>
			</div>
		</div>
	</div>

</div>

<script type="text/javascript">
	$(document).ready(
			function() {
				$(".movieForm").validate(
						{
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
								$(element).closest('.form-group').removeClass(
										'has-success').addClass('has-error');
							},
							unhighlight : function(element) {
								$(element).closest('.form-group').removeClass(
										'has-error').addClass('has-success');
							}
						});
			})

	var el = document.querySelector('.c-rating');
	var currentRating = document.getElementById('rating').innerHTML;
	var maxRating = 5;
	var callback = function(rating) {
		alert(rating);
	};
	var myRating = rating(el, currentRating, maxRating, callback);
	//myRating.setRating(3);
</script>