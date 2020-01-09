package co.kc.CountryFinder.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryResponse {

	private ArrayList<Country> list;

	public CountryResponse() {
		super();
	}

	public CountryResponse(ArrayList<Country> list) {
		super();
		this.list = list;
	}

	public ArrayList<Country> getList() {
		return list;
	}

	public void setList(ArrayList<Country> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "CountryResponse [list=" + list + "]";
	}

}
