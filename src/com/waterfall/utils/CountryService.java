package com.waterfall.utils;

import java.util.ArrayList;
import java.util.Locale;

import javax.ejb.Stateless;

@Stateless
public class CountryService {

	public ArrayList<String> getAllCountries() {
		ArrayList<String> countries = new ArrayList<>();
		String[] countryCodes = Locale.getISOCountries();
		for (int i = 0; i < countryCodes.length; i++) {
			Locale country = new Locale("en", countryCodes[i]);
			Locale.setDefault(country);
			countries.add(country.getDisplayCountry());
		}
		
		return countries;
	}
}
