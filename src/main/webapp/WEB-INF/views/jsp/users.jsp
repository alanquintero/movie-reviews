<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ include file="../../../resources/layout/taglib.jsp" %>
<%@ include file="../../../resources/jsp/remove.jsp" %>

<div>

    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th>User Name</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${appUsers}" var="appUser">
            <c:choose>
                <c:when test="${empty appUser.code}">
                    <tr>
                        <td>There are not Users</td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td>
                            <a href="<spring:url value='/appUsers/${appUser.code}.html' />">
                                <c:out value="${appUser.userName}"/>
                            </a>
                        </td>
                        <c:if test="${admin != appUser.userName}">
                            <td>
                                <a href="<spring:url value='/appUsers/remove/${appUser.code}.html' />"
                                   class="btn btn-danger triggerRemove">
                                    Remove User
                                </a>
                            </td>
                        </c:if>
                    </tr>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        </tbody>
    </table>

</div>

<script type="text/javascript">
    $(document).ready(function () {
        $(".triggerRemove").click(function (e) {
            e.preventDefault();
            $("#modalRemove .removeBtn").attr("href", $(this).attr("href"));
            $("#removeModalLabel").text("Remove User");
            $("#confirmationMessage").text("Are you sure to delete this User?");
            $("#modalRemove").modal();
        });
    });
</script>
