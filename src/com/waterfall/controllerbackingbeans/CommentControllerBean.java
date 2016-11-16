package com.waterfall.controllerbackingbeans;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.waterfall.EJB.interfaces.LocalComment;
import com.waterfall.EJB.interfaces.LocalDrop;
import com.waterfall.EJB.interfaces.LocalUser;
import com.waterfall.models.CommentModel;
import com.waterfall.validators.CreateCommentValidator;

@Named(value="commentControllerBean")
@RequestScoped
public class CommentControllerBean {
	
	private String content;
	private String creationDate;
	
	@EJB
	private LocalComment commentEJB;
	
	@EJB
	private LocalDrop dropEJB;
	
	@EJB
	private LocalUser userEJB;
	
	@EJB
	private CreateCommentValidator commentValidator;
	
	@Inject
	DropControllerBean dropControllerBean;
	
	public String createNewComment(Long dropId) {
		CommentModel commentModel = new CommentModel();
		commentModel.setContent(content);
		commentModel.setDropHost(dropEJB.getDrop(dropId));
		commentModel.setOwner(userEJB.getUserFromSession("loggedInUser"));
		
		if(commentValidator.validateComment(commentModel)){
			commentEJB.storeComment(commentModel);
			dropControllerBean.init();
		}
		content = null;
		return "index";
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

}
