package com.krm.voteplateform.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 自定义公式解析 进行公式解析、验证
 */
public class FormulaUtils {
	/**
	 * 公式操作符定义 公式分解使用的结构体
	 */
	static class OperSignDef {
		String Oper; // 操作类型
		String Sign; // 操作符号
		int Len; // 符号长度
		int Level; // 优先级别

		OperSignDef(String Oper, String Sign, int Len, int Level) {
			this.Oper = Oper;
			this.Sign = Sign;
			this.Len = Len;
			this.Level = Level;
		}
	}

	// 操作域,包括数值、参数、运算符、操作符等
	static class OperRange {
		String Sign; // 操作符或常量串
		String Value; // 计算后的值,统一按照字符串存储
		int Type; // 类型 0-操作符号 ；1-引号；2-括号；3-逗号；4-函数;5-字符值；6-数字；7-关系公式
		int NewType; // 转换后的类型,函数返回类型，括号根据括号内的类型返回类型
		int RangeLevel; // 操作域层级，用以识别括号层级
		int OperLevel; // 运算层级

		OperRange(String Sign, String Value, int Type, int RangeLevel, int OperLevel) {
			this.Sign = Sign;
			this.Value = Value;
			this.Type = Type;
			this.RangeLevel = RangeLevel;
			this.OperLevel = OperLevel;
		}
	}

	// 错误信息及位置
	static class ErrorMsg {
		int check;
		String msg;
		int pos;
	}

	// 公式源字符串
	public static String sourcestr = "";

	// 返回信息
	public static ErrorMsg rtnMsg = new ErrorMsg();

	//
	public static Map<String, Object> funmap = null;

	// 函数检测
	public static boolean rtnFlag = true;

	// 定义公式操作符、优先权等
	public static OperSignDef Signs[] = new OperSignDef[] { new OperSignDef("OPER_AND", "and", 3, 0),
			new OperSignDef("OPER_OR", "or", 2, 0), new OperSignDef("OPER_DD", ">=", 2, 1),
			new OperSignDef("OPER_XD", "<=", 2, 1), new OperSignDef("OPER_BD", "<>", 2, 1),
			new OperSignDef("OPER_DY", ">", 1, 2), new OperSignDef("OPER_XY", "<", 1, 2),
			new OperSignDef("OPER_EQUAL", "=", 1, 2), new OperSignDef("OPER_ADD", "+", 1, 3),
			new OperSignDef("OPER_DEC", "-", 1, 3), new OperSignDef("OPER_MUL", "*", 1, 4),
			new OperSignDef("OPER_DIV", "/", 1, 4)
			// 单引号、括号、逗号、空格 需要特殊处理
	};

	private static boolean checkNumber(String value) {
		String regex = "^(-?[1-9]\\d*\\.?\\d*)|(-?0\\.\\d*[1-9])|(-?[0])|(-?[0]\\.\\d*)$";
		return value.matches(regex);
	}

	/**
	 * 检测数据类型（数字？字符） 返回5-字符、6-数字
	 */
	private static int checkDataType(String s) {
		if (checkNumber(s))
			return 6;
		else
			return 5;
	}

	private static void setErrorMsg(String msg, int pos) {
		rtnMsg.msg = msg;
		rtnMsg.pos = pos;
	}

	private static Map<String, Object> rtnRtnMsg(boolean check) {
		rtnMsg.check = check ? 0 : 1;
		Map<String, Object> rtmmap = new HashMap<String, Object>();
		rtmmap.put("check", rtnMsg.check);
		rtmmap.put("msg", rtnMsg.msg);
		rtmmap.put("pos", rtnMsg.pos);
		return rtmmap;
	}

	/**
	 * 操作符号检测 对公式字符串指定位置，进行操作符号检测，返回操作符号,后继通过操作符长度，数组移位
	 */
	private static OperSignDef checkOperSign(String script, int pos) {
		OperSignDef checkresult = null;
		for (int i = 0; i < Signs.length; i++) {
			OperSignDef sign = Signs[i];
			if (pos + sign.Len < script.length()) {
				if (script.substring(pos, pos + sign.Len).equals(sign.Sign)) {
					checkresult = sign;
					break;
				}
			}
		}
		return checkresult;
	}

	private static int getRangeType(List<OperRange> list, int operindex) {
		if (operindex < 0)
			return -1;
		if (operindex >= list.size())
			return -1;
		return list.get(operindex).Type;
	}

