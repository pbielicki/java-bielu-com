/* 
 *  Copyright 2007 Przemyslaw Bielicki All Rights Reserved.
 *
 *  The source code contained or described herein and all documents related to
 *  the source code ("Material") are owned by Przemyslaw Bielicki or its suppliers 
 *  or licensors. Title to the Material remains with Przemyslaw Bielicki or its 
 *  suppliers and licensors. The Material contains trade secrets and proprietary 
 *  and confidential information of Przemyslaw Bielicki or its suppliers and licensors. 
 *  The Material is protected by worldwide copyright and trade secret laws and treaty 
 *  provisions. No part of the Material may be used, copied, reproduced, modified, 
 *  published, uploaded, posted, transmitted, distributed, or disclosed in any way 
 *  without Przemyslaw Bielicki's prior express written permission.
 *
 *  No license under any patent, copyright, trade secret or other intellectual 
 *  property right is granted to or conferred upon you by disclosure or delivery 
 *  of the Materials, either expressly, by implication, inducement, estoppel 
 *  or otherwise. Any license under such intellectual property rights must be express 
 *  and approved by Przemyslaw Bielicki in writing.
 */
package com.bielu.dracul.www.charts;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <code>AbstractData</code> TODO provide description
 *
 * @author Przemyslaw Bielicki
 */
public class AbstractData {

	private static final Log LOG = LogFactory.getLog(AbstractData.class);

	protected static ApplicationContext ctx = null;
	
	protected static NumberFormat format = NumberFormat.getInstance();
	static {
		format.setMaximumFractionDigits(2);
	}

	public void setSpringContext(ApplicationContext ctx) {
		AbstractData.ctx = ctx;
	}
	
	public AbstractData() {
		if (ctx == null) {
			LOG.info("Creating new Spring application context");
			ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		}
	}
	
	/**
	 * This producer's data is invalidated after 50 seconds. By this method the producer can influence Cewolf's caching behaviour
	 * the way it wants to.
	 */
	@SuppressWarnings("rawtypes")
	public boolean hasExpired(Map params, Date since) {
		LOG.debug(getClass().getName() + " hasExpired()");	
		return (System.currentTimeMillis() - since.getTime()) > 50000;
	}

}