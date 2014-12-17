
package com.blogzhou.webservice.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.blogzhou.webservice.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetUser_QNAME = new QName("http://webservice.jfit.com.cn", "getUser");
    private final static QName _GetMenus_QNAME = new QName("http://webservice.jfit.com.cn", "getMenus");
    private final static QName _GetMenusResponse_QNAME = new QName("http://webservice.jfit.com.cn", "getMenusResponse");
    private final static QName _GetUserResponse_QNAME = new QName("http://webservice.jfit.com.cn", "getUserResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.blogzhou.webservice.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link WSResult }
     * 
     */
    public WSResult createWSResult() {
        return new WSResult();
    }

    /**
     * Create an instance of {@link GetUserResult }
     * 
     */
    public GetUserResult createGetUserResult() {
        return new GetUserResult();
    }

    /**
     * Create an instance of {@link SessionInfo }
     * 
     */
    public SessionInfo createSessionInfo() {
        return new SessionInfo();
    }

    /**
     * Create an instance of {@link GetMenusResponse }
     * 
     */
    public GetMenusResponse createGetMenusResponse() {
        return new GetMenusResponse();
    }

    /**
     * Create an instance of {@link GetUser }
     * 
     */
    public GetUser createGetUser() {
        return new GetUser();
    }

    /**
     * Create an instance of {@link GetUserResponse }
     * 
     */
    public GetUserResponse createGetUserResponse() {
        return new GetUserResponse();
    }

    /**
     * Create an instance of {@link GetMenus }
     * 
     */
    public GetMenus createGetMenus() {
        return new GetMenus();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jfit.com.cn", name = "getUser")
    public JAXBElement<GetUser> createGetUser(GetUser value) {
        return new JAXBElement<GetUser>(_GetUser_QNAME, GetUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMenus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jfit.com.cn", name = "getMenus")
    public JAXBElement<GetMenus> createGetMenus(GetMenus value) {
        return new JAXBElement<GetMenus>(_GetMenus_QNAME, GetMenus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMenusResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jfit.com.cn", name = "getMenusResponse")
    public JAXBElement<GetMenusResponse> createGetMenusResponse(GetMenusResponse value) {
        return new JAXBElement<GetMenusResponse>(_GetMenusResponse_QNAME, GetMenusResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.jfit.com.cn", name = "getUserResponse")
    public JAXBElement<GetUserResponse> createGetUserResponse(GetUserResponse value) {
        return new JAXBElement<GetUserResponse>(_GetUserResponse_QNAME, GetUserResponse.class, null, value);
    }

}
