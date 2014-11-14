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

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;

import com.bielu.dracul.www.domain.DirectoryInfo;

/**
 * <code>ImageDao</code> TODO provide description
 *
 * @author Przemyslaw Bielicki
 */
public class DirectoryInfoDao extends Dao<DirectoryInfo> implements IDirectoryInfoDao {

	/**
	 * @{inheritDoc}
	 */
	public DirectoryInfo findById(long id) {
		return (DirectoryInfo) getHibernateTemplate().get(DirectoryInfo.class, new Long(id));
	}
	
	/**
	 * @{inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<DirectoryInfo> loadAll() {
		return getHibernateTemplate().find("from " + DirectoryInfo.class.getName());
	}

	/**
	 * @{inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<DirectoryInfo> loadByRegion(String country) {
		if ("asia".equals(country)) {
			return getHibernateTemplate().find("from " + DirectoryInfo.class.getName() + " as d where d.countryInfo is not null");
		} else if ("sicily".equals(country)) {
			return getHibernateTemplate().find("from " + DirectoryInfo.class.getName() + " as d where d.countryInfo is null");
		} else {
			return new ArrayList<DirectoryInfo>();
		}
	}

	/**
	 * @{inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<DirectoryInfo> loadAllOrderByCount() {
		List<Object[]> list = getSession(true).createSQLQuery("select img.id_sub, sub.nazwa, sum(img.count) as count " +
				"from img, sub where img.id_sub = sub.id group by img.id_sub order by count desc")
				.addScalar("id_sub", Hibernate.INTEGER)
				.addScalar("nazwa", Hibernate.STRING)
				.addScalar("count", Hibernate.DOUBLE)
				.list();
		
		List<DirectoryInfo> value = new ArrayList<DirectoryInfo>(list.size());
		
		for (Object[] o : list) {
			DirectoryInfo info = new DirectoryInfo();
			info.setId((Integer) o[0]);
			info.setName(o[1].toString());
			info.setCount(((Double) o[2]));
			value.add(info);
		}
		return value;
	}
}
