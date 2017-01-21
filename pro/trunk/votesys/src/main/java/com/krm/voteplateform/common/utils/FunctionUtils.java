package com.krm.voteplateform.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 函数操作功能根据 解析函数支持，进行解析并转换，脚本验证
 */

public class FunctionUtils {

	/**
	 * 函数操作符号定义 函数分解使用的结构体
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

	public static String sourcestr = "";

	// 定义公式符号
	public static OperSignDef Signs[] = new OperSignDef[] { new OperSignDef("OPER_AND", "AND", 3, 0),
			new OperSignDef("OPER_OR", "OR", 2, 0), new OperSignDef("OPER_DD", ">=", 2, 1),
			new OperSignDef("OPER_XD", "<=", 2, 1), new OperSignDef("OPER_BD", "<>", 2, 1),
			new OperSignDef("OPER_DY", ">", 1, 2), new OperSignDef("OPER_XY", "<", 1, 2),
			new OperSignDef("OPER_EQUAL", "=", 1, 2), new OperSignDef("OPER_ADD", "+", 1, 3),
			new OperSignDef("OPER_DEC", "-", 1, 3), new OperSignDef("OPER_MUL", "*", 1, 4),
			new OperSignDef("OPER_DIV", "/", 1, 4) };

	// 公式检测
	public static boolean checkFormula(String script) {
		String s = script.toUpperCase();
		// 循环，切割公式
		for (char ch : s.toCharArray()) {
			switch (ch) {
			case '(':
				break;
			case '\'':
				break;
			case ')':
				break;
			case ',': // 分隔符
				break;
			default:
				break;
			}
		}
		return true;
	}

	/**
	 * 分解公式，提取出函数字符串
	 * 
	 * @param 公式
	 * @return 函数组
	 */
	private static String[] splitFunStr(String script) {
		String regex = "[A-Za-z0-9]*?\\(([A-Za-z0-9,()']+)\\)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(script);
		int i = 0;
		while (m.find()) {
			i++;
		}

		String[] s = new String[i];
		m.reset();
		i = 0;
		while (m.find()) {
			s[i] = m.group();
			splitParams(s[i]);
			i++;
		}
		return s;
	}

	/**
	 * 验证括号是否匹配,简单验证，双向数量是否相等,以后再扩展,比较麻烦
	 * 
	 * @param 公式
	 * @return 函数组
	 */
	private static boolean checkBrackets(String script) {
		if (script.replace("(", "").length() == script.replace(")", "").length())
			return true;
		else
			return false;
	}

	/**
	 * 函数部分，分割参数
	 * 
	 * @param 函数
	 * @return 分割后的参数数组
	 */
	private static String[] splitParams(String fun) {
		String funname = fun.substring(0, fun.indexOf('(') - 1);
		String s = fun.substring(fun.indexOf('(') + 1, fun.length() - 1);
		int n1 = 0; // 统计括号(
		int n2 = 0; // 统计单引号等
		// 括号内、单引号内的逗号，不能作为分隔符。
		String tempparam = "";
		System.out.println("函数：" + funname);
		for (char ch : s.toCharArray()) {
			// System.out.println(ch);
			switch (ch) {
			case '(':
				n1++;
				tempparam += ch;
				break;
			case '\'':
				n2 = n2 > 0 ? 0 : 1;
				tempparam += ch;
				break;
			case ')':
				n1--;
				tempparam += ch;
				break;
			case ',': // 分隔符
				if ((n1 > 0) || (n2 > 0)) // 括号内或单引号内的逗号
					tempparam += ch;
				else // 用于分割
				{
					System.out.println("参数:" + tempparam);
					tempparam = "";
				}
				break;
			default:
				tempparam += ch;
				break;
			}
		}
		return null;
	}
}
