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

import java.util.Properties;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.log4j.Logger;

/**
 * <code>ConfigReaderTag</code> TODO provide description
 * 
 * @author Przemyslaw Bielicki
 */
public class ConfigReaderTag extends TagSupport implements Tag {

	private static final Logger LOG = Logger.getLogger(ConfigReaderTag.class);
	
	/**
	 * @{inheritDoc}
	 */
	public int doEndTag() throws JspException {
		try {
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			Properties props = new Properties();
			props.load(pageContext.getServletContext().getResourceAsStream(request.getServletPath() + ".properties"));
			LOG.info("Reading properties from [" + pageContext.getServletContext().getResource(request.getServletPath() + ".properties") + "] file.");
			
			for (Entry<Object, Object> entry : props.entrySet()) {
				String key = entry.getKey().toString();
				if (pageContext.getAttribute(key) != null) {
					LOG.warn("Overwriting previously set page context attribute [" + key + "] (old value was [" + pageContext.getAttribute(key) + "]).");
				}
				pageContext.setAttribute(key, entry.getValue());
				LOG.debug("Set [" + entry.getKey() + "] page context attribute to [" + entry.getValue() + "] value.");
			}
			
		} catch (Exception e) {
			LOG.error("Could not read properties - igoring.", e);
		}
		
		return Tag.EVAL_PAGE;
	}
}