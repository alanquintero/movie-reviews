<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../../../resources/layout/taglib.jsp"%>
<%@ include file="../../../resources/jsp/remove.jsp"%>

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
								year : {
									required : true,
									minlength : 4,
									maxlength : 4
								},
								image : {
									required : true,
									minlength : 5
								},
								trailer : {
									required : true,
									minlength : 5
								},
								synopsis : {
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
				$("#message").delay(3000).fadeOut("slow");
			})

	$(document).ready(
			function() {
				$(".triggerRemove").click(
						function(e) {
							e.preventDefault();
							$("#modalRemove .removeBtn").attr("href",
									$(this).attr("href"));
							$("#removeModalLabel").text("Remove Movie");
							$("#confirmationMessage").text(
									"Are you sure to delete this Movie?");
							$("#modalRemove").modal();
						})
			});

	function fillForm(code, movieTitle, movieYear, movieImage, movieTrailer,
			movieSynopsis) {
		document.getElementById("movie.code").value = code;
		document.getElementById("movie.title").value = movieTitle;
		document.getElementById("movie.year").value = movieYear;
		document.getElementById("movie.image").value = movieImage;
		document.getElementById("movie.trailer").value = movieTrailer;
		document.getElementById("movie.synopsis").value = movieSynopsis;
		document.getElementById("submitBtn").value = "Update Movie";
		$("#movieModal").modal();
	}

	function resetModal() {
		document.getElementById("movie.code").value = "";
		document.getElementById("movie.title").value = "";
		document.getElementById("movie.year").value = "";
		document.getElementById("movie.image").value = "";
		document.getElementById("movie.trailer").value = "";
		document.getElementById("movie.synopsis").value = "";
		document.getElementById("submitBtn").value = "Save Movie";
	}
</script>


<div role="tabpanel" class="tab-pane" id="reviews">
	<br> <br>
	<!-- Button trigger modal -->
	<button type="button" class="btn btn-primary btn-lg"
		data-toggle="modal" data-target="#movieModal" onclick="resetModal();">Add
		Movie</button>

	<c:if test="${param.success eq true }">
		<div id="message" class="alert alert-success">Successful!</div>
	</c:if>
	<c:if test="${param.success eq false }">
		<div id="message" class="alert alert-danger">Sorry! Movie
			already exists.</div>
	</c:if>

	<form:form commandName="movie" cssClass="form-horizontal movieForm">
		<!-- Modal -->
		<div class="modal fade" id="movieModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">Movie</h4>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label for="title" class="col-sm-2 control-label">Title:</label>
							<div class="col-sm-10">
								<form:input path="code" type="hidden" id="movie.code" />
								<form:input path="title" id="movie.title"
									cssClass="form-control" placeholder="Title" maxlength="50" />
								<form:errors path="title" />
							</div>
						</div>
						<div class="form-group">
							<label for="year" class="col-sm-2 control-label">Year:</label>
							<div class="col-sm-10">
								<form:input type="number" path="year" id="movie.year"
									cssClass="form-control" placeholder="Year" maxlength="4" />
								<form:errors path="year" />
							</div>
						</div>
						<div class="form-group">
							<label for="image" class="col-sm-2 control-label">Image
								URL:</label>
							<div class="col-sm-10">
								<form:input path="image" id="movie.image"
									cssClass="form-control" placeholder="Image URL" maxlength="200" />
								<form:errors path="image" />
							</div>
						</div>
						<div class="form-group">
							<label for="trailer" class="col-sm-2 control-label">Trailer
								URL:</label>
							<div class="col-sm-10">
								<form:input path="trailer" id="movie.trailer"
									cssClass="form-control" placeholder="Trailer URL"
									maxlength="200" />
								<form:errors path="trailer" />
							</div>
						</div>
						<div class="form-group">
							<label for="synopsis" class="col-sm-2 control-label">Synopsis:</label>
							<div class="col-sm-10">
								<form:textarea path="synopsis" id="movie.synopsis"
									cssClass="form-control" placeholder="Synopsis" rows="3"
									maxlength="350" />
								<form:errors path="synopsis" />
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<input id="submitBtn" type="submit" class="btn btn-primary"
							value="Add Movie" />
					</div>
				</div>
			</div>
		</div>
	</form:form>
</div>


<div>
	<table class="table table-striped table-hover">
		<thead>
			<tr>
				<th>Movie</th>
				<th>Rating</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${movies}" var="movie">
				<c:choose>
					<c:when test="${empty movie.code}">
						<c:out value="${movie.title}" />
						<tr>
							<td>There are not Movies</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<td><a
								href="<spring:url value="/movie/${movie.code}.html" />"> <c:out
										value="${movie.title}" /> <font color="gray">(<c:out
											value="${movie.year}" />)
								</font>
							</a></td>
							<td><c:out value=" ${movie.rating}" /></td>
							<td>
								<button type="button" class="btn btn-primary"
									onclick="fillForm('${movie.code}', '${movie.title}', '${movie.year}', '${movie.image}', '${movie.trailer}', '${movie.synopsis}')">Update
									Movie</button>
							</td>
							<td><a
								href="<spring:url value="/movies/remove/${movie.code}.html" />"
								class="btn btn-danger triggerRemove"> Remove Movie </a></td>
						</tr>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</tbody>
	</table>
</div>