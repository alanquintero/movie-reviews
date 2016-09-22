<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../../../resources/layout/taglib.jsp"%>

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
				<th>Rating Given</th>
				<th>Title Comment</th>
				<th>Comment</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${user.profile.review}" var="review">
				<tr>
					<td>
						<a href="<spring:url value="/movie/${review.movie.id}.html" />">
							${review.movie.title} <font color="gray">(${review.movie.year})</font>
						</a>
					</td>
					<td> <fmt:formatDate type="date" value="${review.publishedDate}" /></td>
					<td>${review.rating}</td>
					<td><b>${review.title}</b></td>
					<td>${review.comment}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</div>