	private static OperRange getRange(List<OperRange> list, int operindex) {
		if (operindex < 0)
			return null;
		if (operindex >= list.size())
			return null;
		return list.get(operindex);
	}

	/**
	 * 操作域校验 递归 操作域List,起始位置，操作层级
	 */
	private static boolean checkRanges(List<OperRange> list, int operindex, int operlevel) {
		boolean result = true;
		for (int i = operindex; i < list.size(); i++) {
			OperRange range = list.get(i);
			// 当前节点正确，检测下级
			if (range.RangeLevel == operlevel) {
				// System.out.println(range.Sign+"---type:"+range.Type+" newtype:"+range.NewType+"
				// level:"+range.RangeLevel);
				if (!checkRange(list, i)) {
					// System.out.println(range.Sign+"错误");
					result = false;
					break;
				}

				if (i + 1 < list.size()) {
					// 递归查找是否有下级，并进行检测
					result = checkRanges(list, i + 1, range.RangeLevel + 1);
					if (!result)
						break;
				}
			} else if ((range.RangeLevel < operlevel)) { // 统计处理结束
				break;
			}
		}
		return result;
	}

	/**
	 * 修改操作域类型，主要是修改括号等层级运算后的属性 递归 操作域List,起始位置，操作层级
	 */
	private static int changeRangesType(List<OperRange> list, int operindex, int operlevel) {
		int resulttype = -1;
		for (int i = operindex; i < list.size(); i++) {
			OperRange range = list.get(i);

			// 当前节点正确，检测下级
			int downtype = -1;
			if (range.RangeLevel == operlevel) {

				range.NewType = range.Type;
				// 判断关系运算
				if (range.Type == 0) {
					if (range.OperLevel <= 2)
						resulttype = 7;
					else
						resulttype = 6;
				}
				// 函数需要获取函数返回值属性
				else if (range.Type == 4) {

				}

				if (i + 1 < list.size()) {
					downtype = changeRangesType(list, i + 1, range.RangeLevel + 1);
					if (downtype != -1) {
						resulttype = downtype;

						// 节点为括号，要更新类型
						if (range.Type == 2) {
							range.NewType = downtype;
						}
					}
				}
			} else if ((range.RangeLevel < operlevel)) { // 统计处理结束
				break;
			}
		}
		return resulttype;
	}

	/**
	 * 函数部分校验 判断是否为函数
	 */
	private static boolean checkFunction(List<OperRange> list, int operindex) {
		OperRange fun = list.get(operindex);
		String funname = fun.Sign.toLowerCase();
		int funlevel = fun.RangeLevel; // 函数操作层级
		funname = funname.replace("(", "");

		boolean check = true;
		int paramcount = 0;

		// 检测函数是否存在
		if (funmap != null) {
			Object o = funmap.get(funname);
			if (o == null) // 函数不存在
			{
				setErrorMsg("函数:" + fun.Sign + " 不存在。", 0);
				return false;
			}
			paramcount = Integer.parseInt(String.valueOf(o));
		} else {
			setErrorMsg("系统还未定义任何函数，函数:" + funname + "不存在。", 0);
			return false;
		}

		int count = 0;
		// 检测参数个数书是否相符
		for (int i = operindex + 1; i < list.size(); i++) {
			OperRange range = list.get(i);
			if (range.RangeLevel == funlevel + 1) { // 函数层级
				if (range.Type == 3)
					count++; // 逗号
			} else if (range.RangeLevel == funlevel) {
				if (range.Sign == ")") { // 函数结束
					if (i - 1 != operindex) {
						count++;
						break;
					} else
						break;
				} else { // 函数异常结束，无结束括号
					check = false;
					setErrorMsg("函数:" + funname + " 格式错误。", 0);
					break;
				}
			} else if (range.RangeLevel < funlevel)
				break;
		}

		// 参数不相符
		if ((check) && (paramcount != count)) {
			setErrorMsg("函数:" + fun.Sign + " 参数个数不相符，预期:" + paramcount + "个,实际:" + count + "个。", 0);
			return false;
		}

		return check;
	}

