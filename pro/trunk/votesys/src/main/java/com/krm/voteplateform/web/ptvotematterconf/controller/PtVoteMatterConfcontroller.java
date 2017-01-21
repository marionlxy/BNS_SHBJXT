package com.krm.voteplateform.web.ptvotematterconf.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.krm.voteplateform.common.base.controller.BaseController;
import com.krm.voteplateform.common.base.entity.Result;
import com.krm.voteplateform.common.utils.VoteFormulaUtils;
import com.krm.voteplateform.web.basProject.service.BasMettingService;
import com.krm.voteplateform.web.ptvotematterconf.model.PtVoteMatterConf;
import com.krm.voteplateform.web.ptvotematterconf.service.PtVoteMatterConfService;
import com.krm.voteplateform.web.util.SysUserUtils;

@Controller
@RequestMapping("pt/votematterconf")
public class PtVoteMatterConfcontroller extends BaseController {
	@Autowired
	private PtVoteMatterConfService ptVoteMatterConfService;
	@Autowired
	private BasMettingService basMettingService;

	/**
	 * 保存详情
	 * @param ptVoteMatterConf
	 * @return
	 */
	@RequestMapping(value = "/savePtVoteMatterConf",method=RequestMethod.POST)
	public ModelAndView savePtVoteMatterConf(PtVoteMatterConf ptVoteMatterConf){
		ModelAndView mv = new ModelAndView("redirect:/plateform/listPtVoteMatterConf");
		ptVoteMatterConfService.savePtVoteMatterConf(ptVoteMatterConf);
		return mv;
	}
	//去主页面
	@RequestMapping("toMenuList")
	public String toMenuList(Model model){
		String code=SysUserUtils.getCurrentCommissionCode();
		model.addAttribute("code", code);
		return "plateform/ptvotemattperconf/PvmshowList";
	}
	@RequestMapping("toResultMenuList")
	public String toResultMenuList(Model model){
		String code = SysUserUtils.getCurrentCommissionCode();
		model.addAttribute("code", code);
		return "plateform/ptvotemattperconf/PvmshowResultList";
	}
	//展示列表数据查询
	@RequestMapping("selectList")
	@ResponseBody
	public List<Map<String, Object>> ListAll(HttpServletRequest request,Model model,String code){
		logger.info("开始查询ptvotematterConf表");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("code", code);
		List<Map<String, Object>> listAll = ptVoteMatterConfService.selectAll(params);
		logger.info("结束查询ptvotematterConf表");
		return listAll;
	}
	//展示列表数据查询
	@RequestMapping("selecResultList")
	@ResponseBody
	public List<Map<String, Object>> selecList(HttpServletRequest request,Model model,String code){
		logger.info("开始查询ptvotematterConf表");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("code", code);
		List<Map<String, Object>> selecResultList = ptVoteMatterConfService.selecResultList(params);
		logger.info("结束查询ptvotematterConf表");
		return selecResultList;
	}
	//去修改投票意见的界面开始展示修改页面
	@RequestMapping("updateDate")
	public String ToupdateDate(HttpServletRequest request,Model model,String id){
		logger.info("开始展示ptvotematterConf修改页面");
		PtVoteMatterConf ptVoteMatterConf =  ptVoteMatterConfService.selectchoose(id);
		model.addAttribute("ptVoteMatterConf", ptVoteMatterConf);
		logger.info("结束展示ptvotematterConf修改页面");
		return "plateform/ptvotemattperconf/PvmshowUpdate";
	}
	//修改之后的保存
	@RequestMapping(value = "updateSave",method = RequestMethod.POST)
	@ResponseBody
	public Result updateSave(HttpServletRequest request, String id,String committeeText,String enable){
		String enable1=getPara("enable",enable);
		PtVoteMatterConf ptVoteMatterConf = new PtVoteMatterConf();
		ptVoteMatterConf.setEnable(enable1);
		ptVoteMatterConf.setId(id);
		ptVoteMatterConf.setCommitteeText(committeeText);
		boolean flag = ptVoteMatterConfService.updateSave(ptVoteMatterConf);
		return Result.successResult();
		
	}
	@RequestMapping("TestPlan")
	public String TestPlan(@RequestParam Map<String, Object> params,HttpServletRequest request,Model model){
		params.put("enable", "0");
		params.put("code", SysUserUtils.getCurrentCommissionCode());
		model.addAttribute("voteMatterList", ptVoteMatterConfService.selectAll(params));
		return "sysfunction/planmanage/TestPlan";
	}
	@RequestMapping(value = "calculate",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> selectVote(@RequestParam Map<String, Object> params,HttpServletRequest request,Model model){
		//查询通过公式 
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		//出表决结果
			Map<String, Object> fomulas = basMettingService.selectFomula(params);
			//查询该委员会是否有一票否决权
				//查询具有一票否决权的委员是否投了否决票
					if(params.containsKey("oneMindFlag")){
						params.put("enable", "0");
						params.put("code", SysUserUtils.getCurrentCommissionCode());
						params.put("val", "03");
						List<Map<String,Object>> resultList = ptVoteMatterConfService.selecResultList(params);
						String resultText = resultList.size() == 0 ? "未通过" : resultList.get(0).get("committeeText").toString();
						resultMap.put("result", resultText);
						resultMap.put("resultType", "03");
						//一票否决出结果,更改项目状态
					}else{
						Integer agree =  params.containsKey("01")?Integer.parseInt(params.get("01").toString()):0;
						Integer Reconsideration = params.containsKey("02")?Integer.parseInt(params.get("02").toString()):0;
						Integer disagree =  params.containsKey("03")?Integer.parseInt(params.get("03").toString()):0;
						Integer total = agree + Reconsideration + disagree;
						String resultText = "";
						if(fomulas.containsKey("adoptFormula")){
							String againFormula = fomulas.get("adoptFormula").toString();
							String [] array = againFormula.substring(againFormula.indexOf("(")+1,againFormula.indexOf(")")).split(",");
							Map<String,Integer> map1 = new HashMap<String,Integer>();
							map1.put(array[0], agree);
							map1.put(array[1], total);
							boolean result = VoteFormulaUtils.calcFormula(againFormula, map1);
							/*Map<String, Object> params1 = new HashMap<String, Object>();
							params1.put("code", SysUserUtils.getCurrentCommissionCode());
							List<Map<String, Object>> selecResultList = ptVoteMatterConfService.selecResultList(params1);*/
							if(result){
								params.put("enable", "0");
								params.put("code", SysUserUtils.getCurrentCommissionCode());
								params.put("val", "01");
								if(ptVoteMatterConfService.selecResultList(params).size()>0){
									resultText = ptVoteMatterConfService.selecResultList(params).get(0).get("committeeText").toString();
									resultMap.put("result", resultText);
									resultMap.put("resultType", "01");
								}else {
									return null;
								}
							//如果通过公式不满足,则查询再议公式
							}else if(fomulas.containsKey("againFormula")){
									String adoptFormula = fomulas.get("againFormula").toString();
									String [] arr = adoptFormula.substring(adoptFormula.indexOf("(")+1,adoptFormula.indexOf(")")).split(",");
									Map<String,Integer> map2 = new HashMap<String,Integer>();
									map2.put(arr[0], Reconsideration);
									map2.put(arr[1], total);
									boolean b = VoteFormulaUtils.calcFormula(adoptFormula, map2);
									if(b){
										params.put("enable", "0");
										params.put("code", SysUserUtils.getCurrentCommissionCode());
										params.put("val", "02");
										if (ptVoteMatterConfService.selecResultList(params).size()>0) {
											resultText = ptVoteMatterConfService.selecResultList(params).get(0).get("committeeText").toString();
											resultMap.put("result", resultText);
											resultMap.put("resultType", "02");
										}
										else{
											return null;
										}
									//上面
									}else{
									params.put("enable", "0");
									params.put("code", SysUserUtils.getCurrentCommissionCode());
									params.put("val", "03");
									if (ptVoteMatterConfService.selecResultList(params).size()>0) {
										List<Map<String,Object>> resultList = ptVoteMatterConfService.selecResultList(params);
										resultText = resultList.size() == 0 ? "未通过" : resultList.get(0).get("committeeText").toString();
										resultMap.put("result", resultText);
										resultMap.put("resultType", "03");
									}
									else{
										return null;
									}
									
								}
							}
						}}
			return resultMap;
	}
}
