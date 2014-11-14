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
public class Image extends Persistent implements Identifiable, Countable {

	private String path;

	private String thumbnail;

	private String description;

	private String shortDescription;

	private String kmz;

	private long count;

	private DirectoryInfo directoryInfo;

	public String getKmz() {
		return kmz;
	}

	public void setKmz(String kmz) {
		this.kmz = kmz;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public DirectoryInfo getDirectoryInfo() {
		return directoryInfo;
	}

	public void setDirectoryInfo(DirectoryInfo directoryInfo) {
		this.directoryInfo = directoryInfo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		return "IMG";
	}

	/**
	 * @{inheritDoc}
	 */
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((path == null) ? 0 : path.hashCode());
		result = PRIME * result + ((directoryInfo == null) ? 0 : directoryInfo.hashCode());
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

		if (!(obj instanceof Image)) {
			return false;
		}

		final Image other = (Image) obj;
		if (path == null) {
			if (other.path != null) {
				return false;
			}
		} else if (!path.equals(other.path)) {
			return false;
		}

		if (directoryInfo == null) {
			if (other.directoryInfo != null) {
				return false;
			}
		} else if (!directoryInfo.equals(other.directoryInfo)) {
			return false;
		}

		return true;
	}
}
