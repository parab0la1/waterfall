package com.waterfall.validators;

import javax.ejb.Stateless;

import com.waterfall.models.UserModel;

@Stateless
public class LoginValidator {

	public boolean validateUserPassword(UserModel userFromDatabase,UserModel userToCheckInDatabase) {
		if(userToCheckInDatabase.getVisiblePassword().equals(userFromDatabase.getVisiblePassword())){
			return true;
		}
		return true;
	}
	

}
