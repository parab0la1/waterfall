package com.waterfall.serviceEJB;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.waterfall.EJB.interfaces.LocalFilter;
import com.waterfall.models.DropModel;
import com.waterfall.models.FilterModel;
import com.waterfall.models.UserModel;
import com.waterfall.serviceEJB.enums.Method;
import com.waterfall.storage.DropDAOBean;
import com.waterfall.storage.FilterDAOBean;
import com.waterfall.storage.UserDAOBean;
import com.waterfall.utils.DateService;

@Stateless
public class FilterServiceEJB implements LocalFilter {

	@EJB
	DropDAOBean dropDAOBean;

	@EJB
	UserDAOBean userDAOBean;

	@EJB
	FilterDAOBean filterDAOBean;

	@EJB
	DateService dateService;

	private ArrayList<DropModel> dropListFromSearch;
	private FilterModel filter;

	@Override
	public FilterModel getFilterById(Long filterid) {
		return filterDAOBean.getFilterById(filterid);
	}

	@Override
	public void saveFilterAsPool(FilterModel filterModel) {
		filterDAOBean.storeFilterAsPool(filterModel);
	}

	@Override
	public List<DropModel> filterDrops(FilterModel filterModel) {

		filter = filterModel;

		dropListFromSearch = (ArrayList<DropModel>) getInitialList(splitTagList());
		dropListFromSearch = (ArrayList<DropModel>) removeDuplicatesFromSearchList();

		return dropListFromSearch;
	}

	private List<DropModel> getInitialList(String[] searchWords) {
		dropListFromSearch = new ArrayList<DropModel>();

		if (filter.getIsFilteredByMale() || filter.getIsFilteredByFemale() || filter.getIsFilteredByOther()) {
			dropListFromSearch = (ArrayList<DropModel>) getDropsByGender();
		} else {
			dropListFromSearch.addAll(dropDAOBean.getAllDrops());
		}
		
		boolean startAgeIsZero = filter.getStartAge() == 0;
		boolean endAgeIsZero = filter.getEndAge() == 0;
		boolean startAgeIsHigherThanEndAge = filter.getStartAge() > filter.getEndAge();

		if (!(startAgeIsZero && endAgeIsZero) && !(startAgeIsHigherThanEndAge)) {
			dropListFromSearch = (ArrayList<DropModel>) filterByAgeSpan(filter.getStartAge(), filter.getEndAge());
		}
		
		dropListFromSearch = (ArrayList<DropModel>) filterInitialList(searchWords, dropListFromSearch);
		
		return dropListFromSearch;
		
	}
	
	@SuppressWarnings("deprecation")
	private List<DropModel> filterByAgeSpan(int startAge, int endAge) {

		int month = dateService.getCurrentDate().getMonthValue() - 1;
		int day = dateService.getCurrentDate().getDayOfMonth();
		startAge = dateService.getCurrentDate().getYear() - startAge;
		endAge = dateService.getCurrentDate().getYear() - endAge;

		Date endDate = new Date(startAge - 1900, month, day);
		Date startDate = new Date(endAge - 1900, month, day);

		for (int i = 0; i < dropListFromSearch.size(); i++) {
			int birthYear = dropListFromSearch.get(i).getOwner().getBirthdate().getYear();
			int birthMonth = dropListFromSearch.get(i).getOwner().getBirthdate().getMonth();
			int birthDay = dropListFromSearch.get(i).getOwner().getBirthdate().getDate();

			Date userBirthDate = new Date(birthYear, birthMonth, birthDay);
			if (userBirthDate.before(startDate) || userBirthDate.after(endDate)) {
				dropListFromSearch.remove(i);
				i--;
			}
		}
		return dropListFromSearch;
	}
	
