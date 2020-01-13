<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Capital Guess</title>
<link href="https://stackpath.bootstrapcdn.com/bootswatch/4.3.1/sketchy/bootstrap.min.css" rel="stylesheet" integrity="sha384-N8DsABZCqc1XWbg/bAlIDk7AS/yNzT5fcKzg/TwfmTuUqZhGquVmpb5VvfmLcMzp" crossorigin="anonymous">
</head>
<body>

<h1>What is the capital of ${randomCountry.name}?</h1>
<form action="/capital-guess">
	<input type="text" name="capital" required>
	<input type="submit" value="submit">
</form>

<a class="btn btn-primary" href="/main-page">Main Page</a>

</body>
</html>