	/**
	 * 校验操作域 根据规则校验指定操作域
	 */
	private static boolean checkRange(List<OperRange> list, int operindex) {
		boolean result = true;
		OperRange range = list.get(operindex);
		int ltype = -1;
		int rtype = -1;
		String lsign = "";
		String rsign = "";

		OperRange lrange = getRange(list, operindex - 1);
		OperRange rrange = getRange(list, operindex + 1);

		if (lrange != null) {
			lsign = lrange.Sign;
			ltype = lrange.Type;
		}
		if (rrange != null) {
			rsign = rrange.Sign;
			rtype = rrange.Type;
		}

		int type = range.Type;
		String sign = range.Sign;

		switch (type) {
		case 0: // 0-操作符号
			// 操作符号两侧值属性必须一致,+-需要特殊处理，可能一侧有值
			if ((ltype == -1) || (rtype == -1)) {
				setErrorMsg("公式运算符号错误.", 0);
				result = false;
			} else if ((ltype == 0) || (rtype == 0)) {
				setErrorMsg("公式运算符号错误.", 0);
				result = false;
			}
			break;
		case 1: // 1-引号
			if ((ltype == 5) || (ltype == 6) || (rtype == 5) || (rtype == 6)) // 相邻两个常量中间需要有操作符号
			{
				setErrorMsg("相邻两个字符或数字间，必须有运算符号相连.", 0);
				result = false;
			}
			break;
		case 2: // 2-括号
			if (lsign.equals(")") && sign.equals("(")) {
				setErrorMsg("公式括号匹配错误.", 0);
				result = false;
				break;
			}
			if (rsign.equals("(") && sign.equals(")")) {
				setErrorMsg("公式括号匹配错误.", 0);
				result = false;
				break;
			}
			if (lsign.equals("(") && sign.equals(")")) {
				setErrorMsg("公式括号内不能为空.", 0);
				result = false;
				break;
			} // 空括号
			if (rsign.equals(")") && sign.equals("(")) {
				setErrorMsg("公式括号内不能为空.", 0);
				result = false;
				break;
			} // 空括号
			if (sign.equals("(") && (ltype == 5 || ltype == 6)) {
				setErrorMsg("相邻两个字符、数字、函数间，必须有运算符号相连.", 0);
				result = false;
				break;
			} // 括号与值直接连接
			if (sign.equals(")") && (rtype == 4 || rtype == 5 || rtype == 6)) {
				setErrorMsg("相邻两个字符、数字、函数间，必须有运算符号相连.", 0);
				result = false;
				break;
			} // 括号与值直接连接
			if (sign.equals("(") && rtype == 3) {
				setErrorMsg("公式格式错误或括号不匹配.", 0);
				result = false;
				break;
			}
			if (sign.equals(")") && ltype == 3) {
				setErrorMsg("公式格式错误或括号不匹配.", 0);
				result = false;
				break;
			}
			break;
		case 3: // 3-逗号
			if ((ltype == 3) || (rtype == 3)) {
				setErrorMsg("公式格式错误或括号不匹配，或函数参数不能为空.", 0);
				result = false;
				break;
			}
			// if((ltype==0)||(rtype==0)) {setErrorMsg("公式格式错误.",0);result = false;break;}
			break;
		case 4: // 4-函数
			if (!checkFunction(list, operindex)) {
				result = false;
				break;
			}
			break;
		case 5: // 5-字符值
			if ((ltype == 5) || (ltype == 6) || (rtype == 5) || (rtype == 6)) // 相邻两个常量中间需要有操作符号
			{
				setErrorMsg("公式格式错误，字符或数字间必须有运算符号.", 0);
				result = false;
			}
			// if((range.Sign.substring(1)!="'")||(range.Sign.substring(-1)!="'")) //字符需要判断两侧是否为单引号
			// result = false;
			break;
		case 6: // 6-数字
			if ((ltype == 5) || (ltype == 6) || (rtype == 5) || (rtype == 6)) // 相邻两个常量中间需要有操作符号
			{
				setErrorMsg("公式格式错误，字符或数字间必须有运算符号.", 0);
				result = false;
			}
			break;
		case 7:
			break;
		}
		// System.out.println(type+" left:"+ltype+" right:"+rtype);
		return result;
	}

