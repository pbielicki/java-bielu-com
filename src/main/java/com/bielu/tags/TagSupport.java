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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/**
 * <code>TagSupport</code> TODO provide description
 *
 * @author Przemyslaw Bielicki
 */
public class TagSupport {

	protected PageContext pageContext = null;
	private Tag parent = null;

	/**
	 * @{inheritDoc}
	 */
	public int doStartTag() throws JspException {
		return Tag.SKIP_BODY;
	}

	/**
	 * @{inheritDoc}
	 */
	public void setPageContext(PageContext context) {
		this.pageContext = context;
	}

	/**
	 * @{inheritDoc}
	 */
	public void setParent(Tag parent) {
		this.parent = parent;
	}

	/**
	 * @{inheritDoc}
	 */
	public Tag getParent() {
		return parent;
	}

	/**
	 * @{inheritDoc}
	 */
	public void release() {
	}

}