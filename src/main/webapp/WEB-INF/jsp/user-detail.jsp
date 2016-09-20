<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/taglib.jsp"%>

<div>

	<h1>${user.name}</h1>

	<p>${user.profile.quote}</p>

	<table class="table table-striped table-hover">
		<thead>
			<tr>
				<th>Movie</th>
				<th>Rating</th>
				<th>Comment</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${user.profile.review}" var="review">
				<tr>
					<td>${review.movie.title}</td>
					<td>${review.rating}</td>
					<td>${review.comment}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</div>