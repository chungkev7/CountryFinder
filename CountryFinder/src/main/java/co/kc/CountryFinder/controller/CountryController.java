/**
 * @author Kevin Chung
 */

package co.kc.CountryFinder.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import co.kc.CountryFinder.model.Country;
import co.kc.CountryFinder.model.User;
import co.kc.CountryFinder.repo.UserRepo;

@Controller
public class CountryController {

	// API request key
	@Value("${country.key}")
	private String countryKey;
	
	@Autowired
	UserRepo uRepo;
	
	@Autowired
	HttpSession session;
	
	RestTemplate rt = new RestTemplate();
	
	// Created various static variables to utilize throughout the controller
	static ArrayList<Country> countryList = new ArrayList<>();
	static ArrayList<Country> randomCountries = new ArrayList<>();
	static Country randomCountry;
	static Country randomCountry2;
	static Country randomCountry3;
	static Country randomCountry4;
	// Index page is the initial login page
	static ModelAndView indexPage = new ModelAndView("index");
	static ModelAndView startPage = new ModelAndView("start-page");
	static ModelAndView searchResults = new ModelAndView("search-results");
	static ModelAndView popGuess = new ModelAndView("pop-guess");
	static ModelAndView popGuessMc = new ModelAndView("pop-guess-mc");
	static ModelAndView capGuess = new ModelAndView("capital-guess");
	static ModelAndView capGuessMc = new ModelAndView("capital-guess-mc");
	static ModelAndView guessResult = new ModelAndView("guess-result");
	static int nameClickCounter = 0;
	static int capitalClickCounter = 0;
	static int populationClickCounter = 0;
	static int regionClickCounter = 0;
	static int areaClickCounter = 0;
	static User currentUser = new User();
	static int currentLoginWins = 0;
	static int currentLoginLosses = 0;
	static int currentLoginGamesPlayed = 0;
	
	/**
	 * Returns a list of countries based on user input request
	 * 
	 * @param name: country name text input form from index jsp page
	 * @return: a list of countries
	 */
	@RequestMapping("/search-results")
	public ModelAndView homePage(@RequestParam("name") String name) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-RapidAPI-Key", countryKey);
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		
		try {
			ResponseEntity<Country[]> response = rt.exchange("https://restcountries-v1.p.rapidapi.com/name/" + name, HttpMethod.GET, entity, Country[].class);
			
			addtoCountryList(response);
			
			searchResults.addObject("results", countryList);

		} catch (RestClientException e) {
			searchResults.addObject("none", "No search results found.");
		}
		
