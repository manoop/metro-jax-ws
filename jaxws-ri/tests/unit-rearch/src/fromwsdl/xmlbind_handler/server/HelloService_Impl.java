/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2005-2017 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
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

package fromwsdl.xmlbind_handler.server;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.soap.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.http.HTTPException;
import javax.xml.ws.Provider;
import javax.xml.ws.Service;
import javax.xml.ws.Service.Mode;
import javax.xml.ws.WebServiceException;
                                                                                
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.rmi.RemoteException;
import java.util.Map;
                                                                                
import org.w3c.dom.Node;
import javax.xml.ws.WebServiceProvider;


/**
 */
@WebServiceProvider
public class HelloService_Impl implements Provider<Source> {
    private static final JAXBContext jaxbContext = createJAXBContext();

    public JAXBContext getJAXBContext(){
        return jaxbContext;
    }
                                                                                    
    private static javax.xml.bind.JAXBContext createJAXBContext(){
        try {
            return JAXBContext.newInstance(ObjectFactory.class);
        } catch(JAXBException e) {
            throw new WebServiceException(e.getMessage(), e);
        }
    }

    private Source sendSource(byte[] body) {
        String begin = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Body>";
        String end = "</soapenv:Body></soapenv:Envelope>";
                                                                                                                        
        String content = begin + new String(body) + end;
        return new StreamSource(new ByteArrayInputStream(content.getBytes()));
    }

    private int recvBean(Source source) throws Exception {
	SOAPMessage msg = MessageFactory.newInstance().createMessage();
	msg.getSOAPPart().setContent(source);
	Node node = msg.getSOAPBody().getFirstChild();
	DOMSource dom = new DOMSource(node);
        Hello hello = (Hello) jaxbContext.createUnmarshaller().unmarshal(dom);
        return hello.getIntin();
    }


    private byte[] sendBean(int x) throws Exception {
        HelloResponse resp = new HelloResponse();
        resp.setIntout(x);
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        Marshaller marshaller = jaxbContext.createMarshaller();
	marshaller.setProperty("jaxb.fragment", Boolean.TRUE);
        marshaller.marshal(resp, bout);

        bout.close();
        return bout.toByteArray();
    }
    
    public Source invoke(Source source) {
        try {
            int x = recvBean(source);
            byte[] body = sendBean(x);
            return sendSource(body);
        } catch(Exception e) {
            e.printStackTrace();
            throw new HTTPException(501);
        }

    }
    
}
