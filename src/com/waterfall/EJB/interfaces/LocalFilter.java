package com.waterfall.EJB.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.waterfall.models.DropModel;
import com.waterfall.models.FilterModel;

@Local
public interface LocalFilter {
	
	List<DropModel> filterDrops(FilterModel filterModel);

	void saveFilterAsPool(FilterModel filterModel);

	FilterModel getFilterById(Long filterid);

}
