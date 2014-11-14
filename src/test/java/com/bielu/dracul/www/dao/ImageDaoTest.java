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

import java.util.Collection;

import org.springframework.test.AbstractTransactionalSpringContextTests;

import com.bielu.dracul.domain.Identifiable;
import com.bielu.dracul.www.domain.DirectoryInfo;
import com.bielu.dracul.www.domain.Image;

/**
 * <code>ImageDaoTest</code> TODO provide description
 *
 * @author Przemyslaw Bielicki
 */
public class ImageDaoTest extends AbstractTransactionalSpringContextTests {
	
	private IImageDao imageDao;
	
	protected String[] getConfigLocations() {
		return new String[] {"classpath:/applicationContext*.xml"};
	}
	
	public void setImageDao(IImageDao dao) {
		this.imageDao = dao;
	}

	public void testBasicPersistentMethods() {
		assertNotNull("ImageDao should not be null.", imageDao);
		
		Collection<Image> col = imageDao.loadAll();
		assertNotNull("Empty database should not return null collections", col);
		assertTrue("Database is not empty", col.size() == 0);
		
		DirectoryInfo info = new DirectoryInfo();
		info.setDir("dir");
		Image newImage = new Image();
		newImage.setDirectoryInfo(info);
		newImage.setCount(100);
		newImage.setKmz("kmz");
		newImage.setPath("path");
		newImage.setShortDescription("short descr");
		newImage.setThumbnail("thumbnail");
		imageDao.saveOrUpdate(newImage);
		assertTrue("Image was not stored correctly into database", newImage.getId() > Identifiable.UNSAVED_ID);
		
		Image image = imageDao.findById(newImage.getId());
		assertEquals(newImage, image);
		assertEquals(100, image.getCount());
		assertEquals("kmz", image.getKmz());
		assertEquals("path", image.getPath());
		assertEquals("short descr", image.getShortDescription());
		assertEquals("thumbnail", image.getThumbnail());
		
		image = imageDao.findById(100);
		assertNull("Image with id = 100 should not exist in database", image);
		
		col = imageDao.loadAll();
		assertNotNull("Collection of images should not be null", col);
		assertTrue("List of images should not be empty.", col.size() > 0);
		
		image = col.iterator().next();
		Image dbImage = imageDao.findById(image.getId());
		assertEquals(image, dbImage);
		assertEquals(image.hashCode(), dbImage.hashCode());
		
		image.setCount(image.getCount() + 1);
		imageDao.saveOrUpdate(image);
		dbImage = imageDao.findById(image.getId());
		assertEquals(image, dbImage);
		assertEquals(image.hashCode(), dbImage.hashCode());

		image.setCount(image.getCount() - 1);
		imageDao.saveOrUpdate(image);
		dbImage = imageDao.findById(image.getId());
		assertEquals(image, dbImage);
		assertEquals(image.hashCode(), dbImage.hashCode());
		
		imageDao.delete(dbImage);
		dbImage = imageDao.findById(dbImage.getId());
		assertNull("Deleted Image should not exist in database.", dbImage);
	}
}
