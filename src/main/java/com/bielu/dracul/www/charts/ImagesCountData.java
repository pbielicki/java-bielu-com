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

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import com.bielu.dracul.www.dao.IImageDao;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.tooltips.PieToolTipGenerator;

public class ImagesCountData extends AbstractData implements DatasetProducer, PieToolTipGenerator {

	private static final long serialVersionUID = 1L;

	private static final Log LOG = LogFactory.getLog(ImagesCountData.class);

	private static IImageDao imageDao = null;

	private double asiaCount;

	private double sicilyCount;
	
	private int asiaRate; 

	public ImagesCountData() {
		if (imageDao == null) {
			LOG.info("Creating new IImage bean");
			imageDao = (IImageDao) ctx.getBean("imageDao");
		}
	}
	
	/**
	 * Produces some random data.
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object produceDataset(Map params) throws DatasetProduceException {
		LOG.debug("Producing data...");
		DefaultPieDataset dataset = new DefaultPieDataset();

		asiaCount = imageDao.getCountSumByRegion("asia");
		sicilyCount = imageDao.getCountSumByRegion("sicily");
		asiaRate = (int) (asiaCount / (asiaCount + sicilyCount) * 100);
		
		dataset.setValue("Asia", (int) asiaCount);
		dataset.setValue("Sicily", (int) sicilyCount);
		
		return dataset;
	}

	/**
	 * Returns a unique ID for this DatasetProducer
	 */
	@Override
	public String getProducerId() {
		return "ImagesCountData DatasetProducer";
	}

	/**
	 * @{inheritDoc}
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String generateToolTip(PieDataset data, Comparable key, int pieIndex) {
		String value = key.toString() + " - ";
		if ("Asia".equals(key.toString())) {
			value += asiaCount + " hits (" + asiaRate + "%)";
		} else if ("Sicily".equals(key.toString())) {
			value += sicilyCount + " hits (" + (100 - asiaRate) + "%)";
		}
		return value;
	}
}