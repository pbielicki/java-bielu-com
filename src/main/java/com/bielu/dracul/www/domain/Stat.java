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
package com.bielu.dracul.www.domain;

import com.bielu.dracul.domain.Countable;
import com.bielu.dracul.domain.Identifiable;
import com.bielu.dracul.domain.Persistent;

/**
 * @author pbielick
 * 
 */
public class Stat extends Persistent implements Identifiable, Countable {

	private String page;
	
	private long count;

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	/**
	 * {@inheritDoc}
	 */
	public long getCount() {
		return count;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setCount(long count) {
		this.count = count;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getTableName() {
		return "STAT";
	}

	/**
	 * @{inheritDoc}
	 */
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((page == null) ? 0 : page.hashCode());
		return result;
	}

	/**
	 * @{inheritDoc}
	 */
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (!(obj instanceof Stat)) {
			return false;
		}

		final Stat other = (Stat) obj;
		if (page == null) {
			if (other.page != null) {
				return false;
			}
		} else if (!page.equals(other.page)) {
			return false;
		}

		return true;
	}
}
