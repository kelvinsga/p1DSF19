package edu.uprm.cse.datastructures.cardealer.model;

import java.util.Comparator;

public class CarComparator implements Comparator<Car> {

	@Override
	public int compare(Car o1, Car o2) {		
		//Properties of each car to be sorted in alphabetic order.
		String vehicle1= o1.getCarBrand() + "-" + o1.getCarModel() +"-"+ o1.getCarModelOption();
		String vehicle2= o2.getCarBrand() + "-" + o2.getCarModel() +"-"+ o2.getCarModelOption();
		
		return vehicle1.compareTo(vehicle2);
	}

}
