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

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.bielu.dracul.www.dao.IDirectoryInfoDao;
import com.bielu.dracul.www.domain.DirectoryInfo;
import com.opensymphony.xwork2.Action;

/**
 * <code>EditDirectoryInfoAction</code> TODO provide description
 *
 * @author Przemyslaw Bielicki
 */
public class EditDirectoryInfoAction extends AbstractKmzFileAction {

	private static final Logger LOG = Logger.getLogger(EditDirectoryInfoAction.class);
    
	private IDirectoryInfoDao directoryInfoDao;
    private DirectoryInfo directoryInfo;
    
	private long directoryId;

	/*
	 * Getters and setters
	 */
	public void setDirectoryInfoDao(IDirectoryInfoDao directoryInfoDao) {
		this.directoryInfoDao = directoryInfoDao;
	}
    
	public void setDirectoryId(long directoryId) {
		this.directoryId = directoryId;
	}
	
	public long getDirectoryId() {
		return directoryId;
	}
	
	public DirectoryInfo getDirectoryInfo() {
		return directoryInfo;
	}

	/*
	 * Action definitions
	 */
	public String edit() {
    	directoryInfo = directoryInfoDao.findById(directoryId);
    	if (directoryInfo == null) {
    		setGeneralError("Directrory Info with given ID [" + directoryId + "] does not exist in database");
    		return Action.ERROR;
    	}
    	    	
		return Action.SUCCESS;
    }
    
    public String save() {
    	DirectoryInfo info = directoryInfoDao.findById(directoryId);
    	if (info == null) {
    		return Action.ERROR;
    	}
    	
    	if (clearKmz()) {
    		info.setKmz(null);
    	} else {
	    	if (checkAndStoreKmzFile(info) == true) {
	    		info.setKmz(fileName);
	    	} else {
	    		return Action.SUCCESS;
	    	}
    	}
    	
    	try {
    		directoryInfoDao.saveOrUpdate(info);
    	} catch (DataAccessException e) {
    		LOG.error("Could not update directoryInfo with id [" + directoryId + "]", e);
    		return Action.ERROR;
    	}
    	
    	return Action.SUCCESS;
    }
}
