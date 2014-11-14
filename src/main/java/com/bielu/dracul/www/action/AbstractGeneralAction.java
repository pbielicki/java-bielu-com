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
package com.bielu.dracul.www.action;

/**
 * <code>AbstractGeneralAction</code> TODO provide description
 *
 * @author Przemyslaw Bielicki
 */
public class AbstractGeneralAction {

	private int page = 1;
    protected int pageCount = -1;
	private int recordsPerPage = 10;
	private boolean thumbs;
	private Long country;
	private Long dir;
	private String region;
	
	private String generalError;

	public Long getCountry() {
		return country;
	}

	public void setCountry(Long country) {
		this.country = country;
	}

	public Long getDir() {
		return dir;
	}

	public void setDir(Long dir) {
		this.dir = dir;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		if (page < 1) {
			this.page = 1;
		} else {
			this.page = page;
		}
	}

	public int getRecordsPerPage() {
		return recordsPerPage;
	}

	public void setRecordsPerPage(int recordsPerPage) {
		if (recordsPerPage < 1) {
			recordsPerPage = 20;
		} else {
			this.recordsPerPage = recordsPerPage;
		}
	}

	public boolean isThumbs() {
		return thumbs;
	}

	public void setThumbs(boolean thumbs) {
		this.thumbs = thumbs;
	}
	
	public String getGeneralError() {
		return generalError;
	}

	public void setGeneralError(String error) {
		this.generalError = error;
	}

	public int getPageCount() {
		return pageCount;
	}
}