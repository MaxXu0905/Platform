package com.ailk.sets.grade.intf;

@SuppressWarnings("serial")
public class GetTestPlanRequest extends BaseRequest {

	private String deviceId;
	private int networkType;
	private int phoneType;
	private String subscriberId;
	private String model;
	private String release;
	private boolean sysMobileEnabled;
	private boolean sysWifiEnabled;
	private double longitude;
	private double latitude;
	private String stats;
	private int jobNumber;
	private String ipAddress;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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

	public String getStats() {
		return stats;
	}

	public void setStats(String stats) {
		this.stats = stats;
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
