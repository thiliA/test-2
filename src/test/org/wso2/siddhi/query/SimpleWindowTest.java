/*
 * Copyright (c) 2005-2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * 
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.siddhi.query;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;

public class SimpleWindowTest {

	private static final Logger LOGGER = Logger.getLogger(SimpleWindowTest.class);

	public static void main(String[] args) {
		BufferedReader br1 = null;
		BufferedReader br2 = null;

		int matchedEvents = 0;

		SimpleWindow window = new SimpleWindow(10000, 1, 1);
		Date startDate = new Date();

		try {

			String sCurrentLine1, sCurrentLine2;

			br1 = new BufferedReader(new FileReader("EventStream1.txt"));
			br2 = new BufferedReader(new FileReader("EventStream2.txt"));

			while (((sCurrentLine1 = br1.readLine()) != null) |
			       ((sCurrentLine2 = br2.readLine()) != null)) {

				Date date = new Date();

				if (sCurrentLine1 != null) {
					String str1 = sCurrentLine1;
					String[] splited1 = str1.split(" ");

					Object[] ob1 = { splited1[0], splited1[1], splited1[2], splited1[3] };

					SiddhiEvent event1 = new SiddhiEvent("1", date.getTime(), ob1);

					window.addEvent(event1);
				}

				if (sCurrentLine2 != null) {
					String str2 = sCurrentLine2;
					String[] splited2 = str2.split(" ");
					Object[] ob2 = { splited2[0], splited2[1], splited2[2] };

					SiddhiEvent event2 = new SiddhiEvent("2", date.getTime(), ob2);

					window.joinEvent(event2, 1, 2);
				}

			}

		} catch (IOException e) {
			LOGGER.error("IO Exception", e);
		} finally {
			try {
				if (br1 != null) {
					br1.close();
				}
				if (br2 != null) {
					br2.close();
				}
			} catch (IOException e) {
				LOGGER.error("IO Exception", e);
			}
		}

		Date endDate = new Date();

		long diff = endDate.getTime() - startDate.getTime();

		LOGGER.info("Started - " + startDate.getTime());
		LOGGER.info("Ended - " + endDate.getTime());

		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;

		LOGGER.info("Time duration - " + diffHours + ":" + diffMinutes + ":" + diffSeconds);

		LOGGER.info("Matched events - " + matchedEvents);

	}
}
