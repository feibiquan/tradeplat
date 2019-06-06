package com.xfpay.utils.common;

public class RegexUtils {
	/**
	 * 匹配EMAI 地址
	 */
	public final static String EMAIL = "^([a-zA-Z0-9\\+_\\-]+)(\\.[a-zA-Z0-9\\+_\\-]+)*@([a-zA-Z0-9\\-]+\\.)+[a-zA-Z]{2,6}$";
	/**
	 * 匹配纯中文
	 */
	public final static String CHINESE = "[\\u4e00-\\u9fa5]+";
	/**
	 * 匹配身份证号码
	 */
	public final static String IDCARD = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
	/**
	 * 匹配手机号码   "^(0|86|17951)?(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$"
	 * 只验证是否11位号码
	 */
	public final static String MOBILE = "^(0|86|17951)?[0-9]{11}$";
	/**
	 * 匹配固话号码
	 */
	public final static String PHONE = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
	/**
	 * 匹配浮点型
	 */
	public final static String DECIMALS = "\\-?[1-9]\\d+(\\.\\d+)?";
	/**
	 * 验证URL
	 */
	public final static String URL = "[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?";
	/**
	 *
	 * 匹配正整数
	 *
	 */
	public static final String POSITIVE_INTEGER_REGEXP = "^[0-9]*[1-9][0-9]*$";

	/**
	 *
	 * 匹配非正整数（负整数 + 0）
	 *
	 */
	public static final String NON_POSITIVE_INTEGERS_REGEXP = "^((-//d+)|(0+))$";

	/**
	 *
	 * 匹配负整数
	 *
	 */
	public static final String NEGATIVE_INTEGERS_REGEXP = "^-[0-9]*[1-9][0-9]*$";

	/**
	 *
	 * 匹配整数
	 *
	 */
	public static final String INTEGER_REGEXP = "^-?//d+$";

	/**
	 *
	 * 匹配非负浮点数（正浮点数 + 0）
	 *
	 */
	public static final String NON_NEGATIVE_RATIONAL_NUMBERS_REGEXP = "^//d+(//.//d+)?$";

	/**
	 *
	 * 匹配正浮点数
	 *
	 */
	public static final String POSITIVE_RATIONAL_NUMBERS_REGEXP = "^(([0-9]+//.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*//.[0-9]+)|([0-9]*[1-9][0-9]*))$";

	/**
	 *
	 * 匹配非正浮点数（负浮点数 + 0）
	 *
	 */
	public static final String NON_POSITIVE_RATIONAL_NUMBERS_REGEXP = "^((-//d+(//.//d+)?)|(0+(//.0+)?))$";

	/**
	 *
	 * 匹配负浮点数
	 *
	 */
	public static final String NEGATIVE_RATIONAL_NUMBERS_REGEXP = "^(-(([0-9]+//.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*//.[0-9]+)|([0-9]*[1-9][0-9]*)))$";

	/**
	 *
	 * 匹配浮点数
	 *
	 */
	public static final String RATIONAL_NUMBERS_REGEXP = "^(-?//d+)(//.//d+)?$";

	/**
	 *
	 * 匹配由26个英文字母组成的字符串
	 *
	 */
	public static final String LETTER_REGEXP = "^[A-Za-z]+$";

	/**
	 *
	 * 匹配由26个英文字母的大写组成的字符串
	 *
	 */
	public static final String UPWARD_LETTER_REGEXP = "^[A-Z]+$";

	/**
	 *
	 * 匹配由26个英文字母的小写组成的字符串
	 *
	 */
	public static final String LOWER_LETTER_REGEXP = "^[a-z]+$";

	/**
	 *
	 * 匹配由数字和26个英文字母组成的字符串
	 *
	 */
	public static final String LETTER_NUMBER_REGEXP = "^[A-Za-z0-9]+$";

	/**
	 *
	 * 匹配由数字、26个英文字母或者下划线组成的字符串
	 *
	 */
	public static final String LETTER_NUMBER_UNDERLINE_REGEXP = "^//w+$";

	/**
	 * 只能中文
	 *
	 * @param param
	 * @return
	 */
	public static boolean regexChina(String param) {
		return param.matches(CHINESE);
	}

	public static boolean regexEmail(String email) {
		return email.matches(EMAIL);
	}
}
