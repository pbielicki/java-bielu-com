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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.bielu.dracul.www.domain.DirectoryInfo;

/**
 * <code>AbstractKmzFileAction</code> TODO provide description
 *
 * @author Przemyslaw Bielicki
 */
public class AbstractKmzFileAction extends AbstractGeneralAction {
	
	protected static final String PUBLIC_HTML = System.getProperty("user.home") + File.separator + "public_html";
	
	private static final Logger LOG = Logger.getLogger(AbstractKmzFileAction.class);

	protected String fileName;
	private String contentType;
    protected File kmzFile;
    private boolean clearKmz = false;
    
	public File getKmz() {
		return kmzFile;
	}

	public void setKmz(File kmz) {
		this.kmzFile = kmz;
	}
	
    public String getKmzFileName() {
        return fileName;
    }
    public void setKmzFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getKmzContentType() {
        return contentType;
    }
    public void setKmzContentType(String contentType) {
        this.contentType = contentType;
    }
    
	public boolean clearKmz() {
		return clearKmz;
	}

	public void setClearKmz(String clearKmz) {
		this.clearKmz = "ON".equals(clearKmz.toUpperCase().trim());
	}

	protected boolean checkAndStoreKmzFile(DirectoryInfo info) {
		if (fileName != null && fileName.trim().length() > 0) {
			String kmzLocation = PUBLIC_HTML + File.separator;
			try {
				if (info.getCountryInfo() == null) {
					kmzLocation += "sycylia" + File.separator + "zdjecia" + File.separator
							+ info.getDir();
				} else {
					kmzLocation += "azja" + File.separator + info.getCountryInfo().getDir()
							+ File.separator + info.getDir();
				}
				kmzLocation += fileName;
				
				FileInputStream in = new FileInputStream(kmzFile);
				FileOutputStream out = new FileOutputStream(kmzLocation);
				int c;
				while ((c = in.read()) != -1) {
					out.write(c);
				}
				out.close();
				in.close();
				
				try {
					new ProcessBuilder("chmod", "604", kmzLocation).start();
				} catch (IOException e) {
					LOG.warn("Could not change access [604] of the destination file [" + kmzLocation + "].", e);					
				}
				return true;
			} catch (IOException e) {
				LOG.error("Could not copy KMZ file to the destination file [" + kmzLocation + "].", e);
			}
		}
		return false;
	}
}
