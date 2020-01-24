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
	
	static ArrayList<Country> countryList = new ArrayList<>();
	static Country randomCountry;
	
	RestTemplate rt = new RestTemplate();
	
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
	
	@RequestMapping("/sort-by-name")
	public ModelAndView sortByName() {
		ModelAndView mv = new ModelAndView("search-results");
		
		Comparator<Country> sortByName = (Country c1, Country c2) -> c1.getName().compareTo(c2.getName());
		
		Collections.sort(countryList, sortByName);
		
		mv.addObject("results", countryList);
		
		return mv; 
	}
	
	@RequestMapping("/sort-by-capital")
	public ModelAndView sortByCapital() {
		ModelAndView mv = new ModelAndView("search-results");
		
		Comparator<Country> sortByCapital = (Country c1, Country c2) -> c1.getCapital().compareTo(c2.getCapital());
		
		Collections.sort(countryList, sortByCapital);
		
		mv.addObject("results", countryList);
				
		return mv;
	}
	
	@RequestMapping("/sort-by-population")
	public ModelAndView sortByPopulation() {
		ModelAndView mv = new ModelAndView("search-results");
		
		Comparator<Country> sortByPopulation = (Country c1, Country c2) -> Integer.compare(c1.getPopulation(), c2.getPopulation());
		
		Collections.sort(countryList, sortByPopulation);
		
		mv.addObject("results", countryList);
		
		return mv;
	}
	
	@RequestMapping("/main-page")
	public ModelAndView homePage() {
		ModelAndView mv = new ModelAndView("index");
		
		countryList.clear();
		
		return mv;
	}
	
	public static void addtoCountryList(ResponseEntity<Country[]> response) {
		for (Country c : response.getBody()) {
			countryList.add(c);
		}
	}
}
