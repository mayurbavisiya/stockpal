package com.in.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PlaceOrderToVendorScheduler {

	private static final Logger logger = LogManager.getLogger(PlaceOrderToVendorScheduler.class);

	@Autowired
	PlaceOrderToVendorSchedulerImpl placeOrderToVendorScheduler;

	// Send notification every day at 01:01 AM
	@Scheduled(cron = "0 1 1 * * ?")
	public void cronJobSch() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date now = new Date();
		String strDate = sdf.format(now);
		System.out.println("Java cron job expression:: " + strDate);
		logger.info("Java cron job expression:: " + strDate);
		placeOrderToVendorScheduler.execute();
	}

}
