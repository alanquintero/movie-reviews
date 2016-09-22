<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../../../resources/layout/taglib.jsp"%>

<div>

	<h1>${movie.title} <font color="gray">(${movie.year})</font></h1>
	<p>${movie.rating}</p>
	<br>
	<p>${movie.storyline}</p>
	
	<br><br>
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
					<td>
						<a href="<spring:url value="/users/${review.profile.user.id}.html" />">
							${review.profile.user.name} 
						</a>
					</td>
					<td> <fmt:formatDate type="date" value="${review.publishedDate}" /></td>
					<td>${review.rating}</td>
					<td>${review.title}</td>
					<td>${review.comment}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</div>