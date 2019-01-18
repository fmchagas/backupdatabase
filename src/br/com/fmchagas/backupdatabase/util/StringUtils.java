package br.com.fmchagas.backupdatabase.util;

public class StringUtils {

	public static boolean isEmpty(String s) {
		if (s == null) {
			return true;
		}
		return s.trim().length() == 0;
	}
}
