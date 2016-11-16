package com.waterfall.EJB.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.waterfall.models.DropModel;

@Local
public interface LocalDrop {

	boolean storeDrop(DropModel dropModel);
	
	DropModel getDrop(Long dropId);

	List<DropModel> getAllDrops();

	void deleteDrop(DropModel dropModel);
	
	
}
