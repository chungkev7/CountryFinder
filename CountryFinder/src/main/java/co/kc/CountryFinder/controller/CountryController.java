package co.kc.CountryFinder.controller;

import java.util.ArrayList;

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
	
	private static ArrayList<Country> countryList = new ArrayList<>();
	private static Country randomCountry;
	
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
						
			mv.addObject("results", response.getBody());
			
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
		
		mv.addObject("results", response.getBody());
		
		return mv;
	}
	
	
	@RequestMapping("/random-country")
	public ModelAndView getRandomCountryByRegion(@RequestParam("region") String region) {
		ModelAndView mv = new ModelAndView("pop-guess");
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-RapidAPI-Key", countryKey);
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		
		ResponseEntity<Country[]> response = rt.exchange("https://restcountries-v1.p.rapidapi.com/region/" + region, HttpMethod.GET, entity, Country[].class);
					
		for (Country c : response.getBody()) {
			countryList.add(c);
		}
		
		randomCountry = countryList.get( (int)Math.random() * (countryList.size() - 1));
		
		mv.addObject("randomCountry", randomCountry);
		
		return mv;
	}
	
	@RequestMapping("/pop-guess")
	public ModelAndView populationGuess(@RequestParam("population") int population) {
		ModelAndView mv = new ModelAndView("index");
		
		if (population != randomCountry.getPopulation()) {
			System.out.println("Sorry, your guess is incorrect. You are off by " + ((int)Math.abs(population - randomCountry.getPopulation())) + " citizens.");
		} else {
			System.out.println("You are correct!");
		}
		
		return mv;
	}
}
