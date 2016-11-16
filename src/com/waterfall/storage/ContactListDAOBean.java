package com.waterfall.storage;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.waterfall.models.ContactListModel;

@Stateful
public class ContactListDAOBean {

	@PersistenceContext
	private EntityManager em;

	public boolean storeContactList(ContactListModel contactListModel) {
		if (em.merge(contactListModel) != null) {
			return true;
		}
		return false;

	}

	public void removeContactList(ContactListModel contactListModel) {
		contactListModel = em.merge(contactListModel);
		em.remove(contactListModel);

	}

}
