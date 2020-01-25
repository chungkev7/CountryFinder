package co.kc.CountryFinder.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

@Controller
public class CountryController {

	@Value("${country.key}")
	private String countryKey;
	
	RestTemplate rt = new RestTemplate();
	
	static ArrayList<Country> countryList = new ArrayList<>();
	static Country randomCountry;
	static int nameClickCounter = 0;
	static int capitalClickCounter = 0;
	static int populationClickCounter = 0;
	
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
	 * @param region: dropdown list of countries
	 * @return: a random country's population
	 */
	@RequestMapping("/random-country-population")
	public ModelAndView getRandomCountryByRegion(@RequestParam("region") String region) {
		ModelAndView mv = new ModelAndView("pop-guess");
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-RapidAPI-Key", countryKey);
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		
		ResponseEntity<Country[]> response = rt.exchange("https://restcountries-v1.p.rapidapi.com/region/" + region, HttpMethod.GET, entity, Country[].class);
					
		addtoCountryList(response);
		
		int countryNum = (int) (Math.random() * countryList.size());
		randomCountry = countryList.get(countryNum);
		
		mv.addObject("randomCountry", randomCountry);

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
	 * @param region: dropdown list of countries
	 * @return: a random country's capital
	 */
	@RequestMapping("/random-country-capital")
	public ModelAndView getRandomCountryCapital(@RequestParam("region") String region) {
		ModelAndView mv = new ModelAndView("capital-guess");
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-RapidAPI-Key", countryKey);
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		
		ResponseEntity<Country[]> response = rt.exchange("https://restcountries-v1.p.rapidapi.com/region/" + region, HttpMethod.GET, entity, Country[].class);

		addtoCountryList(response);
		
		int countryNum = (int) (Math.random() * countryList.size());
		randomCountry = countryList.get(countryNum);
		
		mv.addObject("randomCountry", randomCountry);
		
		return mv;
		
	}
	
	/**
	 * Generates a message to user if user guesses the capital correctly or incorrectly
	 * 
	 * @param population: compares the user's capital guess to the random country
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
	 * @param region: user indication (yes or not to continue)
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
	 * @param region: user indication (yes or not to continue)
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
	
	/**
	 * Clears the country list if user goes back to the main page
	 * 
	 * @return: an empty countryList 
	 */
	@RequestMapping("/main-page")
	public ModelAndView homePage() {
		ModelAndView mv = new ModelAndView("index");
		
		countryList.clear();
		
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
