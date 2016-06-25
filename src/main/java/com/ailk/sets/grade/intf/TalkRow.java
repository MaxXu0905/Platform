package com.ailk.sets.grade.intf;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TalkRow implements Serializable {

	private String school; // 学校
	private String city; // 城市
	private String address; // 宣讲具体地点、教室等
	private Integer seats; // 预计座位数
	private String date; // 宣讲日期
	private String beginTime; // 宣讲预计开始时间
	private String endTime; // 预计结束时间
	private int signalStrength; // 信号强度

	public TalkRow() {
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getSeats() {
		return seats;
	}

	public void setSeats(Integer seats) {
		this.seats = seats;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getSignalStrength() {
		return signalStrength;
	}

	public void setSignalStrength(int signalStrength) {
		this.signalStrength = signalStrength;
	}

}
