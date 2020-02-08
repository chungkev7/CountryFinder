<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Guess Result</title>
<link href="https://stackpath.bootstrapcdn.com/bootswatch/4.3.1/sketchy/bootstrap.min.css" rel="stylesheet" integrity="sha384-N8DsABZCqc1XWbg/bAlIDk7AS/yNzT5fcKzg/TwfmTuUqZhGquVmpb5VvfmLcMzp" crossorigin="anonymous">

</head>
<body>

<h1>${result}</h1>

<a class="btn btn-primary" href="/start-page">Back to start page</a>

<a class="btn btn-primary" onclick="logOutShowStats()" href="/log-out">Log out</a>

<script>

//Displays user stats when user logs out
const logOutShowStats = () => {
	alert("Thanks for playing!\n\nTotal wins: ${currentUser.wins}\nTotal losses: ${currentUser.losses}\nTotal games played: ${currentUser.gamesPlayed}");
}

</script>

</body>
</html>