package it.polito.tdp.borders.model;

public class Border {

	private int bId;
	private Country c1, c2;
	private int year;
	private int cType;
	
	public Border(int bId, Country c1, Country c2, int year) {
		this.bId = bId;
		this.c1 = c1;
		this.c2 = c2;
		this.year = year;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((c1 == null) ? 0 : c1.hashCode());
		result = prime * result + ((c2 == null) ? 0 : c2.hashCode());
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
		Border other = (Border) obj;
		if( 	( this.c1.equals(other.c1) && this.c2.equals(other.c2) ) ||
				( this.c1.equals(other.c2) && this.c2.equals(other.c1) ) )
			return true;
		return false;
	}

	public Country getC1() {
		return c1;
	}

	public Country getC2() {
		return c2;
	}

	public int getYear() {
		return year;
	}

	public void setC1(Country c1) {
		this.c1 = c1;
	}

	public void setC2(Country c2) {
		this.c2 = c2;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return bId + ", [" + c1 + "], [" + c2 + "], " + year;
	}	
	
}
