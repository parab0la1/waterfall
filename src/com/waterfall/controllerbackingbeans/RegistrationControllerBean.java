package com.waterfall.controllerbackingbeans;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.google.gson.Gson;
import com.waterfall.EJB.interfaces.LocalUser;
import com.waterfall.hashing.pbkdf2.PBKDF2;
import com.waterfall.models.UserModel;
import com.waterfall.utils.CountryService;
import com.waterfall.utils.DateService;
import com.waterfall.validators.RegistrationValidator;

import jersey.repackaged.com.google.common.collect.Lists;

@Named(value = "registrationControllerBean")
@RequestScoped
public class RegistrationControllerBean {

	private String firstName;
	private String lastName;
	private String username;
	private String city;
	private int birthYear;
	private int birthMonth;
	private int birthDay;
	private String email;
	private String gender;
	private String password;
	private String country;
	private List<String> allCountries;
	private List<Integer> years;
	private List<Integer> days;
	private ArrayList<String> errorMessages;
	private String errorAsJson;
	private UserModel userToStore;

	@Inject
	private LoginControllerBean loginControllerBean;

	@EJB
	private RegistrationValidator registrationValidator;

	@EJB
	private CountryService countryService;

	@EJB
	private DateService dateService;

	@EJB
	private LocalUser userEJB;

	@PostConstruct
	public void init() {
		setAllCountries(countryService.getAllCountries());
		setYears(Lists.reverse(dateService.years()));
		setDays(dateService.days());
		if (errorMessages == null) {
			errorMessages = new ArrayList<>();
		}

		birthYear = 0;
		birthMonth = 0;
		birthDay = 0;
	}

	public String registerNewUser() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		userToStore = new UserModel();
		userToStore.setFirstName(firstName);
		userToStore.setLastName(lastName);
		userToStore.setUsername(username);
		userToStore.setEmail(email);
		userToStore.setCity(city);
		userToStore.setCountry(country);
		userToStore.setGender(gender);
		userToStore.setPassword(password);
		errorMessages = registrationValidator.validateUserForRegistration(birthYear, birthMonth, birthDay,
				userToStore, errorMessages);
		if (errorMessages.isEmpty()) {
			storeUser(createUserToSave());
			return "index";

		} else {
			setErrorAsJson(new Gson().toJson(errorMessages));
			errorMessages.clear();
			return "reg-new-user";
		}
	}

	private UserModel createUserToSave()
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		userToStore.setPassword(PBKDF2.generatePasswordHash(userToStore.getVisiblePassword()));
		@SuppressWarnings("deprecation")
		Date birthDate = new Date((birthYear - 1900), (birthMonth - 1), birthDay);
		userToStore.setBirthdate(birthDate);
		return userToStore;
	}

	private void storeUser(UserModel userModel) {

		userEJB.storeUser(userModel);
		userModel = userEJB.getUserByUsername(userModel.getUsername());
		userEJB.setUserInSession("loggedInUser", userModel);
		loginControllerBean.setLoggedInUser(userModel);
		errorMessages.clear();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountry() {
		return country;
	}

	public List<String> getAllCountries() {
		return allCountries;
	}

	public int getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}

	public int getBirthMonth() {
		return birthMonth;
	}

	public void setBirthMonth(int birthMonth) {
		this.birthMonth = birthMonth;
	}

	public int getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(int birthDay) {
		this.birthDay = birthDay;
	}

	public void setAllCountries(List<String> allCountries) {
		this.allCountries = allCountries;
	}

	public List<Integer> getYears() {
		return years;
	}

	public void setYears(List<Integer> years) {
		this.years = years;
	}

	public List<Integer> getDays() {
		return days;
	}

	public void setDays(List<Integer> days) {
		this.days = days;
	}

	public ArrayList<String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(ArrayList<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public String getErrorAsJson() {
		return errorAsJson;
	}

	public void setErrorAsJson(String errorAsJson) {
		this.errorAsJson = errorAsJson;
	}

}
