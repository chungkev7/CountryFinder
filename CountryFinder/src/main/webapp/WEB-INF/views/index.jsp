<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home Page</title>
<link href="https://stackpath.bootstrapcdn.com/bootswatch/4.3.1/sketchy/bootstrap.min.css" rel="stylesheet" integrity="sha384-N8DsABZCqc1XWbg/bAlIDk7AS/yNzT5fcKzg/TwfmTuUqZhGquVmpb5VvfmLcMzp" crossorigin="anonymous">
</head>
<body>

	<h1>Search by country name</h1>
	<form action="/search-results">
		Country name: <input type="text" name="name" required> 
		<input class="btn btn-primary" type="submit" value="Submit">
	</form>

	<h1>Search by region</h1>
	<form action="/region-search">
		<select name="region" required>
			<option value="Africa">Africa</option>
			<option value="Americas">Americas</option>
			<option value="Asia">Asia</option>
			<option value="Europe">Europe</option>
			<option value="Oceania">Oceania</option>
		</select>
		<input class="btn btn-primary" type="submit" value="Submit">
	</form>

	<h1>Guess population: Search by region</h1>
		<form action="/random-country">
		<select name="region" required>
			<option value="Africa">Africa</option>
			<option value="Americas">Americas</option>
			<option value="Asia">Asia</option>
			<option value="Europe">Europe</option>
			<option value="Oceania">Oceania</option>
		</select>
		<input class="btn btn-primary" type="submit" value="Submit">
	</form>
	
</body>
</html>