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

import com.bielu.dracul.www.dao.IImageDao;
import com.bielu.dracul.www.domain.Image;
import com.bielu.jsp.util.JspUtil;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.links.PieSectionLinkGenerator;
import de.laures.cewolf.tooltips.PieToolTipGenerator;

public class ImagesData extends AbstractData implements DatasetProducer, PieToolTipGenerator, PieSectionLinkGenerator {

	private static final long serialVersionUID = 1L;

	private static final Log LOG = LogFactory.getLog(ImagesData.class);

	private static IImageDao imageDao = null;

	private int limit = 10;

	private List<Image> images = Collections.emptyList();
	
	private final Map<Object, Image> imagesMap = new HashMap<Object, Image>();

	private boolean ascending = false;
	
	public void setLimit(int limit) {
		this.limit  = limit;
	}
	
	public void setAscending(boolean asc) {
		this.ascending = asc;
	}
	
	public ImagesData() {
		if (imageDao == null) {
			LOG.info("Creating new IImageDao bean");
			imageDao = (IImageDao) ctx.getBean("imageDao");
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
		
		if (ascending == true) {
			images = imageDao.loadLeastPoplularImages(limit);
		} else {
			images  = imageDao.loadMostPoplularImages(limit);
		}
		
		for (Image img : images) {
			String key = img.getShortDescription() + " (" + img.getPath() + ")";
			dataset.setValue(key.trim(), img.getCount());
			imagesMap.put(key.trim(), img);
		}
		
		return dataset;
	}

	/**
	 * Returns a unique ID for this DatasetProducer
	 */
	@Override
	public String getProducerId() {
		return "ImagesData DatasetProducer";
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public String generateToolTip(PieDataset data, Comparable key, int pieIndex) {
		return key.toString();
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public String generateLink(Object dataset, Object key) {	
		Image img = imagesMap.get(key);
		return JspUtil.imageBaseUrl(img) + img.getDirectoryInfo().getDir() + img.getPath();
	}
}