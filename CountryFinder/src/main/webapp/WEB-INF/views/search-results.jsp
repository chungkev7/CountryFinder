<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Search Results</title>
<link href="https://stackpath.bootstrapcdn.com/bootswatch/4.3.1/sketchy/bootstrap.min.css" rel="stylesheet" integrity="sha384-N8DsABZCqc1XWbg/bAlIDk7AS/yNzT5fcKzg/TwfmTuUqZhGquVmpb5VvfmLcMzp" crossorigin="anonymous">
</head>
<body>

${none}

<table class="table">
  <tr>
    <th>Name</th>
    <th>Capital</th>
    <th>Region</th>
    <th>Population</th>
  </tr>
		<c:forEach var="c" items="${results}">
			<tr>
				<td>${c.name}</td>
				<td>${c.capital}</td>
				<td>${c.region}</td>
				<td>${c.population}</td>
			</tr>
		</c:forEach>
	</table>

<a class="btn btn-primary" href="/main-page">Main Page</a>

</body>
</html>