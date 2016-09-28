<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:getAsString name="title"></tiles:getAsString></title>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
	integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp"
	crossorigin="anonymous">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
	integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
	crossorigin="anonymous"></script>


<spring:url value="/resources/core/jquery.autocomplete.min.js" var="jqueryAutoCompl" />
<script src="${jqueryAutoCompl}"></script>
	
<link rel="stylesheet" type="text/css" href="/resources/css/searchResults.css" />

</head>
<body>

	<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras"
		prefix="tilesx"%>

	<tilesx:useAttribute name="current" />

	<div class="container">

		<!-- Static navbar -->
		<nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#navbar" aria-expanded="false"
						aria-controls="navbar">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<div class="${current == 'index' ? 'active' : ''}">
						<a class="navbar-brand" href="<spring:url value="/"/>">Movie Pick</a>
					</div>
				</div>
				<div id="navbar" class="navbar-collapse collapse">
					<form class="navbar-form navbar-left">
            			<input type="text" id="movieInput" class="form-control" placeholder="Search a movie" >
          			</form>
					<ul class="nav navbar-nav">
						<li class="${current == 'result' ? 'active' : ''}">
							<a id="searchLink" href="<spring:url value="/result.html" />">Search</a>
						</li>
						<security:authorize access="isAuthenticated() and hasRole('ROLE_ADMIN')">
							<li class="${current == 'users' ? 'active' : ''}"><a
								href="<spring:url value="/users.html" />">Users</a></li>
						</security:authorize>
						<security:authorize access="isAuthenticated() and hasRole('ROLE_ADMIN')">
							<li class="${current == 'movies' ? 'active' : ''}"><a
								href="<spring:url value="/movies.html" />">Movies</a></li>
						</security:authorize>
						<security:authorize access="isAuthenticated()">
							<li class="${current == 'profile' ? 'active' : ''}"><a
								href="<spring:url value="/profile.html" />">My Profile</a></li>
						</security:authorize>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<security:authorize access="! isAuthenticated()">
							<li class="${current == 'login' ? 'active' : ''}"><a
								href="<spring:url value="/login.html" />">Login</a></li>
						</security:authorize>
						<security:authorize access="isAuthenticated()">
							<li><a href="<spring:url value="/logout" />">Logout</a></li>
						</security:authorize>
						<security:authorize access="! isAuthenticated()">
							<li class="${current == 'register' ? 'active' : ''}"><a
								href="<spring:url value="/register.html" />">Register</a></li>
						</security:authorize>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
			<!--/.container-fluid -->
		</nav>

		<tiles:insertAttribute name="body" />

		<br> <br>

		<p style="text-align:center">
			<tiles:insertAttribute name="footer" />
		</p>

	</div>
	
	<script>
		var link = document.getElementById('searchLink');
	    var input = document.getElementById('movieInput');
	    input.onchange = input.onkeyup = function() {
	        link.href= '/result/'+input.value+'.html';
	    };
	    
	    $(document).ready(function() {
			$('#movieInput').autocomplete({
				serviceUrl: '${pageContext.request.contextPath}/getMovies.html',
				paramName: "movieName",
			    transformResult: function(response) {
			        return {
			            suggestions: $.map($.parseJSON(response), function(item) {
			                return { value: item.movieName, data: item.id};
			            })
			        };   
			    },
			    onSelect: function (suggestion) {
			        window.location.href = "${pageContext.request.contextPath}/movie/"+suggestion.data+".html";
			    }
			});
		});
		
	</script>

</body>
</html>