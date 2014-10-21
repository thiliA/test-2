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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * TestGenerator class creates two event streams, written into text files
 * separately to take is as inputs to the window implementation.
 * 
 */
public class TestGenerator {

	private static final Logger LOGGER = Logger.getLogger(TestGenerator.class);

	private TestGenerator() {

	}

	public static void main(String[] args) {

		String[] brand = new String[10];
		brand[0] = "IBM";
		brand[1] = "Samsung";
		brand[2] = "Azus";
		brand[3] = "Toshiba";
		brand[4] = "Lenovo";
		brand[5] = "Apple";
		brand[6] = "Dell";
		brand[7] = "HP";
		brand[8] = "Singer";
		brand[9] = "LG";

		String[] usage = new String[3];
		usage[0] = "used";
		usage[1] = "new";
		usage[2] = "refurbished";

		Random random = new Random();

		try {
			PrintWriter out1 =
			                   new PrintWriter(
			                                   new BufferedWriter(
			                                                      new FileWriter(
			                                                                     "EventStream1.txt",
			                                                                     true)));
			PrintWriter out2 =
			                   new PrintWriter(
			                                   new BufferedWriter(
			                                                      new FileWriter(
			                                                                     "EventStream2.txt",
			                                                                     true)));
			for (int i = 0; i < 10000000; i++) {

				Object[] ob1 =
				               { brand[random.nextInt(10)], usage[random.nextInt(3)],
				                random.nextInt(100000), random.nextInt(20) };

				out1.println(ob1[0].toString() + " " + ob1[1].toString() + " " + ob1[2].toString() +
				             " " + ob1[3].toString());

				String itemBrand2 = brand[random.nextInt(10)];

				if (i % 10 == 0) {
					itemBrand2 = brand[0];
				}

				Object[] ob2 = { itemBrand2, random.nextInt(100000), random.nextInt(20) };

				out2.println(ob2[0].toString() + " " + ob2[1].toString() + " " + ob2[2].toString());

			}
			out1.close();
			out2.close();

		} catch (IOException e) {
			LOGGER.error("IO Exception", e);
		}

	}

}
