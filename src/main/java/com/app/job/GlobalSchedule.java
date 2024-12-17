package com.app.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GlobalSchedule {

	@Scheduled(cron = "${app.job.check-users.cron}")
	// @Async
	public void checkUsers() throws InterruptedException {
		log.info(">>>>>>> my schedule");
		Thread.sleep(3000);
	}

}