	/**
	 * 公式分解 根据分隔符，分解公式
	 */
	public static Map<String, Object> checkScript(String script, Map<String, Object> infunmap) {
		boolean checkresult = true;
		List<OperRange> list = new ArrayList<OperRange>(); // 存放操作域

		funmap = infunmap; // 设置函数

		String s = script.toLowerCase();
		int n1 = 0; // 统计括号 判断阔海是否匹配，正确匹配为0
		int n2 = 0; // 统计单引号等 处理单引号配对，单引号内所有分隔符无效
		String tempstr = "";
		char ch[] = s.toCharArray();

		for (int i = 0; i < ch.length; i++) {
			if (n2 > 0) // 单引号内内容，按照字符串处理，不错其他识别
			{
				tempstr += ch[i]; // 单引号暂时先保留
				if (ch[i] == '\'') {
					n2--;
					OperRange range = new OperRange(tempstr, tempstr, 5, n1, -1); // 单引号内值默认为字符
					list.add(range);
					tempstr = "";
				}
			} else {
				switch (ch[i]) {
				case '(': {
					OperRange range;
					// 需要判断是否为函数，还是运算括号
					if (!tempstr.isEmpty()) // 函数
						range = new OperRange(tempstr + "(", "", 4, n1, -1);
					else // 普通括号
						range = new OperRange(tempstr + "(", "", 2, n1, -1);
					list.add(range);
					n1++;
					tempstr = "";
				}
					break;
				case '\'': // 处理引号，优先级高，单引号内的其他符号均按字符处理
				{
					n2++;
					if (!tempstr.isEmpty()) // 单引号前有值（非操作符），报错
					{
						setErrorMsg("公式格式错误，单引号前不能有数字或字符.", 0);
						return rtnRtnMsg(false);
					}
					tempstr += ch[i];
				}
					break;
				case ')': {
					// 判断是否有括号多,多则错误
					if (n1 < 0) {
						setErrorMsg("公式格式错误，括号不匹配.", 0);
						return rtnRtnMsg(false);
					}
					if (!tempstr.isEmpty()) {
						OperRange range = new OperRange(tempstr, "", checkDataType(tempstr), n1, -1);
						list.add(range);
						tempstr = "";
					}
					n1--;
					OperRange range = new OperRange(")", "", 2, n1, -1);
					list.add(range);
				}
					break;
				case ',': // 分隔符
				{
					if (!tempstr.isEmpty()) {
						OperRange range = new OperRange(tempstr, "", checkDataType(tempstr), n1, -1);
						list.add(range);
						tempstr = "";
					}
					OperRange range = new OperRange(",", "", 3, n1, -1);
					list.add(range);
					tempstr = "";
				}
					break;
				case ' ':
					// 判断是否是两个非操作符间的空格，如果是，公式错误
					/*
					 * if(!tempstr.isEmpty()) { setErrorMsg("公式格式错误.",0); return
					 * rtnRtnMsg(checkresult); }
					 */
					if (!tempstr.isEmpty()) {
						OperRange range = new OperRange(tempstr, "", checkDataType(tempstr), n1, -1);
						list.add(range);
						tempstr = "";
					}
					break;
				default: {
					OperSignDef sign = checkOperSign(s, i);
					if (sign == null) // 非其他运算符号
						tempstr += ch[i];
					else {
						if (!tempstr.isEmpty()) {
							OperRange range = new OperRange(tempstr, "", checkDataType(tempstr), n1, -1);
							list.add(range);
							tempstr = "";
						}
						OperRange range = new OperRange(sign.Sign, "", 0, n1, sign.Level);
						list.add(range);
						i = i + sign.Len - 1; // 识别为操作符后需移位
					}
				}
					break;
				}
			}
		}

		if (!tempstr.isEmpty()) {
			OperRange range = new OperRange(tempstr, "", checkDataType(tempstr), n1, -1);
			list.add(range);
			tempstr = "";
		}

		// 验证单引号是否匹配,返回错误
		if (n2 > 0) {
			setErrorMsg("公式单引号不匹配.", 0);
			checkresult = false;
			return rtnRtnMsg(checkresult);
		}

		// 括号不匹配，返回错误
		if (n1 != 0) {
			setErrorMsg("公式括号不匹配.", 0);
			checkresult = false;
			return rtnRtnMsg(checkresult);
		}

		// changeRangesType(list,0,0); //暂时先取消，公式中包含字段信息等，暂时不用识别数字字符等，括号函数等转属性暂时也先不用
		checkresult = checkRanges(list, 0, 0);

		list.clear();
		if (checkresult)
			setErrorMsg("公式校验通过。", 0);
		return rtnRtnMsg(checkresult);
	}

	public static List<String> splitCondition(String condition) {
		List<String> rtnlist = new ArrayList<String>();
		String ragex = "\\'(.*)\\'";
		String s = condition.replaceAll(ragex, "");

		StringTokenizer st = new StringTokenizer(s, "=+-<> */()", false);
		while (st.hasMoreTokens()) {
			rtnlist.add(st.nextToken().toUpperCase());
		}
		return rtnlist;
	}
}
