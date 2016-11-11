<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../../../resources/layout/taglib.jsp"%>
<%@ include file="../../../resources/jsp/remove.jsp"%>

<script type="text/javascript">
	$(document).ready(function(){
		$(".triggerRemove").click(function(e){
			e.preventDefault();
			$("#modalRemove .removeBtn").attr("href", $(this).attr("href"));
			$("#removeModalLabel").text("Remove Review");
			$("#confirmationMessage").text("Are you sure to delete this Review?");
			$("#modalRemove").modal();
		})
	});
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
					<td>
						<a href="<spring:url value="/movie/${review.movie.id}.html" />">
							<c:out value="${review.movie.title}" /> <font color="gray">(<c:out value="${review.movie.year}" />)</font>
						</a>
					</td>
					<td> <fmt:formatDate type="date" value="${review.publishedDate}" /></td>
					<td><b><c:out value="${review.title}" /></b></td>
					<td><c:out value="${review.comment}" /></td>
					<td>
						<a href="<spring:url value="/profile/remove/${review.id}.html" />"
						   class="btn btn-danger triggerRemove">
						    Remove Review
						</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</div>