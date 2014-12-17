
package com.blogzhou.webservice.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.1 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "UserService", targetNamespace = "http://webservice.jfit.com.cn")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface UserService {


    /**
     * 
     * @param name
     * @return
     *     returns com.blogzhou.webservice.client.GetUserResult
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getUser", targetNamespace = "http://webservice.jfit.com.cn", className = "com.blogzhou.webservice.client.GetUser")
    @ResponseWrapper(localName = "getUserResponse", targetNamespace = "http://webservice.jfit.com.cn", className = "com.blogzhou.webservice.client.GetUserResponse")
    public GetUserResult getUser(
        @WebParam(name = "name", targetNamespace = "")
        String name);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getMenus", targetNamespace = "http://webservice.jfit.com.cn", className = "com.blogzhou.webservice.client.GetMenus")
    @ResponseWrapper(localName = "getMenusResponse", targetNamespace = "http://webservice.jfit.com.cn", className = "com.blogzhou.webservice.client.GetMenusResponse")
    public String getMenus(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

}
