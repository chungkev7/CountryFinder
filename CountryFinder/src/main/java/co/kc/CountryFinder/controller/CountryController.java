package co.kc.CountryFinder.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import co.kc.CountryFinder.model.Country;

@Controller
public class CountryController {

	@Value("${country.key}")
	private String countryKey;
	
	RestTemplate rt = new RestTemplate();
	
	@RequestMapping("/")
	public ModelAndView homePage() {
		ModelAndView mv = new ModelAndView("index");
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-RapidAPI-Key", countryKey);
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

		ResponseEntity<Country[]> response = rt.exchange("https://restcountries-v1.p.rapidapi.com/name/United", HttpMethod.GET, entity, Country[].class);
		
		for (Country c : response.getBody()) {
			System.out.println(c);
		}
//		System.out.println(response.getBody());
		
		return mv;
	}
}
