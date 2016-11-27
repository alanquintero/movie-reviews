<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../../../resources/layout/taglib.jsp"%>
<%@ include file="../../../resources/jsp/remove.jsp"%>
<%-- <%@ include file="../../../resources/jsp/updateReview.jsp"%> --%>

<script type="text/javascript">
	$(document).ready(
			function() {
				$(".triggerRemove").click(
						function(e) {
							e.preventDefault();
							$("#modalRemove .removeBtn").attr("href",
									$(this).attr("href"));
							$("#removeModalLabel").text("Remove Review");
							$("#confirmationMessage").text(
									"Are you sure to delete this Review?");
							$("#modalRemove").modal();
						})
			});

	function fillForm(movieId, movieTitle, reviewId, reviewTitle, reviewComment) {
		document.getElementById("movie.id").value = movieId;
		document.getElementById("movie.title").value = movieTitle;
		document.getElementById("id").value = reviewId;
		document.getElementById("title").value = reviewTitle;
		document.getElementById("comment").value = reviewComment;
		$("#myModal").modal();
	}

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
</script>

<div>

	<h1>${user.name}</h1>

	<p>${user.profile.quote}</p>

	<br>
	<h2>Reviews</h2>
	<table class="table table-striped table-hover">
		<thead>
			<tr>
				<th>Movie</th>
				<th>Date</th>
				<th>Title Comment</th>
				<th>Comment</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${user.profile.review}" var="review">
				<tr>
					<td><a
						href="<spring:url value="/movie/${review.movie.id}.html" />">
							<c:out value="${review.movie.title}" /> <font color="gray">(<c:out
									value="${review.movie.year}" />)
						</font>
					</a></td>
					<td><fmt:formatDate type="date"
							value="${review.publishedDate}" /></td>
					<td><b><c:out value="${review.title}" /></b></td>
					<td><c:out value="${review.comment}" /></td>
					<td>
						<button type="button" class="btn btn-primary"
							onclick="fillForm('${review.movie.id}','${review.movie.title}', '${review.id}', '${review.title}', '${review.comment}')">Update
							Review</button>
					</td>
					<td><a
						href="<spring:url value="/profile/remove/${review.id}.html" />"
						class="btn btn-danger triggerRemove "> Remove Review </a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

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
						<h4 class="modal-title" id="myModalLabel">Update Review</h4>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<input type="text" class="form-control input-sm" id="movie.title"
								name="movieTitle" readonly> <br>
							<form:input path="movie.id" type="hidden" id="movie.id" />
							<form:input path="id" type="hidden" id="id" />
						</div>
						<div class="form-group">
							<label for="title" class="col-sm-2 control-label">Title:</label>
							<div class="col-sm-10">
								<form:input path="title" cssClass="form-control"
									placeholder="Title" maxlength="50" id="title" />
								<form:errors path="title" />
							</div>
						</div>
						<div class="form-group">
							<label for="comment" class="col-sm-2 control-label">Comment:</label>
							<div class="col-sm-10">
								<form:textarea path="comment" cssClass="form-control"
									placeholder="Comment" rows="3" maxlength="150" id="comment" />
								<form:errors path="comment" />
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<input type="submit" class="btn btn-primary" value="Update Review" />
					</div>
				</div>
			</div>
		</div>
	</form:form>

</div>