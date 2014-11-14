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
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.bielu.dracul.www.dao.IDirectoryInfoDao;
import com.bielu.dracul.www.domain.DirectoryInfo;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.links.CategoryItemLinkGenerator;
import de.laures.cewolf.tooltips.CategoryToolTipGenerator;

public class DirectoryInfoData extends AbstractData implements DatasetProducer, CategoryToolTipGenerator, CategoryItemLinkGenerator {

	private static final long serialVersionUID = 1L;

	private static final Log LOG = LogFactory.getLog(DirectoryInfoData.class);

	private static IDirectoryInfoDao directoryDao = null;

	private List<DirectoryInfo> directories = Collections.emptyList();
	
	private final Map<Object, DirectoryInfo> directoriesMap = new HashMap<Object, DirectoryInfo>();

	private double sum;
	
	public DirectoryInfoData() {
		if (directoryDao == null) {
			LOG.info("Creating new IDirectoryInfoDao bean");
			directoryDao = (IDirectoryInfoDao) ctx.getBean("directoryInfoDao");
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
		
		directories = directoryDao.loadAllOrderByCount();
		
		sum = 0;
		for (DirectoryInfo dir : directories) {
			String key = "" + dir.getId();
			dataset.addValue(dir.getCount(), "Visits", key);
			directoriesMap.put(key, dir);
			sum += dir.getCount();
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
		DirectoryInfo dir = directories.get(item);
		return dir.getName() + " - " + (int) dir.getCount() + " views (" + format.format((dir.getCount() / sum) * 100D) + "%)";
	}

	private String getStrutsExtension() {
		Properties p = new Properties();
		try {
			p.load(getClass().getResourceAsStream("charts.properties"));
		} catch (Exception e) {
			return ".jsps";
		}
		return p.getProperty("struts.extension", ".jsps");
	}
	
	/**
	 * @{inheritDoc}
	 */
	@Override
	public String generateLink(Object dataset, int series, Object category) {
		return "http://java.bielu.com/list" + getStrutsExtension() + "?dir=" + category + "&thumbs=true";
	}
}