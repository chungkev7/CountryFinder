/**
 * @author Kevin Chung
 */

package co.kc.CountryFinder.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

	@Value("${country.key}")
	private String countryKey;
	
	@Autowired
	UserRepo uRepo;
	
	RestTemplate rt = new RestTemplate();
	
	static ArrayList<Country> countryList = new ArrayList<>();
	static ArrayList<Country> randomCountries = new ArrayList<>();
	static Country randomCountry;
	static int nameClickCounter = 0;
	static int capitalClickCounter = 0;
	static int populationClickCounter = 0;
	static User currentUser;
	
	/**
	 * Returns a list of countries based on user input request
	 * 
	 * @param name: country name text input form from index jsp page
	 * @return: a list of countries
	 */
	@RequestMapping("/search-results")
	public ModelAndView homePage(@RequestParam("name") String name) {
		ModelAndView mv = new ModelAndView("search-results");
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-RapidAPI-Key", countryKey);
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		
		try {
			ResponseEntity<Country[]> response = rt.exchange("https://restcountries-v1.p.rapidapi.com/name/" + name, HttpMethod.GET, entity, Country[].class);
			
			addtoCountryList(response);
			
			mv.addObject("results", countryList);

		} catch (RestClientException e) {
			mv.addObject("none", "No search results found.");
		}
		
		return mv;
	}
	
	/**
	 * Returns a list of countries in a particular region based on user selection
	 * 
	 * @param region: dropdown list of countries
	 * @return: a list of countries
	 */
	@RequestMapping("/region-search")
	public ModelAndView searchByRegion(@RequestParam("region") String region) {
		ModelAndView mv = new ModelAndView("search-results");
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-RapidAPI-Key", countryKey);
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		
		ResponseEntity<Country[]> response = rt.exchange("https://restcountries-v1.p.rapidapi.com/region/" + region, HttpMethod.GET, entity, Country[].class);
		
		addtoCountryList(response);
		
		mv.addObject("results", countryList);
		
		return mv;
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
		// Initialize view, then set view if choice was single input/multiple choice
		ModelAndView mv = new ModelAndView();

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-RapidAPI-Key", countryKey);
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		
		ResponseEntity<Country[]> response = rt.exchange("https://restcountries-v1.p.rapidapi.com/region/" + region, HttpMethod.GET, entity, Country[].class);
					
		addtoCountryList(response);
		
		int countryNum = (int) (Math.random() * countryList.size());
		randomCountry = countryList.get(countryNum);
		
		Country randomCountry2 = new Country();
		Country randomCountry3 = new Country();
		Country randomCountry4 = new Country();
		
		if (choice.equals("Single input")) {
			mv.setViewName("pop-guess");
			mv.addObject("randomCountry", randomCountry);
		} else {
			mv.setViewName("pop-guess-mc");
			
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
			
			Collections.shuffle(randomCountries);
			
			mv.addObject("randomCountry", randomCountry);
			mv.addObject("randomCountries", randomCountries);
		}
		
		return mv;
	}
	
	/**
	 * Generates a message to user if user guesses the population correctly or incorrectly
	 * 
	 * @param population: compares the user's population guess to the random country
	 * @return: message if user was correct or not
	 */
	@RequestMapping("/pop-guess")
	public ModelAndView populationGuess(@RequestParam("population") int population) {
		ModelAndView mv = new ModelAndView("guess-result");
		
		if (population != randomCountry.getPopulation()) {
			mv.addObject("result", "Sorry, your guess of " + String.format("%,d", population) + " is incorrect. The population of " + randomCountry.getName() + " is " + String.format("%,d", randomCountry.getPopulation()) + ". You are off by " + String.format("%,d", ((int)Math.abs(population - randomCountry.getPopulation()))) + " citizens.");
		} else {
			mv.addObject("result", "You are correct! The population of " + randomCountry.getName() + " is " + String.format("%,d", randomCountry.getPopulation()) + " citizens.");
		}
		
		countryList.clear();
		
		return mv;
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
		// Initialize view, then set view if choice was single input/multiple choice
		ModelAndView mv = new ModelAndView();

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-RapidAPI-Key", countryKey);
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		
		ResponseEntity<Country[]> response = rt.exchange("https://restcountries-v1.p.rapidapi.com/region/" + region, HttpMethod.GET, entity, Country[].class);
					
		addtoCountryList(response);
		
		int countryNum = (int) (Math.random() * countryList.size());
		randomCountry = countryList.get(countryNum);
		
		Country randomCountry2 = new Country();
		Country randomCountry3 = new Country();
		Country randomCountry4 = new Country();
		
		if (choice.equals("Single input")) {
			mv.setViewName("capital-guess");
			mv.addObject("randomCountry", randomCountry);
		} else {
			mv.setViewName("capital-guess-mc");
			
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
			
			Collections.shuffle(randomCountries);
			
			mv.addObject("randomCountry", randomCountry);
			mv.addObject("randomCountries", randomCountries);
		}
		
		return mv;
	}
	
	/**
	 * Generates a message to user if user guesses the capital correctly or incorrectly
	 * 
	 * @param capital: compares the user's capital guess to the random country
	 * @return: message if user was correct or not
	 */
	@RequestMapping("/capital-guess")
	public ModelAndView capitalGuess(@RequestParam("capital") String capital) {
		ModelAndView mv = new ModelAndView("guess-result");
		
		if(!capital.equalsIgnoreCase(randomCountry.getCapital())) {
			mv.addObject("result", "Sorry, your guess is incorrect. The capital of " + randomCountry.getName() + " is " + randomCountry.getCapital() + ".");
		} else {
			mv.addObject("result", "You are correct! The capital of " + randomCountry.getName() + " is " + randomCountry.getCapital() + ".");
		}
		
		return mv;
	}
	
	/**
	 * Generates a random country from all regions for user to guess the population
	 * 
	 * user indication (yes or not to continue)
	 * @return: a random country's population
	 */
	@RequestMapping("/all-country-population")
	public ModelAndView getRandomCountryByAllPopulation() {
		ModelAndView mv = new ModelAndView("pop-guess");
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-RapidAPI-Key", countryKey);
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		
		ResponseEntity<Country[]> response = rt.exchange("https://restcountries-v1.p.rapidapi.com/all", HttpMethod.GET, entity, Country[].class);
					
		addtoCountryList(response);
		
		int countryNum = (int) (Math.random() * countryList.size());
		randomCountry = countryList.get(countryNum);
		
		mv.addObject("randomCountry", randomCountry);

		return mv;
	}
	
	/**
	 * Generates a random country from all regions for user to guess the capital
	 * 
	 * user indication (yes or not to continue)
	 * @return: a random country's capital
	 */
	@RequestMapping("/all-country-capital")
	public ModelAndView getRandomCountryByAllCapital() {
		ModelAndView mv = new ModelAndView("capital-guess");
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-RapidAPI-Key", countryKey);
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		
		ResponseEntity<Country[]> response = rt.exchange("https://restcountries-v1.p.rapidapi.com/all", HttpMethod.GET, entity, Country[].class);
					
		addtoCountryList(response);
		
		int countryNum = (int) (Math.random() * countryList.size());
		randomCountry = countryList.get(countryNum);
		
		mv.addObject("randomCountry", randomCountry);

		return mv;
	}
	
	/**
	 * Sorts the country list search results by name
	 * 
	 * @return: sorted country list based on their name
	 */
	@RequestMapping("/sort-by-name")
	public ModelAndView sortByName() {
		ModelAndView mv = new ModelAndView("search-results");
		
		nameClickCounter++;
		
		if (nameClickCounter % 2 != 0) {
			Comparator<Country> sortByNameDesc = (Country c1, Country c2) -> c2.getName().compareTo(c1.getName());
			
			Collections.sort(countryList, sortByNameDesc);
		} else {
			Comparator<Country> sortByNameAsc = (Country c1, Country c2) -> c1.getName().compareTo(c2.getName());
			
			Collections.sort(countryList, sortByNameAsc);
		}
		
		mv.addObject("results", countryList);
		
		return mv; 
	}
	
	/**
	 * Sorts the country list search results by capital
	 * 
	 * @return: sorted country list based on their capital
	 */
	@RequestMapping("/sort-by-capital")
	public ModelAndView sortByCapital() {
		ModelAndView mv = new ModelAndView("search-results");
		
		capitalClickCounter++;
		
		if (capitalClickCounter % 2 != 0) {
			Comparator<Country> sortByCapitalAsc = (Country c1, Country c2) -> c1.getCapital().compareTo(c2.getCapital());
		
			Collections.sort(countryList, sortByCapitalAsc);
		} else {
			Comparator<Country> sortByCapitalDesc = (Country c1, Country c2) -> c2.getCapital().compareTo(c1.getCapital());
			
			Collections.sort(countryList, sortByCapitalDesc);
		}
		
		mv.addObject("results", countryList);
				
		return mv;
	}
	
	/**
	 * Sorts the country list search results by population
	 * 
	 * @return: sorted country list based on their population
	 */
	@RequestMapping("/sort-by-population")
	public ModelAndView sortByPopulation() {
		ModelAndView mv = new ModelAndView("search-results");
		
		populationClickCounter++;
		
		if (populationClickCounter % 2 != 0) {
			
			Comparator<Country> sortByPopulationDesc = (Country c1, Country c2) -> Integer.compare(c2.getPopulation(), c1.getPopulation());
		
			Collections.sort(countryList, sortByPopulationDesc);
		} else {
			Comparator<Country> sortByPopulationAsc = (Country c1, Country c2) -> Integer.compare(c1.getPopulation(), c2.getPopulation());
			
			Collections.sort(countryList, sortByPopulationAsc);
		}
		
		mv.addObject("results", countryList);
		
		return mv;
	}
	
	// Searches database for userId, sets currentUser to that userId if present
	// Otherwise sends user back to index page with user does not exist message
	@RequestMapping("/login")
	public ModelAndView login(@RequestParam("userId") int userId) {
		ModelAndView mv = new ModelAndView();
		
		currentUser = uRepo.findById(userId).orElse(null);
		
		if(currentUser == null) {
			mv.setViewName("index");
			mv.addObject("user", "The userID does not exist. Please register or login as a guest.");
			return mv;
		} else {
			mv.setViewName("start-page");
			return mv;
		}		
	}
	
	// Logins user as a guest, does not set currentUser to a user in the database
	@RequestMapping("/login-guest")
	public ModelAndView loginAsGuest() {
		ModelAndView mv = new ModelAndView("start-page");
		
		return mv;
	}
	
	// Creates a new user and userID
	@RequestMapping("new-user")
	public ModelAndView newUser() {
		ModelAndView mv = new ModelAndView("start-page");
		
		currentUser = new User();
		uRepo.save(currentUser);
		
		return mv;
	}
	
	/**
	 * Clears the country list and random countries list if user goes back to the start page
	 */
	@RequestMapping("/start-page")
	public ModelAndView backToStartPage() {
		ModelAndView mv = new ModelAndView("start-page");
		
		countryList.clear();
		randomCountries.clear();
		
		return mv;
	}
	
	/**
	 * Clears the country list and random countries list if user logs out and goes back to the initial login page
	 */
	@RequestMapping("/log-out")
	public ModelAndView logOut() {
		ModelAndView mv = new ModelAndView("index");
		
		countryList.clear();
		randomCountries.clear();
		
		return mv;
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
