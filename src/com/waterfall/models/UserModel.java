package com.waterfall.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name="usermodel")
@NamedQueries({ @NamedQuery(name = "UserModel.findAll", query = "SELECT u FROM UserModel u"),
		@NamedQuery(name = "UserModel.findByUsername", query = "SELECT u FROM UserModel u WHERE u.username LIKE :username"),
		@NamedQuery(name = "UserModel.findByEmail", query = "SELECT u FROM UserModel u WHERE u.email LIKE :email"),
		@NamedQuery(name = "UserModel.findByCountry", query = "SELECT u FROM UserModel u WHERE u.country LIKE :country"),
		@NamedQuery(name = "UserModel.findByCity", query = "SELECT u FROM UserModel u WHERE u.city LIKE :city"),
		@NamedQuery(name = "UserModel.findByFirstName", query = "SELECT u FROM UserModel u WHERE u.firstName LIKE :firstname"),
		@NamedQuery(name = "UserModel.findByLastName", query = "SELECT u FROM UserModel u WHERE u.lastName LIKE :lastname"),
		@NamedQuery(name = "UserModel.findByGender", query = "SELECT u FROM UserModel u WHERE u.gender LIKE :gender"),
		@NamedQuery(name = "UserModel.findByBirthdate", query = "SELECT u FROM UserModel u WHERE u.birthdate BETWEEN :enddate AND :startdate") 
})
@XmlRootElement
public class UserModel implements Serializable {

	private static final long serialVersionUID = 5461470282886888730L;

	@Id
	private Long userid;

	@Temporal(TemporalType.DATE)
	private Date birthdate;

	private String city;

	private String email;

	private String firstName;

	private String gender;

	private String lastName;
	
	@XmlTransient
	private String password;

	private String username;

	private String country;
	
	@Transient
	private List<LinkModel> links = new ArrayList<>();

	@OneToMany(mappedBy = "contactlistowner", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<ContactListModel> contactList;

	@OneToMany(mappedBy = "dropowner", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<DropModel> dropList;
	
	@OneToMany(mappedBy = "filterowner", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<FilterModel> filterList;
	
	@ManyToMany(mappedBy = "contacts")
	private List<ContactListModel> userAsContact;

	public UserModel() {

	}

	@XmlTransient
	public List<DropModel> getDrops() {
		return dropList;
	}

	public void setDrops(List<DropModel> dropModels) {
		this.dropList = dropModels;
	}

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Date getBirthdate() {
		return this.birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}


	@XmlElement(name="password")
	public String getPassword() {
		return null;
	}

	public void setPassword(String passwordhash) {
		this.password = passwordhash;
	}

	@XmlTransient
	public List<ContactListModel> getContactList() {
		return contactList;
	}

	public void setContactList(List<ContactListModel> contactList) {
		this.contactList = contactList;
	}
	
	@XmlTransient
	public List<FilterModel> getFilterList() {
		return filterList;
	}
	public void setFilterList(List<FilterModel> filterList) {
		this.filterList = filterList;
	}
	
	@XmlTransient
	public List<ContactListModel> getContacts() {
		return userAsContact;
	}

	public void setContacts(List<ContactListModel> userAsContact) {
		this.userAsContact = userAsContact;
	}
	
	@Override
	public String toString() {
		return "UserModel [userid=" + userid + ", birthdate=" + birthdate + ", city=" + city + ", email=" + email
				+ ", firstName=" + firstName + ", gender=" + gender + ", lastName=" + lastName + ", password="
				+ password + ", username=" + username + ", country=" + country + ", dropList=" + dropList + "]";
	}
	
	public String getVisiblePassword() {
		return password;
	}

	public List<LinkModel> getLinks() {
		return links;
	}

	public void setLinks(List<LinkModel> links) {
		this.links = links;
	}
	
	public void addLink(LinkModel link) {
		this.links.add(link);
	}
	
	

}