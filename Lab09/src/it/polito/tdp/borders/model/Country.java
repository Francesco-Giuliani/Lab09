package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.List;

public class Country {

	private int cCode;
	private String cId;
	private String cName;
	private List<Border> borders;
	
	public Country(int countryCode, String countryId, String countryName) {
		this.cCode = countryCode;
		this.cId = countryId;
		this.cName = countryName;
		this.borders = new ArrayList<>();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cCode;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (cCode != other.cCode)
			return false;
		return true;
	}

	public int getcCode() {
		return cCode;
	}

	public String getcId() {
		return cId;
	}

	public String getcName() {
		return cName;
	}

	public List<Border> getBorders() {
		return borders;
	}

	public void setcCode(int cCode) {
		this.cCode = cCode;
	}

	public void setcId(String cId) {
		this.cId = cId;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public void setBorders(List<Border> borders) {
		this.borders = borders;
	}

	@Override
	public String toString() {
		return "["+cCode + ", " + cId+"]"; //+ ", " + cName; //+ borders;
	}
	
	
}
