package com.in.serviceimpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.in.entity.UserEntity;
import com.in.repository.UserRepository;
import com.in.util.Utility;

@Transactional
@Service
public class PushNotificationServiceImpl {
	private static final Logger logger = LogManager.getLogger(PushNotificationServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	RestTemplate restTemplate;

	@Async
	public void pushNotification(int notificationType, String title, String desc) {
		try {
			String[] deviceToken = getDeviceTokens();
			pushNotificationUtil(deviceToken, title, desc, notificationType);
		} catch (JSONException e) {
			logger.error(e);
		}
	}

	private String[] getDeviceTokens() {
		List<UserEntity> users = userRepository.findAll();
		if (users != null) {
			String[] deviceTokens = new String[users.size()];
			for (int i = 0; i < deviceTokens.length; i++) {
				deviceTokens[i] = users.get(i).getDeviceToken();
			}
			return deviceTokens;
		} else
			return null;
	}

	public String pushNotificationUtil(String[] deviceToken, String title, String desc, int notificationType)
			throws JSONException {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", Utility.getProperty("fcm.auth.key"));
		headers.set("Content-Type", "application/json");
		JSONObject json = new JSONObject();
		// json.put("registration_ids", deviceToken);
		json.put("registration_ids", deviceToken);

		json.put("priority", "high");

		JSONObject notificationObj = new JSONObject();
		notificationObj.put("title", title);
		notificationObj.put("body", desc);
		notificationObj.put("order_id", 0);
		notificationObj.put("click_action", "FCM_PLUGIN_ACTIVITY");
		notificationObj.put("badge", "1");
		notificationObj.put("sound", "default");

		JSONObject dataObj = new JSONObject();
		dataObj.put("order_id", 0);
		dataObj.put("title", title);
		dataObj.put("description", desc);
		dataObj.put("push_type", notificationType);
		dataObj.put("datetime", new SimpleDateFormat("yyyy-mm-dd HH:mm").format(new Date()));

		json.put("notification", notificationObj);
		json.put("data", dataObj);
		String data = json.toString();
		HttpEntity<String> entity = new HttpEntity<String>(data.toString(), headers);

		String response = restTemplate.exchange(Utility.getProperty("fcm.url"), HttpMethod.POST, entity, String.class)
				.getBody();
		System.out.println(response);
		logger.info(response);
		return response;
	}

	private void main1() throws JSONException {
		if (restTemplate == null)
			restTemplate = new RestTemplate();
		String[] deviceToken = {
				"c5txg9ggTH-hClqDLJbm5P:APA91bHnRK_kUJ_O_2B_nYeuTxvz94FQGPpSNNxJFeu1JvtC9UyNEmGzAjGM7yBQdnMJDTbRy0iVpZ464vPpF-YHo9D8igUH24koteQ65JO9RRmvhK72RORqX8oyiPykSA8y29BGvV4T",
				"ceYmVIjxD0yiu63vZ_-ilo:APA91bFSPvLEWgqtSXDTL3lJkgZLHcspFFnSjOfrEuS6auVoapVdejAncWbeMYu17IQfYIIRIQQMUzYDIhTHYAC5FmrvfUbCybQsI52Ge6KADUpTLSrk85t8odMuEHwRGQB0Dg3_8b6s" };

		pushNotificationUtil(deviceToken, "hi", "Mew", 1);

	}

	public static void main(String[] args) {
		try {
			new PushNotificationServiceImpl().main1();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
