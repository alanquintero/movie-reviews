<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ include file="../../../resources/layout/taglib.jsp" %>

<div>

    <h1>${appUser.userName}</h1>

    <p>${appUser.profile.quote}</p>

    <br>
    <h2>Reviews</h2>

    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th>Movie</th>
            <th>Comment</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${appUser.profile.review}" var="review">
            <tr>
                <td>
                    <img src="<c:out value='${review.movie.image}' />" alt="img"
                         style="float: left; width: 100px; height: 120px;">
                    <a href="<spring:url value='/movie/${review.movie.code}.html' />">
                        <c:out value="${review.movie.title}"/>
                        <font color="gray">(<c:out value="${review.movie.year}"/>)</font>
                    </a>
                </td>
                <td>
                    <b><c:out value="${review.title}"/></b><br>
                    <fmt:formatDate type="date" value="${review.publishedDate}"/><br>
                    <c:out value="${review.comment}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>
