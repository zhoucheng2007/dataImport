
package com.blogzhou.webservice.client;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.1 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "UserService", targetNamespace = "http://webservice.jfit.com.cn", wsdlLocation = "http://10.36.98.239:8088/ws/UserServices?wsdl")
public class UserService_Service
    extends Service
{

    private final static URL USERSERVICE_WSDL_LOCATION;

    static {
        URL url = null;
        try {
            url = new URL("http://10.36.98.239:8088/ws/UserServices?wsdl");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        USERSERVICE_WSDL_LOCATION = url;
    }

    public UserService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public UserService_Service() {
        super(USERSERVICE_WSDL_LOCATION, new QName("http://webservice.jfit.com.cn", "UserService"));
    }

    /**
     * 
     * @return
     *     returns UserService
     */
    @WebEndpoint(name = "UserServicePort")
    public UserService getUserServicePort() {
        return (UserService)super.getPort(new QName("http://webservice.jfit.com.cn", "UserServicePort"), UserService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns UserService
     */
    @WebEndpoint(name = "UserServicePort")
    public UserService getUserServicePort(WebServiceFeature... features) {
        return (UserService)super.getPort(new QName("http://webservice.jfit.com.cn", "UserServicePort"), UserService.class, features);
    }

}