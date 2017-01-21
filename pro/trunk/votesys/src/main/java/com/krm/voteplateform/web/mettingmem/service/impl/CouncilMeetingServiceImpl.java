package com.krm.voteplateform.web.mettingmem.service.impl;

import org.beetl.sql.core.VoteSqlManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.krm.voteplateform.web.commission.dao.PtCommissionMapper;
import com.krm.voteplateform.web.commission.service.impl.PtCommissionServiceImpl;
import com.krm.voteplateform.web.mettingmem.service.CouncilMeetingService;
import com.krm.voteplateform.web.ptvotematterconf.dao.PtVoteMatterConfMapper;
import com.krm.voteplateform.web.ptvoterestmpl.dao.PtVoteResTmplMapper;

/**
 * ClassName:CouncilMeetingServiceimpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年12月13日 上午10:00:17 <br/>
 * @author   lixy
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class CouncilMeetingServiceImpl implements CouncilMeetingService {
	
	Logger logger = LoggerFactory.getLogger(CouncilMeetingService.class);


	@Autowired
	private VoteSqlManager voteSqlManager;
	@Override
	public void saveCouncilMeeting() {
		// TODO Auto-generated method stub
	}

}

