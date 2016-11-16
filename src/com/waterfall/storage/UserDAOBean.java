package com.waterfall.storage;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.waterfall.models.UserModel;

@Stateful
public class UserDAOBean {

	@PersistenceContext
	private EntityManager em;

	public boolean storeUser(UserModel userModel) {

		if (em.merge(userModel) != null) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public List<UserModel> getAllUsers() {
		return em.createNamedQuery("UserModel.findAll").getResultList();
	}

	public UserModel getUserByUsername(String userToCheckInDatabase) {
		try {
			UserModel userModel = (UserModel) em.createNamedQuery("UserModel.findByUsername")
					.setParameter("username", userToCheckInDatabase).getSingleResult();
			return userModel;
		} catch (NoResultException e) {
			return null;
		}
	}

	public boolean isEmailInDatabaseUnique(String userEmail) {
		try {
			if (em.createNamedQuery("UserModel.findByEmail").setParameter("email", userEmail)
					.getSingleResult() != null) {
				return false;
			}
		} catch (NoResultException e) {
			return true;
		}
		return true;
	}

	public boolean isUsernameInDatabaseUnique(String username) {
		UserModel userModel = new UserModel();
		userModel.setUsername(username);
		if (getUserByUsername(userModel.getUsername()) == null) {
			return true;
		}
		return false;
	}

	public UserModel getUserById(Long userId) {
		return em.find(UserModel.class, userId);
	}

	@SuppressWarnings("unchecked")
	public List<UserModel> getUsersByGender(String string) {
		return em.createNamedQuery("UserModel.findByGender").setParameter("gender", string).getResultList();
	}

	public void deleteUser(UserModel user) {
		user = em.merge(user);
		em.remove(user);
	}

}
