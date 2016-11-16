package com.waterfall.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

@Stateless
public class DateService {

	LocalDate currentDate = LocalDate.now();

	List<Integer> yearList;
	List<Integer> ageList;
	List<Integer> dayList;

	public List<Integer> years() {
		yearList = new ArrayList<Integer>();
		for (int i = 1900; i <= currentDate.getYear(); i++) {
			yearList.add(i);
		}
		return yearList;
	}

	public List<Integer> ageList() {
		ageList = new ArrayList<Integer>();
		for (int i = 0; i <= 116; i++) {
			ageList.add(i);
		}
		return ageList;
	}

	public List<Integer> days() {
		dayList = new ArrayList<Integer>();
		for (int i = 1; i <= 31; i++) {
			dayList.add(i);
		}
		return dayList;
	}

	public LocalDate getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(LocalDate currentDate) {
		this.currentDate = currentDate;
	}

}
