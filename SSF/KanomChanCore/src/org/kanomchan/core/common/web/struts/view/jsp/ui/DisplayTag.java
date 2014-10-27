/*
 * $Id$
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.kanomchan.core.common.web.struts.view.jsp.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.kanomchan.core.common.web.struts.components.Display;
//import org.apache.struts2.components.Form;
import org.kanomchan.core.common.web.struts.components.FormAjax;
import org.apache.struts2.views.jsp.ui.AbstractClosingTag;

import com.opensymphony.xwork2.util.ValueStack;


/**
 * @see Form
 */
public class DisplayTag extends AbstractClosingTag {

    private static final long serialVersionUID = 2792301046860819658L;

    protected String action;
    protected String target;
    protected String enctype;
    protected String method;
    protected String namespace;
    protected String validate;
    protected String onsubmit;
    protected String onreset;
    protected String portletMode;
    protected String windowState;
    protected String acceptcharset;
    protected String focusElement;
    protected boolean includeContext = true;
    
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Display(stack, req, res);
    }

    protected void populateParams() {
        super.populateParams();
        Display display = ((Display) component);
        display.setAction(action);
        display.setTarget(target);
        display.setEnctype(enctype);
        display.setMethod(method);
        display.setNamespace(namespace);
        display.setValidate(validate);
        display.setOnreset(onreset);
        display.setOnsubmit(onsubmit);
        display.setPortletMode(portletMode);
        display.setWindowState(windowState);
        display.setAcceptcharset(acceptcharset);
        display.setFocusElement(focusElement);
        display.setIncludeContext(includeContext);
    }


    public void setAction(String action) {
        this.action = action;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setEnctype(String enctype) {
        this.enctype = enctype;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }

    public void setOnsubmit(String onsubmit) {
        this.onsubmit = onsubmit;
    }

    public void setOnreset(String onreset) {
        this.onreset = onreset;
    }

    public void setPortletMode(String portletMode) {
        this.portletMode = portletMode;
    }

    public void setWindowState(String windowState) {
        this.windowState = windowState;
    }

    public void setAcceptcharset(String acceptcharset) {
        this.acceptcharset = acceptcharset;
    }

    public void setFocusElement(String focusElement) {
        this.focusElement = focusElement;
    }

    public void setIncludeContext(boolean includeContext) {
        this.includeContext = includeContext;
    }
}
