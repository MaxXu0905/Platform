package com.ailk.sets.grade.jdbc;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "ip_info")
public class IpInfo implements Serializable, Cloneable {

	@Id
	@Column(name = "ip", nullable = false)
	private String ip;

	@Column(name = "province", nullable = false)
	private String province;

	@Column(name = "city", nullable = false)
	private String city;

	@Column(name = "city_code", nullable = false)
	private String cityCode;

	@Column(name = "district")
	private String district;

	@Column(name = "street")
	private String street;

	@Column(name = "street_number")
	private String streetNumber;

	@Column(name = "longitude", nullable = false)
	private double longitude;

	@Column(name = "latitude", nullable = false)
	private double latitude;

	@Column(name = "net_name", nullable = false)
	private String netName;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getNetName() {
		return netName;
	}

	public void setNetName(String netName) {
		this.netName = netName;
	}

}