	private List<DropModel> filterInitialList(String[] searchWords, ArrayList<DropModel> dropListFromSearch) {
		
		if (!searchWords[0].isEmpty()) {
			dropListFromSearch = (ArrayList<DropModel>) filterList(dropListFromSearch, searchWords);
		}

		if (!filter.getFirstName().isEmpty()) {
			dropListFromSearch = (ArrayList<DropModel>) filterBySpecificInput(dropListFromSearch, Method.FIRSTNAME,
					filter.getFirstName());
		}

		if (!filter.getLastName().isEmpty()) {
			dropListFromSearch = (ArrayList<DropModel>) filterBySpecificInput(dropListFromSearch, Method.LASTNAME,
					filter.getLastName());
		}

		if (!filter.getUsername().isEmpty()) {
			dropListFromSearch = (ArrayList<DropModel>) filterBySpecificInput(dropListFromSearch, Method.USERNAME,
					filter.getUsername());
		}

		if (!filter.getCity().isEmpty()) {
			dropListFromSearch = (ArrayList<DropModel>) filterBySpecificInput(dropListFromSearch, Method.CITY,
					filter.getCity());
		}

		if (!filter.getCountry().isEmpty()) {
			dropListFromSearch = (ArrayList<DropModel>) filterBySpecificInput(dropListFromSearch, Method.COUNTRY,
					filter.getCountry());
		}

		return (ArrayList<DropModel>) dropListFromSearch;
	}

	private List<DropModel> filterBySpecificInput(ArrayList<DropModel> dropListFromSearch, Method methodName,
			String keyWord) {
		List<DropModel> filteredList = new ArrayList<DropModel>();

		for (DropModel drop : dropListFromSearch) {
			switch (methodName) {
			case FIRSTNAME:
				if (drop.getOwner().getFirstName().equalsIgnoreCase(keyWord)) {
					filteredList.add(drop);
				}
				break;
			case LASTNAME:
				if (drop.getOwner().getLastName().equalsIgnoreCase(keyWord)) {
					filteredList.add(drop);
				}
				break;
			case USERNAME:
				if (drop.getOwner().getUsername().equalsIgnoreCase(keyWord)) {
					filteredList.add(drop);
				}
				break;
			case CITY:
				if (drop.getOwner().getCity().equalsIgnoreCase(keyWord)) {
					filteredList.add(drop);
				}
				break;
			case COUNTRY:
				if (drop.getOwner().getCountry().equalsIgnoreCase(keyWord)) {
					filteredList.add(drop);
				}
				break;
			}
		}
		return filteredList;
	}

	private List<DropModel> getDropsByGender() {
		List<UserModel> users = new ArrayList<UserModel>();
		List<DropModel> drops = new ArrayList<DropModel>();
		if (filter.getIsFilteredByMale()) {
			users.addAll(userDAOBean.getUsersByGender("Male"));
		}

		if (filter.getIsFilteredByFemale()) {
			users.addAll(userDAOBean.getUsersByGender("Female"));
		}

		if (filter.getIsFilteredByOther()) {
			users.addAll(userDAOBean.getUsersByGender("Other"));
		}

		for (UserModel user : users) {
			drops.addAll(user.getDrops());
		}
		return (ArrayList<DropModel>) drops;
	}

	private List<DropModel> removeDuplicatesFromSearchList() {
		for (int i = 0; i < dropListFromSearch.size(); i++) {
			for (int j = i + 1; j < dropListFromSearch.size(); j++) {
				if (dropListFromSearch.get(i).getDropId().equals(dropListFromSearch.get(j).getDropId())) {
					dropListFromSearch.remove(j);
					j--;
				}
			}
		}
		return dropListFromSearch;
	}

	private List<DropModel> filterList(ArrayList<DropModel> dropListFromSearch, String[] searchWords) {
		List<DropModel> filteredList = new ArrayList<DropModel>();
		for (int i = 0; i < dropListFromSearch.size(); i++) {
			boolean dropContainsAllWords = dropContainsAllSearchWords(dropListFromSearch.get(i), searchWords);

			if (dropContainsAllWords) {
				filteredList.add(dropListFromSearch.get(i));
			}
		}
		return filteredList;
	}

	private boolean dropContainsAllSearchWords(DropModel drop, String[] searchWords) {
		for (int i = 0; i < searchWords.length; i++) {
			if (!drop.getContent().toLowerCase().contains(searchWords[i].toLowerCase())
					&& !userInformationContainsSearchWords(drop.getOwner(), searchWords[i])) {
				return false;
			}
		}

		return true;
	}

	private boolean userInformationContainsSearchWords(UserModel owner, String searchWord) {
		String userInfo = owner.getFirstName() + owner.getLastName() + owner.getUsername() + owner.getCity()
				+ owner.getCountry();

		if (userInfo.toLowerCase().contains(searchWord.toLowerCase())) {
			return true;
		}
		return false;
	}

	private String[] splitTagList() {
		String[] searchWords = filter.getSearchWords().split(",");
		return searchWords;
	}

}
