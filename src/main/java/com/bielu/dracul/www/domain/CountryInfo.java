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

import java.util.ArrayList;
import java.util.Collection;

import com.bielu.dracul.domain.Identifiable;
import com.bielu.dracul.domain.Persistent;

/**
 * <code>CountryInfo</code> TODO provide description
 * 
 * @author Przemyslaw Bielicki
 */
public class CountryInfo extends Persistent implements Identifiable {

	private String country;

	private String dir;

	private String flag;

	private String flagLink;
	
	private String background;

	private Collection<DirectoryInfo> directories = new ArrayList<DirectoryInfo>();

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getFlagLink() {
		return flagLink;
	}

	public void setFlagLink(String flagLink) {
		this.flagLink = flagLink;
	}

	public Collection<DirectoryInfo> getDirectories() {
		return directories;
	}

	public void setDirectories(Collection<DirectoryInfo> directories) {
		this.directories = directories;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getTableName() {
		return "PARENT";
	}

	/**
	 * @{inheritDoc}
	 */
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((country == null) ? 0 : country.hashCode());
		result = PRIME * result + ((dir == null) ? 0 : dir.hashCode());
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
		if (!(obj instanceof CountryInfo)) {
			return false;
		}
		
		final CountryInfo other = (CountryInfo) obj;
		if (country == null) {
			if (other.country != null) {
				return false;
			}
		} else if (!country.equals(other.country)) {
			return false;
		}
		if (dir == null) {
			if (other.dir != null) {
				return false;
			}
		} else if (!dir.equals(other.dir)) {
			return false;
		}
		return true;
	}
}
