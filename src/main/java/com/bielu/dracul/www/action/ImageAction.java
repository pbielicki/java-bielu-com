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

import java.util.List;

import com.bielu.dracul.www.dao.ICountryInfoDao;
import com.bielu.dracul.www.dao.IDirectoryInfoDao;
import com.bielu.dracul.www.dao.IImageDao;
import com.bielu.dracul.www.domain.CountryInfo;
import com.bielu.dracul.www.domain.DirectoryInfo;
import com.bielu.dracul.www.domain.Image;
import com.opensymphony.xwork2.Action;

/**
 * <code>ImageAction</code> TODO provide description
 *
 * @author Przemyslaw Bielicki
 */
public class ImageAction extends AbstractGeneralAction {

	private IImageDao imageDao;
	private IDirectoryInfoDao directoryInfoDao;
	private ICountryInfoDao countryInfoDao;
    private List<Image> images;
    private List<CountryInfo> asiaDirs;
    private List<DirectoryInfo> sicilyDirs;
    
    private String windowTitle;

	/*
	 * Getters and setters
	 */
    public void setImageDao(IImageDao dao) {
    	this.imageDao = dao;
    }
    
    public void setCountryInfoDao(ICountryInfoDao dao) {
    	this.countryInfoDao = dao;
    }
    
    public void setDirectoryInfoDao(IDirectoryInfoDao dao) {
    	this.directoryInfoDao = dao;
    }
	
	public String getWindowTitle() {
		return windowTitle;
	}

	public List<CountryInfo> getAsiaDirs() {
		return asiaDirs;
	}

	public List<DirectoryInfo> getSicilyDirs() {
		return sicilyDirs;
	}
	
    public List<Image> getImages() {
    	if (getPage() > pageCount) {
    		setPage(1);
    	}
    	
    	if (getRecordsPerPage() > images.size()) {
    		setRecordsPerPage(images.size());
    	}
    	
    	int start = (getPage() - 1) * getRecordsPerPage();
    	int end = start + getRecordsPerPage();
    	if (end >= images.size()) {
    		end = images.size();
    	}
    	
    	return images.subList(start, end);
    }
    
    private boolean empty(Object obj) {
    	return obj == null || obj.toString().length() == 0;
    }
    
	/*
	 * Action definitions
	 */
    public String list() {
    	this.asiaDirs = countryInfoDao.loadAll();
    	this.sicilyDirs = directoryInfoDao.loadByRegion("sicily");
    	
    	if (empty(getRegion()) == false) {
    		this.images = imageDao.loadByRegion(getRegion());
    	} else if (empty(getCountry()) == false) {
    		this.images = imageDao.loadByCountry(getCountry());
    	} else if (empty(getDir()) == false) {
    		this.images = imageDao.loadByDirectory(getDir());
    		if (images.isEmpty() == false) {
    			Image img = images.iterator().next();
    			if (img.getDirectoryInfo().getCountryInfo() != null) {
    				windowTitle = img.getDirectoryInfo().getCountryInfo().getCountry() + " - "
							+ img.getDirectoryInfo().getName();
    			} else {
    				windowTitle = "Sycylia - " + img.getDirectoryInfo().getName();    				
    			}
    		}
    		
    	} else {
            this.images = imageDao.loadAll();    		
    	}
    	
        pageCount = ((images.size() - 1) / getRecordsPerPage()) + 1;
    	if (getPage() > pageCount) {
    		setPage(1);
    	}
        return Action.SUCCESS;
    }
}
