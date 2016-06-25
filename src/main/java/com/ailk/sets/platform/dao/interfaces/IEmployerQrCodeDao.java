package com.ailk.sets.platform.dao.interfaces;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.domain.EmployerQrcode;

public interface IEmployerQrCodeDao extends IBaseDao<EmployerQrcode> {
	public EmployerQrcode getAvailableQrCode();

	public int getMaxId();
	
	public void removeEmployerId(int employerId);
}
