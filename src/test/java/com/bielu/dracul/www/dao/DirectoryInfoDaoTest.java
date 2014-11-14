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

/**
 * <code>ImageDaoTest</code> TODO provide description
 *
 * @author Przemyslaw Bielicki
 */
public class DirectoryInfoDaoTest extends AbstractTransactionalSpringContextTests {
	
	private IDirectoryInfoDao directoryInfoDao;
	
	protected String[] getConfigLocations() {
		return new String[] {"classpath:/applicationContext*.xml"};
	}
	
	public void setDirectoryInfo(IDirectoryInfoDao dao) {
		directoryInfoDao = dao;
	}
	
	public void testAll() {
		assertNotNull("DirectoryInfoDao should not be null.", directoryInfoDao);
		
		Collection<DirectoryInfo> col = directoryInfoDao.loadAll();
		assertNotNull("Empty database should not return null collections", col);
		//assertTrue("Database is not empty", col.size() == 0);
		
		DirectoryInfo newInfo = new DirectoryInfo();
		newInfo.setDir("c:/NOBACKUP");
		newInfo.setKmz("kmz");
		newInfo.setName("nobackup");
		directoryInfoDao.saveOrUpdate(newInfo);
		assertTrue("DirectoryInfo was not stored correctly into database.", newInfo.getId() > Identifiable.UNSAVED_ID);
		
		DirectoryInfo info = directoryInfoDao.findById(newInfo.getId());
		assertNotNull("Retrieved object should not be null.", info);
		assertEquals(newInfo, info);
		assertEquals("c:/NOBACKUP", info.getDir());
		assertEquals("kmz", info.getKmz());
		assertEquals("nobackup", info.getName());
		
		info = directoryInfoDao.findById(100);
		assertNull("DirectoryInfo with id = 100 should not exist in database.", info);
		
		col = directoryInfoDao.loadAll();
		assertTrue("List of directory infos should not be empty.", col.size() > 0);
		
		info = col.iterator().next();
		DirectoryInfo dbInfo = directoryInfoDao.findById(info.getId());
		assertEquals(info, dbInfo);
		assertEquals(info.hashCode(), dbInfo.hashCode());
		
		info.setKmz("kmz-new");
		directoryInfoDao.saveOrUpdate(info);
		dbInfo = directoryInfoDao.findById(info.getId());
		assertEquals(info, dbInfo);
		assertEquals(info.hashCode(), dbInfo.hashCode());

		info.setKmz("kmz");
		directoryInfoDao.saveOrUpdate(info);
		dbInfo = directoryInfoDao.findById(info.getId());
		assertEquals(info, dbInfo);
		assertEquals(info.hashCode(), dbInfo.hashCode());
		
		directoryInfoDao.delete(dbInfo);
		dbInfo = directoryInfoDao.findById(dbInfo.getId());
		assertNull("Deleted DirectoryInfo should not exist in database.", dbInfo);
	}

}
