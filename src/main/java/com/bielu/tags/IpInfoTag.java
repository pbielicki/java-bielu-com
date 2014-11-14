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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import com.bielu.geoIp.GeoIpUpdater;
import com.bielu.geoIp.IpInfo;

/**
 * <code>IpInfoTag</code> TODO provide description
 * 
 * @author Przemyslaw Bielicki
 */
public class IpInfoTag extends TagSupport implements Tag {

	private static final long serialVersionUID = -3762683110779757493L;

	public static final String PRIVATE_ADDRESS = "(Private Address)";

	public static final String IP = "ip";

	/**
	 * @{inheritDoc}
	 */
	@Override
	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		String ip = request.getRemoteAddr();
		if (request.getParameter(IP) != null) {
			ip = request.getParameter(IP).trim();
			if (!ip.matches("\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b")) {
				try {
					InetAddress addr = InetAddress.getByName(ip);
					String hostname = ip;
					ip = addr.getHostAddress();
					pageContext.setAttribute("hostname", hostname);
				} catch (UnknownHostException e) {
					pageContext.setAttribute("errorMessage", ip + " host does not exist or cannot be resolved.");
					StringWriter sw = new StringWriter();
					PrintWriter out = new PrintWriter(sw);
					e.printStackTrace(out);
					pageContext.setAttribute("exceptionText", sw.toString());
					pageContext.setAttribute("showMap", false);
					return Tag.EVAL_PAGE;
				}
			}
		}
		String proxyUrl = pageContext.getAttribute("proxy.url") == null ? null : pageContext.getAttribute("proxy.url").toString();
		String proxyPort = pageContext.getAttribute("proxy.port") == null ? null : pageContext.getAttribute("proxy.port").toString();
		
		IpInfo info = GeoIpUpdater.getIpInfo(ip, proxyUrl, proxyPort);
		
		pageContext.setAttribute("country", info.getCountry());
		pageContext.setAttribute("city", info.getCity());
		pageContext.setAttribute("latitude", info.getLatitude());
		pageContext.setAttribute("longitude", info.getLongitude());
		pageContext.setAttribute(IP, ip);
		
		boolean showMap = info.getLatitude().length() > 0 && info.getLongitude().length() > 0;
		pageContext.setAttribute("showMap", showMap);
		
		return Tag.EVAL_PAGE;
	}
}