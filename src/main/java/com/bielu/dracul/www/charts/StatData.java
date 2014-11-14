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

import com.bielu.dracul.www.dao.IStatDao;
import com.bielu.dracul.www.domain.Stat;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.links.PieSectionLinkGenerator;
import de.laures.cewolf.tooltips.PieToolTipGenerator;

public class StatData extends AbstractData implements DatasetProducer, PieToolTipGenerator, PieSectionLinkGenerator {

	private static final long serialVersionUID = 1L;

	private static final Log LOG = LogFactory.getLog(StatData.class);

	private static IStatDao statDao = null;
	
	private static Map<String, String> LINK_MAP = new HashMap<String, String>(2);
	static {
		LINK_MAP.put("asia", "azja");
		LINK_MAP.put("sicily", "sycylia");
	}

	private List<Stat> stats = Collections.emptyList();
	
	private final Map<Object, Stat> statsMap = new HashMap<Object, Stat>();
	
	private double sum = 0;

	public StatData() {
		if (statDao == null) {
			LOG.info("Creating new IStatDao bean");
			statDao = (IStatDao) ctx.getBean("statDao");
		}
	}
	
	/**
	 * Produces some random data.
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public Object produceDataset(Map params) throws DatasetProduceException {
		LOG.debug("Producing data...");
		DefaultPieDataset dataset = new DefaultPieDataset();
		stats = statDao.loadAll();
		
		sum = 0;
		for (Stat stat : stats) {
			dataset.setValue(stat.getPage(), stat.getCount());
			statsMap.put(stat.getPage(), stat);
			sum += stat.getCount();
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
	@Override
	@SuppressWarnings("rawtypes")
	public String generateToolTip(PieDataset data, Comparable key, int pieIndex) {
		Stat stat = statsMap.get(key);
		return stat.getPage() + " - visits (" + Math.round((stat.getCount() / sum) * 100D) + "%)";
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public String generateLink(Object dataset, Object key) {	
		Stat stat = statsMap.get(key);
		return "http://www.bielu.com/" + LINK_MAP.get(stat.getPage().toLowerCase());
	}
}