/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2006-2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package wsa.fromjava.custom_action.common;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;

import javax.xml.ws.WebServiceException;
import testutil.WsaBaseSOAPHandler;

/**
 * @author Arun Gupta
 */
public class ClientSOAPHandler extends WsaBaseSOAPHandler {
    @Override
    protected void checkInboundActions(String oper, String action) {
        if (oper.equals("addNumbersNoActionResponse")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_OUT_NOACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (oper.equals("addNumbersEmptyActionResponse")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_OUT_EMPTYACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (oper.equals("addNumbersResponse")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_OUT_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (oper.equals("addNumbers2Response")) {
            if (!action.equals(TestConstants.ADD_NUMBERS2_OUT_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (oper.equals("addNumbers3Response")) {
            if (!action.equals(TestConstants.ADD_NUMBERS3_OUT_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        }
    }

    @Override
    protected void checkFaultActions(String requestName, String detailName, String action) {
        if (requestName.equals("addNumbersFault1") && detailName.equals("AddNumbersException")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT1_ADDNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbersFault2") && detailName.equals("AddNumbersException")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT2_ADDNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbersFault2") && detailName.equals("TooBigNumbersException")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT2_TOOBIGNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbersFault3") && detailName.equals("AddNumbersException")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT3_ADDNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbersFault3") && detailName.equals("TooBigNumbersException")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT3_TOOBIGNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbersFault4") && detailName.equals("AddNumbersException")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT4_ADDNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbersFault4") && detailName.equals("TooBigNumbersException")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT4_TOOBIGNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbersFault5") && detailName.equals("AddNumbersException")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT5_ADDNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbersFault5") && detailName.equals("TooBigNumbersException")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT5_TOOBIGNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbersFault6") && detailName.equals("AddNumbersException")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT6_ADDNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbersFault6") && detailName.equals("TooBigNumbersException")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT6_TOOBIGNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbersFault7") && detailName.equals("AddNumbersException")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT7_ADDNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        } else if (requestName.equals("addNumbersFault7") && detailName.equals("TooBigNumbersException")) {
            if (!action.equals(TestConstants.ADD_NUMBERS_FAULT7_TOOBIGNUMBERS_ACTION)) {
                throw new WebServiceException("Unexpected action received" + action);
            }
        }
        super.checkFaultActions(requestName, detailName, action);
    }

    @Override
    protected String getOperationName(SOAPBody soapBody) throws SOAPException {
        String opName = super.getOperationName(soapBody);
        if (!opName.startsWith("addNumbersFault"))
            return opName;

        if (opName.equals("addNumbersFault1"))
            return opName;

        if (opName.equals("addNumbersFault2")) {
            soapBody.getFirstChild().getFirstChild().getNodeValue();
        }
        return opName;
    }
}