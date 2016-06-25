package com.ailk.sets.grade.intf.report;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class InterviewInfo implements Serializable {

	private String id; // 模板ID
	private String name; // 模板名称
	private List<Group> groups; // 分组

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public static class Group implements Serializable {
		private String id; // 分组ID
		private String name; // 分组名称
		private List<Item> items; // 分项

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<Item> getItems() {
			return items;
		}

		public void setItems(List<Item> items) {
			this.items = items;
		}
	}

	public static class ValuePair implements Serializable {
		private String index;
		private String name;

		public String getIndex() {
			return index;
		}

		public void setIndex(String index) {
			this.index = index;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public static class Item implements Serializable {
		private String id; // 分项ID
		private String name; // 分项名称
		private String type; // 分项类型
		private String description; // 描述
		private String dataType; // 数据类型
		private transient String valueSql; // 值SQL语句
		private String valueLength; // 值长度
		private String verifyExp; // 验证表达式
		private List<ValuePair> values; // 值列表
		private List<Mapping> mappings; // 值映射
		private List<ConfigRegionInfo> configRegionInfos; // 区域映射

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getDataType() {
			return dataType;
		}

		public void setDataType(String dataType) {
			this.dataType = dataType;
		}

		public String getValueSql() {
			return valueSql;
		}

		public void setValueSql(String valueSql) {
			this.valueSql = valueSql;
		}

		public String getValueLength() {
			return valueLength;
		}

		public void setValueLength(String valueLength) {
			this.valueLength = valueLength;
		}

		public String getVerifyExp() {
			return verifyExp;
		}

		public void setVerifyExp(String verifyExp) {
			this.verifyExp = verifyExp;
		}

		public List<Mapping> getMappings() {
			return mappings;
		}

		public void setMappings(List<Mapping> mappings) {
			this.mappings = mappings;
		}

		public List<ConfigRegionInfo> getConfigRegionInfos() {
			return configRegionInfos;
		}

		public void setConfigRegionInfos(
				List<ConfigRegionInfo> configRegionInfos) {
			this.configRegionInfos = configRegionInfos;
		}

		public List<ValuePair> getValues() {
			return values;
		}

		public void setValues(List<ValuePair> values) {
			this.values = values;
		}
	}

	public static class Mapping implements Serializable {
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

	public static class ConfigRegionInfo implements Serializable {
		private Integer regionId;
		private String regionName;
		private List<ConfigRegionInfo> children;

		public Integer getRegionId() {
			return regionId;
		}

		public void setRegionId(Integer regionId) {
			this.regionId = regionId;
		}

		public String getRegionName() {
			return regionName;
		}

		public void setRegionName(String regionName) {
			this.regionName = regionName;
		}

		public List<ConfigRegionInfo> getChildren() {
			return children;
		}

		public void setChildren(List<ConfigRegionInfo> children) {
			this.children = children;
		}
	}

}
