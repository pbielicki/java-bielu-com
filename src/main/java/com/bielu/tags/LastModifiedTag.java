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
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;

/**
 * <code>LastModifiedTag</code> TODO provide description
 * 
 * @author Przemyslaw Bielicki
 */
public class LastModifiedTag extends TagSupport implements Tag {

	private static final String INCLUDE_SERVLET_PATH = "javax.servlet.include.servlet_path";

	private String text;
	
	private String format = "yyyy/MM/dd HH:mm";
	
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @{inheritDoc}
	 */
	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			File file = new File(pageContext.getServletContext().getRealPath(request.getServletPath()));
			
			if (!file.exists() && request.getAttribute(INCLUDE_SERVLET_PATH) != null) {
				file = new File(pageContext.getServletContext().getRealPath((String) request.getAttribute(INCLUDE_SERVLET_PATH)));
			}
			out.write(text);
			out.write(new SimpleDateFormat(format).format(new Date(file.lastModified())));
		} catch (Exception e) {
			// ignore
		}
		
		return Tag.EVAL_PAGE;
	}
}