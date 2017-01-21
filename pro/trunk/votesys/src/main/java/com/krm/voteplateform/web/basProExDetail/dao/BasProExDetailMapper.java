package com.krm.voteplateform.web.basProExDetail.dao;

import java.util.HashMap;
import java.util.List;

import com.krm.voteplateform.web.basProExDetail.model.BasProExDetail;

public interface BasProExDetailMapper {

	Boolean insetBatchList(List<BasProExDetail> detailForm);

	void deleteBasProExDetail(HashMap<String, String> map);

	

}
