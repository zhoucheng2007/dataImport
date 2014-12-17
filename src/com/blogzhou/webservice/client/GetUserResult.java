
package com.blogzhou.webservice.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetUserResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetUserResult">
 *   &lt;complexContent>
 *     &lt;extension base="{http://webservice.jfit.com.cn}WSResult">
 *       &lt;sequence>
 *         &lt;element name="user" type="{http://webservice.jfit.com.cn}sessionInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetUserResult", propOrder = {
    "user"
})
public class GetUserResult
    extends WSResult
{

    protected SessionInfo user;

    /**
     * Gets the value of the user property.
     * 
     * @return
     *     possible object is
     *     {@link SessionInfo }
     *     
     */
    public SessionInfo getUser() {
        return user;
    }

    /**
     * Sets the value of the user property.
     * 
     * @param value
     *     allowed object is
     *     {@link SessionInfo }
     *     
     */
    public void setUser(SessionInfo value) {
        this.user = value;
    }

}
