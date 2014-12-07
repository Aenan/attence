/**
 * @author Cui Kang
 */
package cn.edu.tju.se.base.service;

/**
 * Debug类用于输出简单的字符串信息,统一调试风格
 */
public class Debug {
	/*
	 * 调试状态标志 0：发行状态，不输出任何调试信息 1：调试状态，只输出错误提示信息 2：调试状态，输出所有调试信息，包括一般信息和错误提示信息
	 */

	private static final int NO_MESSAGES = 0;
	private static final int ERROR_MESSAGES = 1;
	private static final int ALL_MESSAGES = 2;
	private static int debugState = ALL_MESSAGES;// 调试状态标志

	private final static String debugString = "Debug--> ";
	private final static String errorString = "Error--> ";

	/**
	 * 设置状态标志
	 */
	public static void setDebugState(int state) {
		debugState = state;
	}

	/**
	 * 取状态标志
	 * 
	 * @return
	 */
	public static int getDebugState() {
		return debugState;
	}

	/**
	 * 根据状态标志输出调试信息
	 * 
	 * @param string
	 */
	public static void out(String string) {
		if (debugState == ALL_MESSAGES) {
			System.out.println(debugString + string);
		}
	}

	public static void error(String string) {
		if (debugState == ERROR_MESSAGES || debugState == ALL_MESSAGES) {
			System.out.println(errorString + string);
		}
	}

	/*
	 * public static void Out(String string) { out(string); }
	 */

	public static void out(int n) {
		out("[Integer]--> " + n);
	}

	public static void out(double x) {
		out("[Double]--> " + x);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Debug.out("hello");
	}

}
