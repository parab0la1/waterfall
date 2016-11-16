package com.waterfall.serviceEJB;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.waterfall.EJB.interfaces.LocalContactList;
import com.waterfall.models.ContactListModel;
import com.waterfall.models.DropModel;
import com.waterfall.models.UserModel;
import com.waterfall.storage.ContactListDAOBean;

@Stateless
public class ContactListServiceEJB implements LocalContactList {

	@EJB
	private ContactListDAOBean contactListDAOBean;

	@Override
	public boolean storeContactList(ContactListModel contactListModel) {

		return contactListDAOBean.storeContactList(contactListModel);
	}

	@Override
	public List<DropModel> getContactDrops(ContactListModel contactListModel) {
		List<DropModel> contactDrops = new ArrayList<DropModel>();
		for (UserModel userModel : contactListModel.getContacts()) {
			contactDrops.addAll(userModel.getDrops());
		}
		return contactDrops;
	}

	@Override
	public void removeContactList(ContactListModel contactListModel) {
		contactListDAOBean.removeContactList(contactListModel);

	}

	@Override
	public void removeContactFromContactList(ContactListModel contactListModel, UserModel contactToRemove) {
		contactListModel.getContacts().remove(contactToRemove);
		contactListDAOBean.storeContactList(contactListModel);

	}

}
