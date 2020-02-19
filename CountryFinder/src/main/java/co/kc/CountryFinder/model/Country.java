package co.kc.CountryFinder.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {

	private String name;
	private String capital;
	private String region;
	private int population;
	private int area;

	public Country() {
		super();
	}

	public Country(String name, String capital, String region, int population, int area) {
		super();
		this.name = name;
		this.capital = capital;
		this.region = region;
		this.population = population;
		this.area = area;
	}

	public String getName() {
		return name;
	}

	public String getCapital() {
		return capital;
	}

	public String getRegion() {
		return region;
	}

	public int getPopulation() {
		return population;
	}

	public int getArea() {
		return area;
	}

	@Override
	public String toString() {
		return "Country [name=" + name + ", capital=" + capital + ", region=" + region + ", population=" + population
				+ ", area=" + area + "]";
	}

}
