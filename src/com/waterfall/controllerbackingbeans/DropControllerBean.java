package com.waterfall.controllerbackingbeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.waterfall.EJB.interfaces.LocalComment;
import com.waterfall.EJB.interfaces.LocalContactList;
import com.waterfall.EJB.interfaces.LocalDrop;
import com.waterfall.EJB.interfaces.LocalUser;
import com.waterfall.models.DropModel;
import com.waterfall.validators.CreateDropValidator;

import jersey.repackaged.com.google.common.collect.Lists;

@Named(value = "dropControllerBean")
@SessionScoped
public class DropControllerBean implements Serializable {

	private static final long serialVersionUID = 2772076160829404613L;

	private String content;
	private String commentContent;
	private List<DropModel> dropList;
	
	@Inject
	FilterControllerBean filterControllerBean;
	
	@EJB
	private CreateDropValidator createDropValidator;

	@EJB
	private LocalUser userEJB;

	@EJB
	private LocalDrop dropEJB;
	@EJB
	private LocalContactList contactListEJB;

	@EJB
	private LocalComment commentEJB;
	

	@PostConstruct
	public void init() {
		dropList = Lists.reverse(dropEJB.getAllDrops());
	}

	public String createNewDrop() {
		DropModel dropModel = new DropModel();
		
		dropModel.setContent(content);
		dropModel.setOwner(userEJB.getUserFromSession("loggedInUser"));

		if(createDropValidator.validateDrop(dropModel)){			
			dropEJB.storeDrop(dropModel);
		}
		
		dropList = Lists.reverse(dropEJB.getAllDrops());
		content = null;
		
		return "index";
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<DropModel> getDropList() {
		return dropList;
	}

	public void setDropList(List<DropModel> dropList) {
		this.dropList = dropList;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

}
