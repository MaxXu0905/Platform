package com.ailk.sets.grade.jdbc;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "campus_test_plan")
public class CampusTestPlan implements Serializable, Cloneable {

	@Id
	@Column(name = "device_id", nullable = false)
	private String deviceId;

	@Column(name = "base_url")
	private String baseUrl;

	@Column(name = "prefix", nullable = false)
	private String prefix;

	@Column(name = "execute_date", nullable = false)
	private Timestamp executeDate;

	@Column(name = "check_interval", nullable = false)
	private int checkInterval;

	@Column(name = "parallels", nullable = false)
	private int parallels;

	@Column(name = "wait_time", nullable = false)
	private int waitTime;

	@Column(name = "weixin_id")
	private String weixinId;

	@Column(name = "wifi_enabled", nullable = false)
	private boolean wifiEnabled;

	@Column(name = "longitude", nullable = false)
	private double longitude;

	@Column(name = "latitude", nullable = false)
	private double latitude;

	@Column(name = "distance", nullable = false)
	private double distance;

	@Column(name = "state", nullable = false)
	private int state;

	@Column(name = "stats")
	private String stats;

	@Column(name = "create_date", nullable = false)
	private Timestamp createDate;

	@Column(name = "active_date", nullable = false)
	private Timestamp activeDate;

	@Column(name = "network_type", nullable = false)
	private int networkType;

	@Column(name = "phone_type", nullable = false)
	private int phoneType;

	@Column(name = "subscriber_id")
	private String subscriberId;

	@Column(name = "model")
	private String model;

	@Column(name = "sys_release")
	private String release;

	@Column(name = "sys_mobile_enabled", nullable = false)
	private boolean sysMobileEnabled;

	@Column(name = "sys_wifi_enabled", nullable = false)
	private boolean sysWifiEnabled;

	@Column(name = "sys_longitude", nullable = false)
	private double sysLongitude;

	@Column(name = "sys_latitude", nullable = false)
	private double sysLatitude;

	@Column(name = "job_number", nullable = false)
	private int jobNumber;

	@Column(name = "ip_address")
	private String ipAddress;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Timestamp getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(Timestamp executeDate) {
		this.executeDate = executeDate;
	}

	public int getCheckInterval() {
		return checkInterval;
	}

	public void setCheckInterval(int checkInterval) {
		this.checkInterval = checkInterval;
	}

	public int getParallels() {
		return parallels;
	}

	public void setParallels(int parallels) {
		this.parallels = parallels;
	}

	public int getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}

	public String getWeixinId() {
		return weixinId;
	}

	public void setWeixinId(String weixinId) {
		this.weixinId = weixinId;
	}

	public boolean isWifiEnabled() {
		return wifiEnabled;
	}

	public void setWifiEnabled(boolean wifiEnabled) {
		this.wifiEnabled = wifiEnabled;
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

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStats() {
		return stats;
	}

	public void setStats(String stats) {
		this.stats = stats;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Timestamp getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Timestamp activeDate) {
		this.activeDate = activeDate;
	}

	public int getNetworkType() {
		return networkType;
	}

	public void setNetworkType(int networkType) {
		this.networkType = networkType;
	}

	public int getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(int phoneType) {
		this.phoneType = phoneType;
	}

	public String getSubscriberId() {
		return subscriberId;
	}

	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getRelease() {
		return release;
	}

	public void setRelease(String release) {
		this.release = release;
	}

	public boolean isSysMobileEnabled() {
		return sysMobileEnabled;
	}

	public void setSysMobileEnabled(boolean sysMobileEnabled) {
		this.sysMobileEnabled = sysMobileEnabled;
	}

	public boolean isSysWifiEnabled() {
		return sysWifiEnabled;
	}

	public void setSysWifiEnabled(boolean sysWifiEnabled) {
		this.sysWifiEnabled = sysWifiEnabled;
	}

	public double getSysLongitude() {
		return sysLongitude;
	}

	public void setSysLongitude(double sysLongitude) {
		this.sysLongitude = sysLongitude;
	}

	public double getSysLatitude() {
		return sysLatitude;
	}

	public void setSysLatitude(double sysLatitude) {
		this.sysLatitude = sysLatitude;
	}

	public int getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(int jobNumber) {
		this.jobNumber = jobNumber;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}
