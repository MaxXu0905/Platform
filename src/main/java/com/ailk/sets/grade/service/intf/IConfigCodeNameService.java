package com.ailk.sets.grade.service.intf;

public interface IConfigCodeNameService {

	public String getPositionLanguage(String codeId);

	public String getPositionLevel(String codeId);

	public String getCodeName(String codeType, String codeId);

}
