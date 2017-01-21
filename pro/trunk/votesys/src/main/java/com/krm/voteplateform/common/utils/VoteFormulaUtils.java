package com.krm.voteplateform.common.utils;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

import fr.expression4j.core.Expression;
import fr.expression4j.core.Parameters;
import fr.expression4j.factory.ExpressionFactory;
import fr.expression4j.factory.NumberFactory;

/**
 * 表决公式工具类
 * 
 * @author JohnnyZhang
 */
public class VoteFormulaUtils {

	private static final Logger log = LoggerFactory.getLogger(VoteFormulaUtils.class);

	/**
	 * 计算表决公式
	 * <p>
	 * 例如:公式y(a,zong)=a/zong-2/3 其中y代表结果，a和zong代表参数，公式含义为y=a/zong-2/3<br>
	 * 调用实例：<br/>
	 * <code> Map<String, Integer> map = Maps.newHashMap();</code><br/>
	 * <code> map.put("a",6);</code><br/>
	 * <code> map.put("zong",10);</code><br/>
	 * boolean flag = calcFormula("y(a,zong)=a/zong-2/3;0",map)
	 * 
	 * @param formula 表决公式,公式后可直接跟分号，分号后数字0代表包含等于结果，其他代表不包含等于结果.若不跟；号，默认使用包含等于的结果
	 * @param paramValueMap 表决公式参数键值Map
	 * @return 若执行结果不小于0，返回true；否则返回false
	 * @throws Exception
	 */
	public static boolean calcFormula(String formula, Map<String, Integer> paramValueMap) {
		boolean flag = false;
		String parms = JSON.toJSON(paramValueMap).toString();
		log.debug("开始计算公式{}，参数为{}", formula, parms);
		try {
			// 公式使用;号分割
			String[] split = StringUtils.split(formula, ";");
			boolean bFlag = true;// 定义是否包含=的
			if (split.length > 1) {// 若分割数量大于1
				formula = split[0];// 重新定义公式
				bFlag = "0".equals(split[1]);
			}
			Expression expression = ExpressionFactory.createExpression(formula);// 初始化表达式
			Parameters parameters = ExpressionFactory.createParameters();// 初始化参数
			Set<String> keySet = paramValueMap.keySet();
			if (!keySet.isEmpty()) {
				for (String key : keySet) {
					parameters.addParameter(key, NumberFactory.createReal(paramValueMap.get(key)));// 添加参数
				}
			}
			double realValue = expression.evaluate(parameters).getRealValue();// 执行取得返回值
			flag = bFlag ? realValue >= 0 : realValue > 0;
		} catch (Exception e) {
			log.error("解析公式" + formula + "出现异常,参数为" + parms, e);
			throw new RuntimeException(e);
		}
		log.debug("解析公式{},参数{},结果为{}", formula, parms, flag);
		return flag;
	}

	public static void main(String[] args) {
		Map<String, Integer> map = Maps.newHashMap();
		map.put("a", 6);
		map.put("zong", 9);
		boolean flag = calcFormula("y(a,zong)=a/zong-2/3;1", map);
		System.out.println(flag);
	}
}
