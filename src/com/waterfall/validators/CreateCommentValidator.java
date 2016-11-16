package com.waterfall.validators;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.waterfall.models.CommentModel;
import com.waterfall.utils.ValidationMessageService;

@Stateless
public class CreateCommentValidator {
	
	@EJB
	private ValidationMessageService validationMessageService;

	public boolean validateComment(CommentModel commentModel) {
		return validateCommentContent(commentModel.getContent());	
	}
	
	public boolean validateCommentContent(String content){
		if(content.trim().equals("") || content == null){
			validationMessageService.errorMsg("Comment is empty");
			return false;
		}if(content.length() > 200){
			validationMessageService.errorMsg("Comment cannot exceed 200 characters");
			return false;
		}
			return true;
	}

	
}
