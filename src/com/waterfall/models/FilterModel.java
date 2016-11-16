package com.waterfall.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the filtermodels database table.
 * 
 */
@Entity
@Table(name="filtermodel")
@NamedQueries({
	@NamedQuery(name="FilterModel.findAll", query="SELECT f FROM FilterModel f"),
	@NamedQuery(name="FilterModel.findFilterById", query="SELECT f FROM FilterModel f WHERE f.filterid = :filterid")
})

public class FilterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long filterid;

	private String city;

	private String country;

	private int endAge;

	private String firstName;

	private Boolean isFilteredByFemale;

	private Boolean isFilteredByMale;

	private Boolean isFilteredByOther;

	private String lastName;
	
	@JoinColumn(name = "filterownerid")
	@ManyToOne
	private UserModel filterowner;

	private String poolname;

	private String searchWords;

	private int startAge;

	private String username;

	public FilterModel() {
	}

	public Long getFilterid() {
		return this.filterid;
	}

	public void setFilterid(Long filterid) {
		this.filterid = filterid;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getEndAge() {
		return this.endAge;
	}

	public void setEndAge(int endAge) {
		this.endAge = endAge;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Boolean getIsFilteredByFemale() {
		return this.isFilteredByFemale;
	}

	public void setIsFilteredByFemale(Boolean isFilteredByFemale) {
		this.isFilteredByFemale = isFilteredByFemale;
	}

	public Boolean getIsFilteredByMale() {
		return this.isFilteredByMale;
	}

	public void setIsFilteredByMale(Boolean isFilteredByMale) {
		this.isFilteredByMale = isFilteredByMale;
	}

	public Boolean getIsFilteredByOther() {
		return this.isFilteredByOther;
	}

	public void setIsFilteredByOther(Boolean isFilteredByOther) {
		this.isFilteredByOther = isFilteredByOther;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPoolname() {
		return this.poolname;
	}

	public void setPoolname(String poolname) {
		this.poolname = poolname;
	}

	public String getSearchWords() {
		return this.searchWords;
	}

	public void setSearchWords(String searchWords) {
		this.searchWords = searchWords;
	}

	public int getStartAge() {
		return this.startAge;
	}

	public void setStartAge(int startAge) {
		this.startAge = startAge;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserModel getFilterowner() {
		return filterowner;
	}

	public void setFilterowner(UserModel filterowner) {
		this.filterowner = filterowner;
	}
	
	

}