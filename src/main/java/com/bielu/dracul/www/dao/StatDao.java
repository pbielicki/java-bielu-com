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

import java.util.List;

import com.bielu.dracul.www.domain.Stat;

/**
 * <code>ImageDao</code> TODO provide description
 *
 * @author Przemyslaw Bielicki
 */
public class StatDao extends Dao<Stat> implements IStatDao {

	/**
	 * @{inheritDoc}
	 */
	public Stat findById(long id) {
		return (Stat) getHibernateTemplate().get(Stat.class, new Long(id));
	}
	
	/**
	 * @{inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<Stat> loadAll() {
		return getHibernateTemplate().find("from " + Stat.class.getName());
	}
}
