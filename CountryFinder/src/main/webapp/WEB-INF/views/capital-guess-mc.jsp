<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Capital Guesser: Multiple choice</title>
<link href="https://stackpath.bootstrapcdn.com/bootswatch/4.3.1/sketchy/bootstrap.min.css" rel="stylesheet" integrity="sha384-N8DsABZCqc1XWbg/bAlIDk7AS/yNzT5fcKzg/TwfmTuUqZhGquVmpb5VvfmLcMzp" crossorigin="anonymous">
</head>
<body>

<h1>What is the capital of ${randomCountry.name}?</h1>

	<form action="/capital-guess">
		<select name="capital" required>
			<option value="">Population:</option>
			<option value="${randomCountries.get(0).capital}">${randomCountries.get(0).capital}</option>
			<option value="${randomCountries.get(1).capital}">${randomCountries.get(1).capital}</option>
			<option value="${randomCountries.get(2).capital}">${randomCountries.get(2).capital}</option>
			<option value="${randomCountries.get(3).capital}">${randomCountries.get(3).capital}</option>
			</select>
		<input class="btn btn-primary" type="submit" value="Submit">
	</form>

<br>
<a class="btn btn-primary" href="/start-page">Back to start page</a>

<a class="btn btn-primary" onclick="logOutShowStats()" href="/log-out">Log out</a>

<script>

var alertMessage = "Thanks for playing!\n\nCurrent session wins: ${currentWins}\nCurrent session losses: ${currentLosses}\nCurrent session games played: ${gamesPlayed}";

//Displays user stats when user logs out
const logOutShowStats = () => {
	if(${currentUser.userId} > 0){
		alertMessage += "\n\nTotal wins: ${currentUser.wins}\nTotal losses: ${currentUser.losses}\nTotal games played: ${currentUser.gamesPlayed}";
	}
	
	alert(alertMessage);
}

</script>

</body>
</html>