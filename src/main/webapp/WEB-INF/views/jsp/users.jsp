<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../../../resources/layout/taglib.jsp"%>
<%@ include file="../../../resources/jsp/remove.jsp"%>

<div>

	<table class="table table-striped table-hover">
		<thead>
			<tr>
				<th>
				    User Name
				</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${users}" var="user">
				<c:choose>
					<c:when test="${empty user.code}">
						<c:out value="${user.name}" />
						<tr>
							<td>
							    There are not Users
							</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<td>
							    <a href="<spring:url value="/users/${user.code}.html" />">
									<c:out value="${user.name}" />
							    </a>
							</td>
							<c:if test="${admin != user.name}">
								<td>
								    <a href="<spring:url value="/users/remove/${user.code}.html" />"
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

	$(document).ready( function() {
	    $(".triggerRemove").click( function(e) {
		    e.preventDefault();
			$("#modalRemove .removeBtn").attr("href", $(this).attr("href"));
			$("#removeModalLabel").text("Remove User");
			$("#confirmationMessage").text("Are you sure to delete this User?");
			$("#modalRemove").modal();
		})
    });
	
</script>
