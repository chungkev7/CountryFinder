<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Start Page</title>
<link href="https://stackpath.bootstrapcdn.com/bootswatch/4.3.1/sketchy/bootstrap.min.css" rel="stylesheet" integrity="sha384-N8DsABZCqc1XWbg/bAlIDk7AS/yNzT5fcKzg/TwfmTuUqZhGquVmpb5VvfmLcMzp" crossorigin="anonymous">
</head>
<body>

<div class="jumbotron">

	<h1>Search by country name</h1>
	<form action="/search-results">
		Country name: <input type="text" name="name" required> 
		<input class="btn btn-primary" type="submit" value="Submit">
	</form>

	<h1>Search countries by region</h1>
	<form action="/region-search">
		<select name="region" required>
			<option value="">Region:</option>
			<option value="Africa">Africa</option>
			<option value="Americas">Americas</option>
			<option value="Asia">Asia</option>
			<option value="Europe">Europe</option>
			<option value="Oceania">Oceania</option>
		</select>
		<input class="btn btn-primary" type="submit" value="Submit">
	</form>
	
	<p></p>
	<h1>Search all countries</h1>
	<a class="btn btn-primary" href="/search-all">Click here to search all countries</a>
	<p></p>
	
	<!-- The selected region value is passed to the hidden value to form action/random-country-population -->
	<h1>Guess population: Search by region</h1>
	<form>
		<select name="region" id="regionPop" onclick="getRegionChoicePop()" required>
			<option value="">Region:</option>
			<option value="Africa">Africa</option>
			<option value="Americas">Americas</option>
			<option value="Asia">Asia</option>
			<option value="Europe">Europe</option>
			<option value="Oceania">Oceania</option>
		</select>
	</form>
	
	<!-- Added onsubmit function for region choice validation -->
	<form action="/random-country-population" onsubmit="return isRegionChoiceEmptyPop()">
		<input type="hidden" id="selectedRegionPop" name="region" value="">
		<select name="choice" required>
			<option value="Single input">Single input choice</option>
			<option value="Multiple choice">Multiple choice</option>
		</select>
		<input class="btn btn-primary" type="submit" value="Submit">
	</form>
	
	<!-- The selected region value is passed to the hidden value to form action/random-country-capital -->
	<h1>Guess capital: Search by region</h1>
	<form>
		<select name="region" id="regionCap" onclick="getRegionChoiceCap()" required>
			<option value="">Region:</option>
			<option value="Africa">Africa</option>
			<option value="Americas">Americas</option>
			<option value="Asia">Asia</option>
			<option value="Europe">Europe</option>
			<option value="Oceania">Oceania</option>
			</select>
	</form>
	
	<!-- Added onsubmit function for region choice validation -->
	<form action="/random-country-capital" onsubmit="return isRegionChoiceEmptyCap()">
		<input type="hidden" id="selectedRegionCap" name="region" value="">
		<select name="choice" required>
			<option value="Single input">Single input choice</option>
			<option value="Multiple choice">Multiple choice</option>
		</select>
		<input class="btn btn-primary" type="submit" value="Submit">
	</form>
	
	<br>
	<h1><a class="btn btn-secondary" onclick="confirmPopChoice()">Guess population (single choice), search by all countries</a></h1>
	
	<br>
	<h1><a class="btn btn-secondary" onclick="confirmCapChoice()">Guess capital (single choice), search by all countries</a></h1>

<br>
<a class="btn btn-primary" onclick="logOutShowStats()" href="/log-out">Log out</a>

</div>

<script>

var regionChoicePop;
var selectedRegionPop;

function getRegionChoicePop(){
	regionChoicePop = document.getElementById("regionPop").value;

	document.getElementById("selectedRegionPop").value = regionChoicePop;

	selectedRegionPop = regionChoicePop;
}
	
function isRegionChoiceEmptyPop() {
	// !selection is equivalent to checking if the variable is empty
	if (!selectedRegionPop){
		alert("You must select a region if you want to guess the population");
		return false;
	}
	return true;
}

var regionChoicePop;
var selectedRegionPop;

function getRegionChoiceCap(){
	regionChoiceCap = document.getElementById("regionCap").value;
	
	document.getElementById("selectedRegionCap").value = regionChoiceCap;

	selectedRegionCap = regionChoiceCap;
}
	
function isRegionChoiceEmptyCap() {
	// !selection is equivalent to checking if the variable is empty
	if (!selectedRegionCap){
		alert("You must select a region if you want to guess the capital");
		return false;
	}
	return true;
}

// Adds confirmation window. If yes, goes to request mapping
function confirmPopChoice() {
	  var r = confirm("Are you sure?");
	  
	  if (r == true) {
		  window.location = "/all-country-population";
	  }
}

//Adds confirmation window. If yes, goes to request mapping
function confirmCapChoice() {
	  var c = confirm("Are you sure?");
	  
	  if (c == true) {
		  window.location = "/all-country-capital";
	  }
}

// Displays user's play record if user is not a guest when the user logs in
// This message will only display correctly (display only once) if the browser is not in private/incognito mode
window.onload = function () {
    if (localStorage.getItem("startPage") === null) {
        if(${currentUser.userId} > 0) {
        	alert("Welcome back!\nHere's your current record:\n\nTotal wins: ${currentUser.wins}\nTotal losses: ${currentUser.losses}\nTotal games played: ${currentUser.gamesPlayed}");
        }
        
        localStorage.setItem("startPage", true);
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