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

<br>
<a class="btn btn-secondary" onclick="backToStartPage()">Back to start page</a>

<a class="btn btn-secondary" onclick="logOutShowStats()">Log out</a>

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
	var c = confirm("Are you sure you want to log out?\n\nThis current guess will not be added to your record");
	
	if (c == true){
		if(${currentUser.userId} > 0){
			alertMessage += "\n\nTotal wins: ${currentUser.wins}\nTotal losses: ${currentUser.losses}\nTotal games played: ${currentUser.gamesPlayed}";
		}

		alert(alertMessage);
		
		window.location = "/log-out";
	}
}

</script>

</body>
</html>