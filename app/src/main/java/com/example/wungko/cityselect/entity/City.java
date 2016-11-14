package com.example.wungko.cityselect.entity;

/**
 *
 */
public class City {

	public static final int TYPE_1 = 0XFF01;
	public static final int TYPE_2 = 0XFF02;
	public static final int TYPE_3 = 0XFF03;
	public static final int TYPE_4 = 0XFF04;


	public String cityName;

	public String citySpell;

	public City() {

	}

	public City(int type, String cityName, String citySpell) {
		this.cityName = cityName;

		this.citySpell = citySpell;
	}



}
