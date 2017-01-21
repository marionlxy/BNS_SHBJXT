package com.krm.voteplateform.web.pageTemplet.service;

import java.util.List;
import java.util.Map;

import com.krm.voteplateform.web.pageTemplet.model.PtVotePageTemplet;

public interface PtVotePageTempletService {

	List<Map<String, Object>> listPT();

	boolean savePage(PtVotePageTemplet ptVotePageTemplet);

	Map<String, Object> getPageTempletById(String id);

	boolean updatePageTemplet(PtVotePageTemplet ptVotePageTemplet);

	boolean isRepeat(String name,String id);

}
