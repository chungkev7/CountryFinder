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

<div class="jumbotron">

<h1>${user}</h1>

<form action="/login">
	UserID: <input type="number" name="userId" min=1 required>
	<input class="btn btn-primary" type="submit" value="login">
</form>

<p></p>

<a class="btn btn-primary" href="/login-guest">Login as a guest</a>

<p></p>

<a class="btn btn-primary" onclick="newUserId()" href="/new-user">New user? Click here to register!</a>

</div>

<script>

function newUserId(){
	alert("Your user ID is: " + ${numOfUsers + 1} + ". Please remember it.");
}

</script>
</body>
</html>