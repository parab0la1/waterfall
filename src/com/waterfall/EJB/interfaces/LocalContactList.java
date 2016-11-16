package com.waterfall.EJB.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.waterfall.models.ContactListModel;
import com.waterfall.models.DropModel;
import com.waterfall.models.UserModel;


@Local
public interface LocalContactList {
	
	boolean storeContactList(ContactListModel contactListModel);

	List<DropModel> getContactDrops(ContactListModel contactListModel);

	void removeContactList(ContactListModel contactListModel);

	void removeContactFromContactList(ContactListModel contactListModel, UserModel contactToRemove);
	
}
