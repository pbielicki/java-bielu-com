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
package com.bielu.tags;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.Proxy.Type;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.log4j.Logger;

import com.bielu.jsp.util.JspUtil;

/**
 * <code>KmzReaderTag</code> TODO provide description
 * 
 * @author Przemyslaw Bielicki
 */
public class KmzReaderTag extends TagSupport implements Tag {

	private static final Logger LOG = Logger.getLogger(KmzReaderTag.class);
	
	private String location;
	
	private int z = 14;
	
	private boolean proxy = false;
	
	public void setLocation(String location) {
		this.location = location;
	}

	public void setProxy(boolean proxy) {
		this.proxy = proxy;
	}
	
	public void setZ(int z) {
		if (z > 0) {
			this.z = z;
		}
	}
	
	/**
	 * @{inheritDoc}
	 */
	public int doEndTag() throws JspException {
		try {
			if (location.endsWith(".kmz") == false) {
				throw new Exception("Accepts only KMZ format files.");
			}
			URL url = new URL(JspUtil.encodeUrl(location));
			
			InputStream in;
			if (proxy == true) {
				Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress("proxy.isw.intel.com", 911));
				in = url.openConnection(proxy).getInputStream();
			} else {
				in = url.openConnection().getInputStream();				
			}
			
			File ctxTempdir = (File) pageContext.getServletContext().getAttribute("javax.servlet.context.tempdir");
			File tmpFile = getTempFile(ctxTempdir.getAbsolutePath(), ".kmz");
			OutputStream out = new FileOutputStream(tmpFile);
			int c;
			int count = 0;
			while ((c = in.read()) != -1) {
				out.write(c);
				count++;
				if (count > 5000) {
					throw new Exception("Size of KMZ file must not exceed 5000 bytes");
				}
			}
			out.close();
			in.close();
			
			ZipFile file = new ZipFile(tmpFile);
			for (Enumeration<? extends ZipEntry> entries = file.entries(); entries.hasMoreElements();) {
				ZipEntry entry = entries.nextElement();
				if ("doc.kml".equals(entry.getName())) {
					LineNumberReader reader = new LineNumberReader(new InputStreamReader(file.getInputStream(entry)));
					String longitude = "";
					String latitude = "";
					String coordinates = null;
					String line = null;
					while ((line = reader.readLine()) != null) {
						int i = line.indexOf("<longitude>");
						if (i > -1) {
							int end = line.indexOf("</longitude>");
							longitude = line.substring(i + "<longitude>".length(), end);
							continue;
						}

						i = line.indexOf("<latitude>");
						if (i > -1) {
							int end = line.indexOf("</latitude>");
							latitude = line.substring(i + "<latitude>".length(), end);
							continue;
						}

						i = line.indexOf("<coordinates>");
						if (i > -1) {
							int end = line.indexOf("</coordinates>");
							coordinates = line.substring(i + "<coordinates>".length(), end);
							continue;
						}
					}
					reader.close();
					
					if ((latitude.length() == 0 || longitude.length() == 0) 
							&& coordinates != null
							&& coordinates.split(",").length == 3) {
						
						longitude = coordinates.split(",")[0];
						latitude = coordinates.split(",")[1];						
					}
					String redirect = "http://maps.google.com/?ll=" + latitude + "," + longitude + "&z=" + z;
					((HttpServletResponse) pageContext.getResponse()).sendRedirect(redirect);
				}
			}
			file.close();
			tmpFile.delete();
		} catch (Exception e) {
			LOG.info("Could not read KMZ file [" + location + "] - igoring.", e);
			String redirect = "http://maps.google.com/";
			try {
				((HttpServletResponse) pageContext.getResponse()).sendRedirect(redirect);
			} catch (IOException e1) {
				LOG.error(e1);
			}
		}
		
		return Tag.EVAL_PAGE;
	}
	
	private File getTempFile(String root, String postfix) throws IOException {
		if (!root.endsWith("/")) {
			root += "/";
		}
		int i = 0;
		File temp = null;
		while (true) {
			temp = new File(root + i + postfix);
			if (temp.exists()) {
				++i;
			} else {
				LOG.info("Temporary file [" + temp.getAbsolutePath() +"] will be created");
				break;
			}
		}

		return temp;
	}
}