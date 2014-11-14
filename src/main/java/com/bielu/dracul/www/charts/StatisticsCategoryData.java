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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.bielu.dracul.www.dao.IStatisticsDao;
import com.bielu.dracul.www.domain.ValueBean;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.tooltips.CategoryToolTipGenerator;

public class StatisticsCategoryData extends AbstractData implements DatasetProducer, CategoryToolTipGenerator {

	private static final long serialVersionUID = 1L;

	private static final Log LOG = LogFactory.getLog(StatisticsCategoryData.class);

	private static IStatisticsDao statisticsDao = null;

	private List<ValueBean> statistics = Collections.emptyList();
	
	private final Map<Object, ValueBean> statisticsMap = new HashMap<Object, ValueBean>();
	
	private double sum = 0;
	
	private int limit;

	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	public StatisticsCategoryData() {
		if (statisticsDao == null) {
			LOG.info("Creating new IStatisticsDao bean");
			statisticsDao = (IStatisticsDao) ctx.getBean("statisticsDao");
		}
	}
	
	/**
	 * Produces some random data.
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public Object produceDataset(Map params) throws DatasetProduceException {
		LOG.debug("Producing data...");
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		statistics = statisticsDao.loadUserAgentStatistics(limit);
		
		sum = 0;
		for (ValueBean stat : statistics) {
			dataset.addValue(stat.getValue(), "", stat.getName());
			statisticsMap.put(stat.getName(), stat);
			sum += (Double) stat.getValue();
		}
		
		return dataset;
	}

	/**
	 * Returns a unique ID for this DatasetProducer
	 */
	@Override
	public String getProducerId() {
		return "MostPopularImagesData DatasetProducer";
	}

	/**
	 * @see CategoryToolTipGenerator#generateToolTip(CategoryDataset, int, int)
	 */
	@Override
	public String generateToolTip(CategoryDataset arg0, int series, int item) {
		ValueBean stat = statistics.get(item);
		return ((Double) stat.getValue()).intValue() + " (" + format.format(((Double) stat.getValue() / sum) * 100D) + "%) - " + stat.getName();
	}
}