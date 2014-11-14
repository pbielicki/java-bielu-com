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

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.bielu.dracul.www.dao.IImageDao;
import com.bielu.dracul.www.domain.Image;
import com.opensymphony.xwork2.Action;

/**
 * <code>EditImageAction</code> TODO provide description
 *
 * @author Przemyslaw Bielicki
 */
public class EditImageAction extends AbstractKmzFileAction {

	private static final Logger LOG = Logger.getLogger(EditImageAction.class);
    
	private IImageDao imageDao;
    private Image image;
    
    private String shortDescription;
    private String description;
	private long imageId;
	private Map<String, String> errors = new HashMap<String, String>();

	/*
	 * Getters and setters
	 */
    public void setImageDao(IImageDao dao) {
    	this.imageDao = dao;
    }
    
    public void setShortDescription(String string) {
    	this.shortDescription = string;
    }
    
    public void setDescription(String string) {
    	this.description = string;
    }
    
    public void setImageId(long id) {
    	this.imageId = id;
    }

    public long getImageId() {
    	return imageId;
    }
	
	public Map<String, String> getErrors() {
		return errors;
	}
	
	public void setErrors(Map<String, String> map) {
		this.errors = map;
	}

    public Image getImage() {    	
    	return image;
    }

	/*
	 * Action definitions
	 */
	public String edit() {
    	image = imageDao.findById(imageId);
    	if (image == null) {
    		setGeneralError("Image with given ID [" + imageId + "] does not exist in database");
    		return Action.ERROR;
    	}
    	    	
		return Action.SUCCESS;
    }
    
    public String save() {
    	if (shortDescription == null || shortDescription.trim().length() == 0) {
    		errors.put("shortDescription", "Short Description could not be empty.");
    		return Action.INPUT;
    	}
    	
    	Image image = imageDao.findById(imageId);
    	if (image == null) {
    		return Action.ERROR;
    	}
    	
    	image.setShortDescription(shortDescription);
    	image.setDescription(description);

    	if (clearKmz()) {
    		image.setKmz(null);
    	} else {
	    	if (checkAndStoreKmzFile(image.getDirectoryInfo()) == true) {
	    		image.setKmz(fileName); 
	    	}
    	}
    	
    	try {
    		imageDao.saveOrUpdate(image);
    	} catch (DataAccessException e) {
    		LOG.error("Could not update image with id [" + imageId + "]", e);
    		return Action.ERROR;
    	}
    	
    	return Action.SUCCESS;
    }
}
