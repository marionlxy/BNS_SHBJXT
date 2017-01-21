package com.krm.voteplateform.web.plateformuser.service;

import java.util.List;
import java.util.Map;

public interface PtPlateformUserService {

	List<Map<String, Object>> toPtUserList();

	List<Map<String, Object>> toPtUserListByCondition(String organId, String userName);

}
