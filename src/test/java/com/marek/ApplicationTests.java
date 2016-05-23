package com.marek;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationTests.class)
public class ApplicationTests {
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(Application.class);

	@Test
	public void test1() {
		log.info("1st test");
	}

}
