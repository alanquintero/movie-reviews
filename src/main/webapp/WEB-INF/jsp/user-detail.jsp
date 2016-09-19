<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/taglib.jsp" %>

<div>

	<h1>
		${user.name} 
	</h1>
	
	<c:forEach items="${user.blogs}" var="blog">
		<h2>
			${blog.name}
		</h2>
		<h2>
			${blog.url}
		</h2>
		
		<table class="table table-striped table-hover">
			<thead>
				<tr>
					<th>Movie Name</th>
					<th>Rating</th>
					<th>Comment</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${blog.movies}" var="movie">
					<tr>
						<td>${movie.title}</td>
						<td>${movie.rating}</td>
						<td>${movie.comment}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
	</c:forEach>
</div>