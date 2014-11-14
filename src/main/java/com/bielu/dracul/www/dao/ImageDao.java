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
package com.bielu.dracul.www.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.bielu.dracul.www.domain.Image;

/**
 * <code>ImageDao</code> TODO provide description
 * 
 * @author Przemyslaw Bielicki
 */
@SuppressWarnings("unchecked")
public class ImageDao extends Dao<Image> implements IImageDao {	

	private static final int MAX_LIMIT = 50;

	/**
	 * @{inheritDoc}
	 */
	public Image findById(long id) {
		return (Image) getHibernateTemplate().get(Image.class, new Long(id));
	}

	/**
	 * @{inheritDoc}
	 */
	public List<Image> loadAll() {
		return getHibernateTemplate().find("from " + Image.class.getName());
	}

	/**
	 * @{inheritDoc}
	 */
	public List<Image> loadByCountry(long countryId) {
		return getHibernateTemplate().find(
				"from " + Image.class.getName() + " as img where img.directoryInfo.countryInfo.id = ?", countryId);
	}

	/**
	 * @{inheritDoc}
	 */
	public List<Image> loadByDirectory(long directoryId) {
		return getHibernateTemplate().find("from " + Image.class.getName() + " as img where img.directoryInfo.id = ?",
				directoryId);
	}

	/**
	 * @{inheritDoc}
	 */
	public List<Image> loadByRegion(String region) {
		if ("asia".equals(region)) {
			return getHibernateTemplate().find(
					"from " + Image.class.getName() + " as img where img.directoryInfo.countryInfo is not null");
			
		} else if ("sicily".equals(region)) {
			return getHibernateTemplate().find(
					"from " + Image.class.getName() + " as img where img.directoryInfo.countryInfo is null");
			
		} else {
			return new ArrayList<Image>();
		}
	}

	/**
	 * @{inheritDoc}
	 */
	public List<Image> loadLeastPoplularImages(int limit) {
		if (limit > MAX_LIMIT) {
			limit = MAX_LIMIT;
		}
		HibernateTemplate t = getHibernateTemplate();
		t.setMaxResults(limit);
		
		return t.find("from " + Image.class.getName() + " as img order by img.count asc");
	}

	/**
	 * @{inheritDoc}
	 */
	public List<Image> loadMostPoplularImages(int limit) {
		if (limit > MAX_LIMIT) {
			limit = MAX_LIMIT;
		}
		HibernateTemplate t = getHibernateTemplate();
		t.setMaxResults(limit);
		
		return t.find("from " + Image.class.getName() + " as img order by img.count desc");
	}

	/**
	 * @{inheritDoc}
	 */
	public double getCountSumByRegion(String region) {
		String sql = "select sum(count) from img, sub where img.id_sub = sub.id and sub.id_par is ";
		
		if ("asia".equals(region)) {
			return ((BigDecimal) getSession().createSQLQuery(sql + "not null").list().get(0)).doubleValue();
		} else if ("sicily".equals(region)) {
			return ((BigDecimal) getSession().createSQLQuery(sql + "null").list().get(0)).doubleValue();
		}
		return 0;
	}
}
