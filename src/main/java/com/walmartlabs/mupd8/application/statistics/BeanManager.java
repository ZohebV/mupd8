/**
 * Copyright 2011-2012 @WalmartLabs, a division of Wal-Mart Stores, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.walmartlabs.mupd8.application.statistics;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

public class BeanManager {

	public static final BeanManager INSTANCE = new BeanManager();
	MBeanServer mbs;

	private BeanManager() {
		initialize();
	}

	public void registerBean(StatisticsMXBean bean) throws Exception {
		System.out.println(" Registered Bean " + bean.getName());
		ObjectName mbeanName = new ObjectName(bean.getName());
		mbs.registerMBean((Object) bean, mbeanName);
	}

	public void initialize() {
		mbs = ManagementFactory.getPlatformMBeanServer();
	}

}
