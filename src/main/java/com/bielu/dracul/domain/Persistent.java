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
package com.bielu.dracul.domain;

/**
 * TODO: provide description. <code>Persistent</code>
 */
public abstract class Persistent implements Identifiable {
	/**
	 * 
	 */
	public static final String ID = "id";
	
	/**
	 * 
	 */
	private long id;
	
	/**
	 * TODO: provide description
	 */
	public Persistent() {
		id = UNSAVED_ID;
	}
	
	/**
	 * TODO: provide description
	 * @return
	 */
	public boolean isSaved() {
		return getId() > UNSAVED_ID;
	}
	
	/**
	 * @see Identifiable#getId()
	 */
	public long getId() {
		return id;
	}

	/**
	 * @see Identifiable#setId(long)
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getTableName() + ": Id = " + getId();
	}
	
	/**
	 * TODO: provide description
	 * @return
	 */
	public abstract String getTableName();
}
