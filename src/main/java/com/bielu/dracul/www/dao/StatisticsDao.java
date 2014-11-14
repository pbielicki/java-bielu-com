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

import com.bielu.dracul.www.domain.Statistics;
import com.bielu.dracul.www.domain.ValueBean;

/**
 * <code>ImageDao</code> TODO provide description
 *
 * @author Przemyslaw Bielicki
 */
public class StatisticsDao extends Dao<Statistics> implements IStatisticsDao {

	/**
	 * @{inheritDoc}
	 */
	public Statistics findById(long id) {
		return (Statistics) getHibernateTemplate().get(Statistics.class, new Long(id));
	}
	
	/**
	 * @{inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<Statistics> loadAll() {
		return getHibernateTemplate().find("from " + Statistics.class.getName());
	}

	/**
	 * @{inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<ValueBean> loadMethodStatistics() {
		List<Object[]> list = getSession().createSQLQuery("select method, count(*) as count from statistics " +
				"group by method order by count desc")
				.addScalar("method", Hibernate.STRING)
				.addScalar("count", Hibernate.DOUBLE).list();
		
		List<ValueBean> result = new ArrayList<ValueBean>(list.size());
		for (Object[] o : list) {
			result.add(new ValueBean(o[0].toString(), (Double) o[1]));
		}
		
		return result;
	}

	/**
	 * @{inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<ValueBean> loadProtocolStatistics() {
		List<Object[]> list = getSession().createSQLQuery("select protocol, count(*) as count from statistics " +
				"group by protocol order by count desc")
				.addScalar("protocol", Hibernate.STRING)
				.addScalar("count", Hibernate.DOUBLE).list();

		List<ValueBean> result = new ArrayList<ValueBean>(list.size());
		for (Object[] o : list) {
			result.add(new ValueBean(o[0].toString(), (Double) o[1]));
		}

		return result;
	}

	/**
	 * @{inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<ValueBean> loadSchemeStatistics() {
		List<Object[]> list = getSession().createSQLQuery("select scheme, count(*) as count from statistics " +
				"group by scheme order by count desc")
				.addScalar("scheme", Hibernate.STRING)
				.addScalar("count", Hibernate.DOUBLE).list();
		
		List<ValueBean> result = new ArrayList<ValueBean>(list.size());
		for (Object[] o : list) {
			result.add(new ValueBean(o[0].toString(), (Double) o[1]));
		}
		
		return result;
	}

	/**
	 * @{inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<ValueBean> loadUserAgentStatistics(int limit) {
		String limitString = "";
		if (limit > 0) {
			limitString = " limit " + limit;
		}
		List<Object[]> list = getSession().createSQLQuery("select userAgent, count(*) as count from statistics " +
				"where userAgent <> 'Unknown' group by userAgent order by count desc" + limitString)
				.addScalar("userAgent", Hibernate.STRING)
				.addScalar("count", Hibernate.DOUBLE).list();
		
		List<ValueBean> result = new ArrayList<ValueBean>(list.size());
		for (Object[] o : list) {
			if (o[0] != null) {
				result.add(new ValueBean(o[0].toString(), (Double) o[1]));
			}
		}
		
		return result;
	}

	/**
	 * @{inheritDoc}
	 */
	public List<ValueBean> loadExecutionTimeStatistics() {
		List<ValueBean> result = new ArrayList<ValueBean>();
		
		Double value = (Double) getSession().createSQLQuery("select count(*) as count from statistics " +
				"where executionTime < 1000")
				.addScalar("count", Hibernate.DOUBLE)
				.list()
				.get(0);
		
		result.add(new ValueBean("< 1 sec.", value));
		
		value = (Double) getSession().createSQLQuery("select count(*) as count from statistics " +
				"where executionTime >= 1000 and executionTime < 2000")
				.addScalar("count", Hibernate.DOUBLE)
				.list()
				.get(0);

		result.add(new ValueBean("1 - 2 sec.", value));

		value = (Double) getSession().createSQLQuery("select count(*) as count from statistics " +
                "where executionTime >= 2000 and executionTime < 3000")
                .addScalar("count", Hibernate.DOUBLE)
                .list()
                .get(0);

		result.add(new ValueBean("2 - 3 sec.", value));

        value = (Double) getSession().createSQLQuery("select count(*) as count from statistics " +
                "where executionTime >= 3000 and executionTime < 5000")
                .addScalar("count", Hibernate.DOUBLE)
                .list()
                .get(0);

	    result.add(new ValueBean("3 - 5 sec.", value));
		
		value = (Double) getSession().createSQLQuery("select count(*) as count from statistics " +
				"where executionTime >= 5000 and executionTime < 10000")
				.addScalar("count", Hibernate.DOUBLE)
				.list()
				.get(0);

		result.add(new ValueBean("5 - 10 sec.", value));
		
		value = (Double) getSession().createSQLQuery("select count(*) as count from statistics " +
				"where executionTime >= 10000 and executionTime < 20000")
				.addScalar("count", Hibernate.DOUBLE)
				.list()
				.get(0);

		result.add(new ValueBean("10 - 20 sec.", value));
		
		value = (Double) getSession().createSQLQuery("select count(*) as count from statistics " +
				"where executionTime >= 20000 and executionTime < 50000")
				.addScalar("count", Hibernate.DOUBLE)
				.list()
				.get(0);

		result.add(new ValueBean("20 - 50 sec.", value));
		
		value = (Double) getSession().createSQLQuery("select count(*) as count from statistics " +
				"where executionTime >= 50000")
				.addScalar("count", Hibernate.DOUBLE)
				.list()
				.get(0);

		result.add(new ValueBean("> 50 sec.", value));
		return result;
	}
}
