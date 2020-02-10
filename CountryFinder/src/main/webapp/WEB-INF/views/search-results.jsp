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
    <th><a href="/sort-by-name">Name</a></th>
    <th><a href="/sort-by-capital">Capital</a></th>
    <th>Region</th>
    <th><a href="/sort-by-population">Population</a></th>
  </tr>
		<c:forEach var="c" items="${results}">
			<tr>
				<td><a target="_blank" href="https://en.wikipedia.org/wiki/${c.name}">${c.name}</a></td>
				<td><a target="_blank" href="https://en.wikipedia.org/wiki/${c.capital}">${c.capital}</a></td>
				<td>${c.region}</td>
				<td>${c.population}</td>
			</tr>
		</c:forEach>
	</table>

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