package com.waterfall.EJB.interfaces;

import javax.ejb.Local;

import com.waterfall.models.CommentModel;

@Local
public interface LocalComment {

	boolean storeComment(CommentModel commentModel);
}
