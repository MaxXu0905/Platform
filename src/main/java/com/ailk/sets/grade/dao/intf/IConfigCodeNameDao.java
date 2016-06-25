package com.ailk.sets.grade.dao.intf;

import com.ailk.sets.platform.intf.empl.domain.ConfigCodeName;
import com.ailk.sets.platform.intf.empl.domain.ConfigCodeNameId;

public interface IConfigCodeNameDao {

	public ConfigCodeName get(ConfigCodeNameId configCodeNameId);
	
	public void evict();

}
