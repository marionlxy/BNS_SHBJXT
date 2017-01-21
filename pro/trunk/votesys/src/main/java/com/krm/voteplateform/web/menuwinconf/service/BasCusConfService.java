package com.krm.voteplateform.web.menuwinconf.service;

import com.krm.voteplateform.web.menuwinconf.model.BasCusConf;

public interface BasCusConfService {

	boolean save(BasCusConf basCusConf);

	boolean isExist(String functionCode);

	void delete(String functionCode);

}
