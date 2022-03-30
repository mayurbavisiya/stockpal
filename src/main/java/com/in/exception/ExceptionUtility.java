package com.in.exception;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExceptionUtility {

	private static ExceptionUtility uTil;

	private ExceptionUtility() {
	}

	public static synchronized ExceptionUtility getInstance() {
		if (uTil == null) {
			uTil = new ExceptionUtility();
		}
		return uTil;
	}

	public static String getDefaultIfNull(String paramVal, String defaultVal) {
		if ((paramVal != null) && (paramVal.trim().length() > 0) && !(paramVal.equalsIgnoreCase("null"))) {
			return paramVal;
		} else {
			return defaultVal;
		}
	}

	public static Boolean isNumber(String paramVal) {
		boolean isNumber = false;
		if ((paramVal != null) && (paramVal.trim().length() > 0) && !(paramVal.equalsIgnoreCase("null"))) {
			try {
				Long.valueOf(paramVal);
				isNumber = true;
			} catch (Exception e) {

			}
		}

		return isNumber;
	}

	public static String getDefaultIfZero(String paramVal, String defaultVal) {
		if ((paramVal != null) && (paramVal.trim().length() > 0) && !(paramVal.equalsIgnoreCase("null"))
				&& !(paramVal.equalsIgnoreCase("0"))) {
			return paramVal;
		} else {
			return defaultVal;
		}
	}

	public static void throwExceptionIfNull(String param, String exceptionMsg) throws ValidationException {
		if (getDefaultIfNull(param, null) == null) {
			throw new ValidationException(exceptionMsg);
		}
	}

	public static void throwExceptionIfNull(long param, String exceptionMsg) throws ValidationException {
		if (param == 0) {
			throw new ValidationException(exceptionMsg);
		}
	}

	public static String getAppProperty(String key) {
		return ResourceBundle.getBundle("app_properties", Locale.ENGLISH).getString(key);
	}

	public static String getCode(String key) {
		try {
			return ResourceBundle.getBundle("error", Locale.ENGLISH).getString(key);
		} catch (Exception ex) {
			return "0";
		}
	}

	public static String removeScriptTags(String message) {
		String scriptRegex = "<(/)?[ ]*script[^>]*>";
		Pattern pattern2 = Pattern.compile(scriptRegex);

		if (message != null) {
			Matcher matcher2 = pattern2.matcher(message);
			StringBuffer str = new StringBuffer(message.length());
			while (matcher2.find()) {
				matcher2.appendReplacement(str, Matcher.quoteReplacement(" "));
			}
			matcher2.appendTail(str);
			message = str.toString();
		}
		return message;
	}

}