		return searchResults;
	}
	
	/**
	 * Returns a list of countries in a particular region based on user selection
	 * 
	 * @param region: dropdown list of countries
	 * @return: a list of countries
	 */
	@RequestMapping("/region-search")
	public ModelAndView searchByRegion(@RequestParam("region") String region) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-RapidAPI-Key", countryKey);
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		
		ResponseEntity<Country[]> response = rt.exchange("https://restcountries-v1.p.rapidapi.com/region/" + region, HttpMethod.GET, entity, Country[].class);
		
		addtoCountryList(response);
		
		searchResults.addObject("results", countryList);
		
		return searchResults;
	}
	
	/**
	 * Returns a list of all of the countries
	 * 
	 * @return: a list of countries
	 */
	@RequestMapping("/search-all")
	public ModelAndView searchAllCountries() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-RapidAPI-Key", countryKey);
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		
		ResponseEntity<Country[]> response = rt.exchange("https://ajayakv-rest-countries-v1.p.rapidapi.com/rest/v1/all", HttpMethod.GET, entity, Country[].class);
		
		addtoCountryList(response);
		
		searchResults.addObject("results", countryList);
		
		return searchResults;
	}
	
	/**
	 * Generates a random country from a particular region for user to guess the population
	 * 
	 * @param region: a selected region from the dropdown list of countries
	 * @param choice: indicates if user wants to guess by an input or by multiple choice
	 * @return: a list of four countries
	 */
	@RequestMapping("/random-country-population")
	public ModelAndView getRandomCountryByRegion(@RequestParam("region") String region, @RequestParam("choice") String choice) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-RapidAPI-Key", countryKey);
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		
		ResponseEntity<Country[]> response = rt.exchange("https://restcountries-v1.p.rapidapi.com/region/" + region, HttpMethod.GET, entity, Country[].class);
					
		addtoCountryList(response);
		
		int countryNum = (int) (Math.random() * countryList.size());
		randomCountry = countryList.get(countryNum);
		
		if (choice.equals("Single input")) {
			popGuess.addObject("randomCountry", randomCountry);
			
			return popGuess;
		} else {
			
			// Assignment of random countries 2-4 are in try-catch blocks
			// if the randomly generated countryNum is on the outer edges of the countryList index
			try {
				randomCountry2 = countryList.get(countryNum + 1);
			} catch (IndexOutOfBoundsException e) {
				randomCountry2 = countryList.get(countryNum - 1);
			}
			
			try {
				randomCountry3 = countryList.get(countryNum + 2);
			} catch (IndexOutOfBoundsException e) {
				randomCountry3 = countryList.get(countryNum - 2);
			}
			
			try {
				randomCountry4 = countryList.get(countryNum + 3);
			} catch (IndexOutOfBoundsException e) {
				randomCountry4 = countryList.get(countryNum - 3);
			}
			
			randomCountries.add(randomCountry);
			randomCountries.add(randomCountry2);
			randomCountries.add(randomCountry3);
			randomCountries.add(randomCountry4);
			
			// Collections.shuffle() randomly moves the countries to different indexes
			Collections.shuffle(randomCountries);
			
			popGuessMc.addObject("randomCountry", randomCountry);
			popGuessMc.addObject("randomCountries", randomCountries);
			
			return popGuessMc;
		}

	}
	
	/**
	 * Generates a message to user if user guesses the population correctly or incorrectly
	 * 
	 * Saves user's guesses to database
	 * 
	 * @param population: compares the user's population guess to the random country
	 * @return: message if user was correct or not
	 */
	@RequestMapping("/pop-guess")
	public ModelAndView populationGuess(@RequestParam("population") int population) {
		
		if (population != randomCountry.getPopulation()) {
			guessResult.addObject("result", "Sorry, your guess of " + String.format("%,d", population) + " is incorrect. The population of " + randomCountry.getName() + " is " + String.format("%,d", randomCountry.getPopulation()) + ". You are off by " + String.format("%,d", ((int)Math.abs(population - randomCountry.getPopulation()))) + " citizens.");
			incorrectGuess(currentUser);
			saveUser(currentUser);
			addCurrentRecordToSession(session);
		} else {
			guessResult.addObject("result", "You are correct! The population of " + randomCountry.getName() + " is " + String.format("%,d", randomCountry.getPopulation()) + " citizens.");
			correctGuess(currentUser);
			saveUser(currentUser);
			addCurrentRecordToSession(session);
		}
		
		countryList.clear();
		
		return guessResult;
	}
	
	/**
	 * Generates a random country from a particular region for user to guess the capital
	 * 
	 * @param region: a selected region from the dropdown list of countries
	 * @param choice: indicates if user wants to guess by an input or by multiple choice
	 * @return: a list of four countries
	 */
	@RequestMapping("/random-country-capital")
	public ModelAndView getRandomCountryByCapital(@RequestParam("region") String region, @RequestParam("choice") String choice) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-RapidAPI-Key", countryKey);
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		
		ResponseEntity<Country[]> response = rt.exchange("https://restcountries-v1.p.rapidapi.com/region/" + region, HttpMethod.GET, entity, Country[].class);
					
		addtoCountryList(response);
		
		int countryNum = (int) (Math.random() * countryList.size());
		randomCountry = countryList.get(countryNum);
		
		if (choice.equals("Single input")) {
			
			capGuess.addObject("randomCountry", randomCountry);
			
			return capGuess;
		} else {
			
			// Assignment of random countries 2-4 are in try-catch blocks
			// if the randomly generated countryNum is on the outer edges of the countryList index
			try {
				randomCountry2 = countryList.get(countryNum + 1);
			} catch (IndexOutOfBoundsException e) {
				randomCountry2 = countryList.get(countryNum - 1);
			}
			
			try {
				randomCountry3 = countryList.get(countryNum + 2);
			} catch (IndexOutOfBoundsException e) {
				randomCountry3 = countryList.get(countryNum - 2);
			}
			
			try {
				randomCountry4 = countryList.get(countryNum + 3);
			} catch (IndexOutOfBoundsException e) {
				randomCountry4 = countryList.get(countryNum - 3);
			}

			randomCountries.add(randomCountry);
			randomCountries.add(randomCountry2);
			randomCountries.add(randomCountry3);
			randomCountries.add(randomCountry4);
			
			// Collections.shuffle() randomly moves the countries to different indexes
			Collections.shuffle(randomCountries);
			
			capGuessMc.addObject("randomCountry", randomCountry);
			capGuessMc.addObject("randomCountries", randomCountries);
			
			return capGuessMc;
		}
		
	}
	
	/**
	 * Generates a message to user if user guesses the capital correctly or incorrectly
	 * 
	 * Saves user's guesses to database
	 * 
	 * @param capital: compares the user's capital guess to the random country
	 * @return: message if user was correct or not
	 */
	@RequestMapping("/capital-guess")
	public ModelAndView capitalGuess(@RequestParam("capital") String capital) {
		
		if(!capital.equalsIgnoreCase(randomCountry.getCapital())) {
			guessResult.addObject("result", "Sorry, your guess is incorrect. The capital of " + randomCountry.getName() + " is " + randomCountry.getCapital() + ".");
			incorrectGuess(currentUser);
			saveUser(currentUser);
			addCurrentRecordToSession(session);
		} else {
			guessResult.addObject("result", "You are correct! The capital of " + randomCountry.getName() + " is " + randomCountry.getCapital() + ".");
			correctGuess(currentUser);
			saveUser(currentUser);
			addCurrentRecordToSession(session);
		}
		
		return guessResult;
	}
	
	/**
	 * Generates a random country from all regions for user to guess the population
	 * 
	 * user indication (yes or not to continue)
	 * @return: a random country's population
	 */
	@RequestMapping("/all-country-population")
	public ModelAndView getRandomCountryByAllPopulation() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-RapidAPI-Key", countryKey);
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		
		ResponseEntity<Country[]> response = rt.exchange("https://restcountries-v1.p.rapidapi.com/all", HttpMethod.GET, entity, Country[].class);
					
		addtoCountryList(response);
		
		int countryNum = (int) (Math.random() * countryList.size());
		randomCountry = countryList.get(countryNum);
		
		popGuess.addObject("randomCountry", randomCountry);

		return popGuess;
	}
	
	/**
	 * Generates a random country from all regions for user to guess the capital
	 * 
	 * user indication (yes or not to continue)
	 * @return: a random country's capital
	 */
	@RequestMapping("/all-country-capital")
	public ModelAndView getRandomCountryByAllCapital() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-RapidAPI-Key", countryKey);
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		
		ResponseEntity<Country[]> response = rt.exchange("https://restcountries-v1.p.rapidapi.com/all", HttpMethod.GET, entity, Country[].class);
					
		addtoCountryList(response);
		
		int countryNum = (int) (Math.random() * countryList.size());
		randomCountry = countryList.get(countryNum);
		
		capGuess.addObject("randomCountry", randomCountry);

		return capGuess;
	}
	
	/**
	 * Sorts the country list search results by name
	 * 
	 * @return: sorted country list based on their name
	 */
	@RequestMapping("/sort-by-name")
	public ModelAndView sortByName() {
		
		nameClickCounter++;
		
		if (nameClickCounter % 2 != 0) {
			Comparator<Country> sortByNameDesc = (Country c1, Country c2) -> c2.getName().compareTo(c1.getName());
			
			Collections.sort(countryList, sortByNameDesc);
		} else {
			Comparator<Country> sortByNameAsc = (Country c1, Country c2) -> c1.getName().compareTo(c2.getName());
			
			Collections.sort(countryList, sortByNameAsc);
		}
		
		searchResults.addObject("results", countryList);
		
		return searchResults; 
	}
	
	/**
	 * Sorts the country list search results by capital
	 * 
	 * @return: sorted country list based on their capital
	 */
	@RequestMapping("/sort-by-capital")
	public ModelAndView sortByCapital() {
		
		capitalClickCounter++;
		
		if (capitalClickCounter % 2 != 0) {
			Comparator<Country> sortByCapitalAsc = (Country c1, Country c2) -> c1.getCapital().compareTo(c2.getCapital());
		
			Collections.sort(countryList, sortByCapitalAsc);
		} else {
			Comparator<Country> sortByCapitalDesc = (Country c1, Country c2) -> c2.getCapital().compareTo(c1.getCapital());
			
			Collections.sort(countryList, sortByCapitalDesc);
		}
		
		searchResults.addObject("results", countryList);
				
		return searchResults;
	}
	
	/**
	 * Sorts the country list search results by population
	 * 
	 * @return: sorted country list based on their population
	 */
	@RequestMapping("/sort-by-population")
	public ModelAndView sortByPopulation() {
		
		populationClickCounter++;
		
		if (populationClickCounter % 2 != 0) {
			
			Comparator<Country> sortByPopulationDesc = (Country c1, Country c2) -> Integer.compare(c2.getPopulation(), c1.getPopulation());
		
			Collections.sort(countryList, sortByPopulationDesc);
		} else {
			Comparator<Country> sortByPopulationAsc = (Country c1, Country c2) -> Integer.compare(c1.getPopulation(), c2.getPopulation());
			
			Collections.sort(countryList, sortByPopulationAsc);
		}
		
		searchResults.addObject("results", countryList);
		
		return searchResults;
	}
	
	/**
	 * Sorts the country list search results by region
	 * 
	 * @return: sorted country list based on region
	 */
	@RequestMapping("/sort-by-region")
	public ModelAndView sortByRegion() {
		
		regionClickCounter++;
		
		if (regionClickCounter % 2 != 0) {
			Comparator<Country> sortByRegionDesc = (Country c1, Country c2) -> c1.getRegion().compareTo(c2.getRegion());
			
			Collections.sort(countryList, sortByRegionDesc);
		} else {
			Comparator<Country> sortByRegionAsc = (Country c1, Country c2) -> c2.getRegion().compareTo(c1.getRegion());
			
			Collections.sort(countryList, sortByRegionAsc);
		}
		
		searchResults.addObject("results", countryList);
		
		return searchResults;
	}
	
	/**
	 * Sorts the country list search results by area
	 * 
	 * @return: sorted country list based on area
	 */
	@RequestMapping("/sort-by-area")
	public ModelAndView sortByArea() {
		
		areaClickCounter++;
		
		if (areaClickCounter % 2 != 0) {
			Comparator<Country> sortByAreaDesc = (Country c1, Country c2) -> Integer.compare(c1.getArea(), c2.getArea());
			
			Collections.sort(countryList, sortByAreaDesc);
		} else {
			Comparator<Country> sortByAreaAsc = (Country c1, Country c2) -> Integer.compare(c2.getArea(), c1.getArea());
			
			Collections.sort(countryList, sortByAreaAsc);
		}
		
		searchResults.addObject("results", countryList);
		
		return searchResults;
	}
	
	/**
	 * Added request mapping for initial startup page to add the number of users in database to session
	 */
	@RequestMapping("/")
	public ModelAndView initialPage() {
		
		session.setAttribute("numOfUsers", uRepo.findMaxId());
		
		return indexPage;
	}
	
	// Searches database for userId, sets currentUser to that userId if present
	// Otherwise sends user back to index page with user does not exist message
	@RequestMapping("/login")
	public ModelAndView login(@RequestParam("userId") int userId) {

		currentUser = uRepo.findById(userId).orElse(null);
		
		if(currentUser == null) {
			
			indexPage.addObject("user", "The userID does not exist. Please register or login as a guest.");
			
			return indexPage;
		} else {
			
			addUserSession(currentUser, session);
			
			// Clear and reset the contents of the index page to remove the error message
			indexPage.clear();
			indexPage.setViewName("index");
			
			return startPage;
		}		
	}
	
	// Logins user as a guest, does not set currentUser to a user in the database
	@RequestMapping("/login-guest")
	public ModelAndView loginAsGuest() {
		
		addUserSession(currentUser, session);
		
		// Clear and reset the contents of the index page to remove the error message
		indexPage.clear();
		indexPage.setViewName("index");
		
		return startPage;
	}
	
	// Creates a new user and userID
	// Next userID is set to the max Id + 1
	@RequestMapping("new-user")
	public ModelAndView newUser() {
		
		currentUser = new User();
		uRepo.save(currentUser);
		
		session.setAttribute("numOfUsers", uRepo.findMaxId());
		
		addUserSession(currentUser, session);
		
		return startPage;
	}
	
	/**
	 * Clears the country list and random countries list if user goes back to the start page
	 */
	@RequestMapping("/start-page")
	public ModelAndView backToStartPage() {
		
		countryList.clear();
		randomCountries.clear();
		
		nameClickCounter = 0;
		capitalClickCounter = 0;
		populationClickCounter = 0;
		regionClickCounter = 0;
		areaClickCounter = 0;
		
		return startPage;
	}
	
	/**
	 * Clears the country list and random countries list if user logs out and goes back to the initial login page
	 * 
	 * Resets the current session record and click counters
	 */
	@RequestMapping("/log-out")
	public ModelAndView logOut() {
		
		countryList.clear();
		randomCountries.clear();
		
		currentUser = new User();
		
		session.removeAttribute("currentUser");

		currentLoginWins = 0;
		currentLoginLosses = 0;
		currentLoginGamesPlayed = 0;
		nameClickCounter = 0;
		capitalClickCounter = 0;
		populationClickCounter = 0;
		regionClickCounter = 0;
		areaClickCounter = 0;
		
		return indexPage;
	}
	
	// Adds a user object to the session to be accessible in the jsp pages
	public static void addUserSession(User currentUser, HttpSession session) {
		session.setAttribute("currentUser", currentUser);
	}
	
	// Adds +1 win, games played for each correct guess
	public static void correctGuess(User user) {
		currentLoginWins++;
		currentLoginGamesPlayed++;
		user.setWins(user.getWins() + 1);
		user.setGamesPlayed(user.getGamesPlayed() + 1);
	}
	
	// Adds +1 loss, games played for each incorrect guess
	public static void incorrectGuess(User user) {
		currentLoginLosses++;
		currentLoginGamesPlayed++;
		user.setLosses(user.getLosses() + 1);
		user.setGamesPlayed(user.getGamesPlayed() + 1);
	}
	
	// Saves user's wins, losses, and games played to database
	// if user did not login as a guest (guest user's ID is 0)
	public void saveUser(User user) {
		if(user.getUserId() > 0) {
			uRepo.save(user);	
		}
	}
	
	// Adds the user's current record stats to session
	public static void addCurrentRecordToSession(HttpSession session) {
		session.setAttribute("currentWins", currentLoginWins);
		session.setAttribute("currentLosses", currentLoginLosses);
		session.setAttribute("gamesPlayed", currentLoginGamesPlayed);
	}
	
	/**
	 * Adds countries to countryList
	 * 
	 * @param response: json data from api request
	 */
	public static void addtoCountryList(ResponseEntity<Country[]> response) {
		for (Country c : response.getBody()) {
			countryList.add(c);
		}
	}
}
