/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the "License").  You may not use this file except
 * in compliance with the License.
 * 
 * You can obtain a copy of the license at
 * https://jwsdp.dev.java.net/CDDLv1.0.html
 * See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL
 * HEADER in each file and include the License file at
 * https://jwsdp.dev.java.net/CDDLv1.0.html  If applicable,
 * add the following below this CDDL HEADER, with the
 * fields enclosed by brackets "[]" replaced with your
 * own identifying information: Portions Copyright [yyyy]
 * [name of copyright owner]
 */
package com.sun.tools.ws.processor.modeler.annotation;

import javax.xml.namespace.QName;

import com.sun.mirror.type.TypeMirror;

/**
 *
 * @author  WS Development Team
 */
public class MemberInfo implements Comparable<MemberInfo> {
    int paramIndex;
    TypeMirror paramType;
    String paramName;
    QName elementName;

    public MemberInfo(int paramIndex, TypeMirror paramType, String paramName,
        QName elementName) {
        this.paramIndex = paramIndex;
        this.paramType = paramType;
        this.paramName = paramName;
        this.elementName = elementName;
    }

    public int getParamIndex() {
        return paramIndex;
    }

    public TypeMirror getParamType() {
        return paramType;
    }

    public String getParamName() {
        return paramName;
    }

    public QName getElementName() {
        return elementName;
    }
    
    public int compareTo(MemberInfo member) {
        return paramName.compareTo(member.paramName);
    }
}
