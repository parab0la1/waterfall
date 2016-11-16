package com.waterfall.validators;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.waterfall.models.DropModel;
import com.waterfall.utils.ValidationMessageService;

@Stateless
public class CreateDropValidator {

	@EJB
	private ValidationMessageService validationMessageService;

	public boolean validateDrop(DropModel dropModel) {
		if (validateDropContent(dropModel.getContent())) {
			return true;
		}
		return false;

	}

	private boolean validateDropContent(String content) {
		if (content.trim().equals("")) {
			validationMessageService.errorMsg("Drop content empty");
			return false;
		}
		if (content.length() > 200) {
			validationMessageService.errorMsg("Drop cannot exceed 200 characters");
			return false;
		}

		return true;
	}

	public boolean validateRestDrop(String content) {
		if (content.trim().equals("") || content.length() > 200) {
			return false;
		}
		return true;
	}


}
