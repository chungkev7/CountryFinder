<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Population Guesser</title>
<link href="https://stackpath.bootstrapcdn.com/bootswatch/4.3.1/sketchy/bootstrap.min.css" rel="stylesheet" integrity="sha384-N8DsABZCqc1XWbg/bAlIDk7AS/yNzT5fcKzg/TwfmTuUqZhGquVmpb5VvfmLcMzp" crossorigin="anonymous">
</head>
<body>

<h1>What is the population of ${randomCountry.name}?</h1>

	<form action="/pop-guess">
	Population: <input type="number" name="population" required>
	<input type="submit" value="submit">
	</form>
	<br>

<a class="btn btn-secondary" onclick="backToStartPage()">Back to start page</a>

<a class="btn btn-primary" onclick="logOutShowStats()" href="/log-out">Log out</a>

<script>

// Confirms if user returns to start page
const backToStartPage = () => {
	  var c = confirm("Are you sure?\n\nThis current guess will not be added to your record");
	  
	  if (c == true) {
		  window.location = "/start-page";
	  }
}

var alertMessage = "Thanks for playing!\n\nCurrent session wins: ${currentWins}\nCurrent session losses: ${currentLosses}\nCurrent session games played: ${gamesPlayed}";

// Displays user stats when user logs out
// Additional stats are shown if user did not login as a guest
const logOutShowStats = () => {
	if(${currentUser.userId} > 0){
		alertMessage += "\n\nTotal wins: ${currentUser.wins}\nTotal losses: ${currentUser.losses}\nTotal games played: ${currentUser.gamesPlayed}";
	}
	
	alert(alertMessage);
}


</script>

</body>
</html>