<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Population Guesser</title>
</head>
<body>

	<h1>What is the population of ${randomCountry.name}?</h1>
	<form action="/pop-guess">
	Population: <input type="number" name="population">
	<input type="submit" value="submit">
	</form>
</body>
</html>