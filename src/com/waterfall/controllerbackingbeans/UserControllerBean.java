package com.waterfall.controllerbackingbeans;

import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.waterfall.EJB.interfaces.LocalContactList;
import com.waterfall.EJB.interfaces.LocalUser;
import com.waterfall.hashing.pbkdf2.PBKDF2;
import com.waterfall.models.ContactListModel;
import com.waterfall.models.UserModel;
import com.waterfall.utils.ValidationMessageService;

import jersey.repackaged.com.google.common.collect.Lists;


@Named(value = "userControllerBean")
@SessionScoped
public class UserControllerBean implements Serializable {

	private static final long serialVersionUID = 3773988104720989698L;
	private List<ContactListModel> contactLists;
	private boolean addContactNotValid;
	private UserModel loggedInUser;
	private String contactListName;
	private String usernameSearch;
	private String errorMessage;

	@EJB
	private ValidationMessageService validationMessageService;

	@EJB
	private LocalUser userEJB;

	@EJB
	private LocalContactList contactListEJB;
	
	@Inject
	LoginControllerBean loginControllerBean;

	@PostConstruct
	public void init() {
		try{
			loggedInUser = userEJB.getUserFromSession("loggedInUser");

			if (contactLists == null) {
				contactLists = new ArrayList<ContactListModel>();
				contactLists = Lists.reverse(loggedInUser.getContactList());
			}
		}catch(Exception e){
			try {
				FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
				FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
		
	}

	public String removeUserFromContactList(ContactListModel contactListModel, UserModel contactToRemove) {
		contactListEJB.removeContactFromContactList(contactListModel, contactToRemove);
		loginControllerBean.setLoggedInUser(userEJB.getUserById(loggedInUser.getUserid()));
		return "profile-page";
	}

	public String removeContactList(ContactListModel contactListModel) {
		contactListEJB.removeContactList(contactListModel);
		contactLists.remove(contactListModel);
		loginControllerBean.setLoggedInUser(userEJB.getUserById(loggedInUser.getUserid()));
		return "profile-page";
	}

	public String updateUser(UserModel user)
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		user.setPassword(PBKDF2.generatePasswordHash(user.getVisiblePassword()));
		userEJB.storeUser(user);
		return "profile-page";
	}

	private UserModel searchUserByUsername() {
		UserModel userToSearch = new UserModel();
		userToSearch.setUsername(usernameSearch);
		userToSearch = userEJB.getUserByUsername(usernameSearch);
		return userToSearch;
	}

	public String createNewContactlist() {

		if (contactListName == null || contactListName.trim().equals("")) {
			validationMessageService.errorMsg("Please enter a name for your list");
		} else {
			ContactListModel contactListModel = new ContactListModel();
			loggedInUser = userEJB.getUserFromSession("loggedInUser");
			contactListModel.setContactlistname(contactListName);
			contactListModel.setOwner(loggedInUser);
			loggedInUser.getContactList().add(contactListModel);
			contactListEJB.storeContactList(contactListModel);
			validationMessageService.successMsg("Contactlist created");
			contactLists = Lists.reverse(userEJB.getUserById(loggedInUser.getUserid()).getContactList());
			loginControllerBean.setLoggedInUser(userEJB.getUserById(loggedInUser.getUserid()));
		}

		contactListName = null;
		return "profile-page";
	}

	public String addContactToList(ContactListModel contactListModel) {
		String errorMessage = userEJB.validateUserContactList(contactListModel, usernameSearch);
		if (errorMessage.equals("ok")) {
			validationMessageService.successMsg("Contact added");

			contactListModel.addContact(searchUserByUsername());
			contactListEJB.storeContactList(contactListModel);
			loginControllerBean.setLoggedInUser(userEJB.getUserById(loggedInUser.getUserid()));

		} else {
			validationMessageService.errorMsg(errorMessage);

		}

		usernameSearch = null;
		return "profile-page";
	}

	public String getUsernameSearch() {
		return usernameSearch;
	}

	public void setUsernameSearch(String usernameSearch) {
		this.usernameSearch = usernameSearch;
	}

	public UserModel getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(UserModel loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public String getContactListName() {
		return contactListName;
	}

	public void setContactListName(String contactListName) {
		this.contactListName = contactListName;
	}

	public List<ContactListModel> getContactLists() {
		return contactLists;
	}

	public void setContactLists(List<ContactListModel> contactLists) {
		this.contactLists = contactLists;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean isAddContactNotValid() {
		return addContactNotValid;
	}

	public void setAddContactNotValid(boolean addContactNotValid) {
		this.addContactNotValid = addContactNotValid;
	}

}
