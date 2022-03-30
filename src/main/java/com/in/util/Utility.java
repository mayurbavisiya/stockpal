package com.in.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import com.in.exception.ValidationException;

public class Utility {

	public static String getStringFromDate(Date date, boolean isTimeInclude) {
		if (date == null)
			return "";
		DateFormat dateFormat;
		if (isTimeInclude)
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		else
			dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String strDate = dateFormat.format(date);
		return strDate;
	}

	public static Date getDateFromString(String inputDate) throws ValidationException {
		if (inputDate == null || "".equals(inputDate))
			return null;
		Date date = null;
		try {
			date = new SimpleDateFormat("dd/mm/yyyy").parse(inputDate);
		} catch (ParseException e) {
			throw new ValidationException("invalidDataCode", "invalidDateFormatDesc", "successBooleanFalse");
		}
		return date;
	}

	public static String uniqueCurrentTimeMS() {
		MessageDigest instance;
		try {
			instance = MessageDigest.getInstance("MD5");

			byte[] messageDigest = instance.digest(String.valueOf(System.nanoTime()).getBytes());
			StringBuilder hexString = new StringBuilder();
			for (int i = 0; i < messageDigest.length; i++) {
				String hex = Integer.toHexString(0xFF & messageDigest[i]);
				if (hex.length() == 1) {
					// could use a for loop, but we're only dealing with a
					// single
					// byte
					hexString.append('0');
				}
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	public static String getProperty(String key) {
		return ResourceBundle.getBundle("application", Locale.ENGLISH).getString(key);
	}

	public static String getFCMProperty(String key) {
		return ResourceBundle.getBundle("notification_fcm", Locale.ENGLISH).getString(key);
	}
}
