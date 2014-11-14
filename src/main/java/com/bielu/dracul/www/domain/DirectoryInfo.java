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

import java.util.HashSet;
import java.util.Set;

import com.bielu.dracul.domain.Identifiable;
import com.bielu.dracul.domain.Persistent;

/**
 * @author pbielick
 *
 */
public class DirectoryInfo extends Persistent implements Identifiable {

	private CountryInfo countryInfo;
	
	private String dir;
	
	private String name;
	
	private String kmz;
	
	private double count;
	
	private Set<Image> images = new HashSet<Image>();
	
	public CountryInfo getCountryInfo() {
		return countryInfo;
	}

	public void setCountryInfo(CountryInfo countryInfo) {
		this.countryInfo = countryInfo;
	}

	public Set<Image> getImages() {
		return images;
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}

	public double getCount() {
		return count;
	}

	public void setCount(double count) {
		this.count = count;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getKmz() {
		return kmz;
	}

	public void setKmz(String kmz) {
		this.kmz = kmz;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getTableName() {
		return "SUB";
	}

	/**
	 * @{inheritDoc}
	 */
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((countryInfo == null) ? 0 : countryInfo.hashCode());
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
		if (!(obj instanceof DirectoryInfo)) {
			return false;
		}
		
		final DirectoryInfo other = (DirectoryInfo) obj;
		if (countryInfo == null) {
			if (other.countryInfo != null) {
				return false;
			}
		} else if (!countryInfo.equals(other.countryInfo)) {
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
