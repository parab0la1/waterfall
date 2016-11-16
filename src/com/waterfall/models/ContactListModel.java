package com.waterfall.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="contactlistmodel")
@NamedQuery(name="ContactListModel.findAll", query="SELECT c FROM ContactListModel c")
public class ContactListModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long contactlistid;
	
	@ManyToMany
	@JoinTable(name = "user_list", joinColumns = 
	@JoinColumn(name = "contactlistid", referencedColumnName = "contactlistid"), inverseJoinColumns = 
	@JoinColumn(name = "userid", referencedColumnName = "userid"))
	private List<UserModel> contacts;

	private String contactlistname;

	@ManyToOne
	@JoinColumn(name = "contactlistownerid")
	private UserModel contactlistowner;
	
	public ContactListModel() {
	}

	public void addContact(UserModel contact){
		this.contacts.add(contact);
	}
	public Long getId() {
		return this.contactlistid;
	}

	public void setId(Long contactlistid) {
		this.contactlistid = contactlistid;
	}

	public String getContactlistname() {
		return this.contactlistname;
	}

	public void setContactlistname(String contactlistname) {
		this.contactlistname = contactlistname;
	}

	public UserModel getOwner() {
		return this.contactlistowner;
	}

	public void setOwner(UserModel owner) {
		this.contactlistowner = owner;
	}

	public List<UserModel> getContacts() {
		return contacts;
	}

	public void setContacts(List<UserModel> contacts) {
		this.contacts = contacts;
	}

}