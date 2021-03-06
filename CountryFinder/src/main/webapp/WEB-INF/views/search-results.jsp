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
    <th><a title="Sort by name" href="/sort-by-name">Name</a></th>
    <th><a title="Sort by capital" href="/sort-by-capital">Capital</a></th>
    <th><a title="Sory by region" href="/sort-by-region">Region</a></th>
    <th><a title="Sort by population" href="/sort-by-population">Population</a></th>
    <th><a title="Sory by area" href="/sort-by-area">Area (mi^2)</a></th>
  </tr>
		<c:forEach var="c" items="${results}">
			<tr>
				<td><a target="_blank" title="Link to the wiki page for the country" href="https://en.wikipedia.org/wiki/${c.name}">${c.name}</a></td>
				<td>
					<c:choose>
						<c:when test="${empty c.capital}">
							Not provided
						</c:when>
						<c:otherwise>
							<a target="_blank" title="Link to the wiki page for the capital" href="https://en.wikipedia.org/wiki/${c.capital}">${c.capital}</a>
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${empty c.region}">
							Not provided
						</c:when>
						<c:otherwise>
							${c.region}
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${c.population == 0}">
							Not provided
						</c:when>
						<c:otherwise>
							${c.population}
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${c.area == 0}">
							Not provided
						</c:when>
						<c:otherwise>
							${c.area}
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</c:forEach>
	</table>

<a class="btn btn-primary" href="/start-page">Back to start page</a>

<a class="btn btn-primary" onclick="logOutShowStats()" href="/log-out">Log out</a>

<script>

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