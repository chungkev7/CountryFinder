<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Population Guesser: Multiple choice</title>
<link href="https://stackpath.bootstrapcdn.com/bootswatch/4.3.1/sketchy/bootstrap.min.css" rel="stylesheet" integrity="sha384-N8DsABZCqc1XWbg/bAlIDk7AS/yNzT5fcKzg/TwfmTuUqZhGquVmpb5VvfmLcMzp" crossorigin="anonymous">
</head>
<body>

<h1>What is the population of ${randomCountry.name}?</h1>

	<form action="/pop-guess">
		<select name="population" required>
			<option value="">Population:</option>
			<option value="${randomCountries.get(0).population}">${randomCountries.get(0).population}</option>
			<option value="${randomCountries.get(1).population}">${randomCountries.get(1).population}</option>
			<option value="${randomCountries.get(2).population}">${randomCountries.get(2).population}</option>
			<option value="${randomCountries.get(3).population}">${randomCountries.get(3).population}</option>
			</select>
		<input class="btn btn-primary" type="submit" value="Submit">
	</form>

<a class="btn btn-primary" href="/start-page">Back to start page</a>

<a class="btn btn-primary" href="/log-out">Log out</a>

</body>
</html>