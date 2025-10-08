<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ include file="../../../resources/layout/taglib.jsp"%>

<div>

    <h1>Results</h1>

    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th>Movie</th>
            <th>Rating</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${movie}" var="movie">
            <c:choose>
                <c:when test="${empty movie.code}">
                    <tr>
                        <td colspan="2">There are no movies</td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td>
                            <img src="<c:out value='${movie.image}' />" alt="Movie Image"
                                 style="float: left; width: 100px; height: 120px; margin-right: 10px;">
                            <a href="<spring:url value='/movie/${movie.code}.html' />">
                                <c:out value="${movie.title}" />
                                <font color="gray">(<c:out value="${movie.year}" />)</font>
                            </a>
                        </td>
                        <td>
                            <img src="<c:out value='../../../resources/img/${movie.rating}stars.JPG' />"
                                 alt="${movie.rating} stars" style="float: left;">
                        </td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        </tbody>
    </table>

</div>
