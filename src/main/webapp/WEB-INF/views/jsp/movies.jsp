<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../../../resources/layout/taglib.jsp"%>
<%@ include file="../../../resources/layout/remove.jsp"%>

<script type="text/javascript">
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
</script>

<div>
	<table class="table table-striped table-hover">
		<thead>
			<tr>
				<th>Movie</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${movies}" var="movie">
				<c:choose>
					<c:when test="${movie.id == 0}">
						<c:out value="${movie.title}" />
						<tr>
						   <td>There are not Movies</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<td><a href="<spring:url value="/movie/${movie.id}.html" />">
									<c:out value="${movie.title}" /> <font color="gray">(<c:out
											value="${movie.year}" />)
								</font> <c:out value=" ${movie.rating}" />
							</a></td>
							<td><a
								href="<spring:url value="/movies/remove/${movie.id}.html" />"
								class="btn btn-danger triggerRemove"> Remove Movie </a></td>
						</tr>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</tbody>
	</table>
</div>
