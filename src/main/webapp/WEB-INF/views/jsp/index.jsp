<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../resources/layout/taglib.jsp"%>

<div>

	<h1>Welcome to Movie Pick</h1>
	
	<h2>Most Voted Movies!</h2>

	<table class="table table-striped table-hover">
		<thead>
			<tr>
				<th>
				    Movie
				</th>
				<th>
				    Number of Votes
				</th>
				<th>
				    Rating
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${movie}" var="movie">
				<c:choose>
					<c:when test="${empty movie.code}">
						<c:out value="${movie.title}" />
						<tr>
							<td>
							    There are not Movies
							</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<td>
							    <img src="<c:out value="${movie.image}" />" alt="img" 
							        style="float: left; width: 100px; height: 120px;"> 
							    <a href="<spring:url value="/movie/${movie.code}.html" />"> 
							        <c:out value="${movie.title}" /> 
							        <font color="gray">
							            (<c:out value="${movie.year}" />)
								    </font>
							    </a>
							</td>
							<td>
							    <c:out value="${movie.vote}" />
							</td>
							<td>
							    <img src="<c:out value="../../../resources/img/${movie.rating}stars.JPG" />" alt="img" 
							        style="float: left;">
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</tbody>
	</table>
	
</div>

