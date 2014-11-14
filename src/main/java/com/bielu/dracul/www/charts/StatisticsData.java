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
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import com.bielu.dracul.www.dao.IStatisticsDao;
import com.bielu.dracul.www.domain.ValueBean;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.tooltips.PieToolTipGenerator;

public class StatisticsData extends AbstractData implements DatasetProducer, PieToolTipGenerator {

	private static final long serialVersionUID = 1L;

	private static final Log LOG = LogFactory.getLog(StatisticsData.class);

	private static IStatisticsDao statisticsDao = null;

	private List<ValueBean> statistics = Collections.emptyList();
	
	private final Map<Object, ValueBean> statisticsMap = new HashMap<Object, ValueBean>();
	
	private double sum = 0;
	
	private String type = "method";
	
	public void setType(String type) {
		this.type = type;
	}

	public StatisticsData() {
		if (statisticsDao == null) {
			LOG.info("Creating new IStatisticsDao bean");
			statisticsDao = (IStatisticsDao) ctx.getBean("statisticsDao");
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
		
		if ("method".equals(type)) {
			statistics = statisticsDao.loadMethodStatistics();
		} else if ("protocol".equals(type)) {
			statistics = statisticsDao.loadProtocolStatistics();
		} else if ("scheme".equals(type)) {
			statistics = statisticsDao.loadSchemeStatistics();
		} else if ("executionTime".equals(type)) {
			statistics = statisticsDao.loadExecutionTimeStatistics();
		}
		
		sum = 0;
		for (ValueBean stat : statistics) {
			dataset.setValue(stat.getName(), stat.getValue());
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
		return "StatData DatasetProducer";
	}

	/**
	 * @{inheritDoc}
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String generateToolTip(PieDataset data, Comparable key, int pieIndex) {
		ValueBean stat = statisticsMap.get(key);
		return stat.getName() + " (" + format.format(((Double) stat.getValue() / sum) * 100D) + "%)";
	}
}