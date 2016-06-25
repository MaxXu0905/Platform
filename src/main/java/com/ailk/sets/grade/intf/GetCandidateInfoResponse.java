package com.ailk.sets.grade.intf;

import java.io.Serializable;
import java.util.List;

public class GetCandidateInfoResponse extends BaseResponse {

	private static final long serialVersionUID = -354815214984281461L;

	public static class UserInfo implements Serializable {
		private static final long serialVersionUID = 1L;
		private String key;
		private String value;

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	private String name; // 姓名
	private String email; // E-mail
	private List<UserInfo> infos; // 用户基本信息

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<UserInfo> getInfos() {
		return infos;
	}

	public void setInfos(List<UserInfo> infos) {
		this.infos = infos;
	}

}
