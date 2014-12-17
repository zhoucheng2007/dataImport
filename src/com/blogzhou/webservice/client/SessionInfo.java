
package com.blogzhou.webservice.client;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for sessionInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sessionInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ip" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="loginName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="loginOrganId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="loginOrganName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="loginOrganSysCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="loginTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="organNames" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="postIds" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="postNames" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="roleIds" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="roleNames" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="userType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sessionInfo", propOrder = {
    "id",
    "ip",
    "loginName",
    "loginOrganId",
    "loginOrganName",
    "loginOrganSysCode",
    "loginTime",
    "name",
    "organNames",
    "postIds",
    "postNames",
    "roleIds",
    "roleNames",
    "userId",
    "userType"
})
public class SessionInfo {

    protected String id;
    protected String ip;
    protected String loginName;
    protected Long loginOrganId;
    protected String loginOrganName;
    protected String loginOrganSysCode;
    protected XMLGregorianCalendar loginTime;
    protected String name;
    protected String organNames;
    @XmlElement(nillable = true)
    protected List<Long> postIds;
    protected String postNames;
    @XmlElement(nillable = true)
    protected List<Long> roleIds;
    protected String roleNames;
    protected Long userId;
    protected String userType;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the ip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIp() {
        return ip;
    }

    /**
     * Sets the value of the ip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIp(String value) {
        this.ip = value;
    }

    /**
     * Gets the value of the loginName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * Sets the value of the loginName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoginName(String value) {
        this.loginName = value;
    }

    /**
     * Gets the value of the loginOrganId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getLoginOrganId() {
        return loginOrganId;
    }

    /**
     * Sets the value of the loginOrganId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setLoginOrganId(Long value) {
        this.loginOrganId = value;
    }

    /**
     * Gets the value of the loginOrganName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoginOrganName() {
        return loginOrganName;
    }

    /**
     * Sets the value of the loginOrganName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoginOrganName(String value) {
        this.loginOrganName = value;
    }

    /**
     * Gets the value of the loginOrganSysCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoginOrganSysCode() {
        return loginOrganSysCode;
    }

    /**
     * Sets the value of the loginOrganSysCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoginOrganSysCode(String value) {
        this.loginOrganSysCode = value;
    }

    /**
     * Gets the value of the loginTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLoginTime() {
        return loginTime;
    }

    /**
     * Sets the value of the loginTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLoginTime(XMLGregorianCalendar value) {
        this.loginTime = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the organNames property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganNames() {
        return organNames;
    }

    /**
     * Sets the value of the organNames property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganNames(String value) {
        this.organNames = value;
    }

    /**
     * Gets the value of the postIds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the postIds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPostIds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     */
    public List<Long> getPostIds() {
        if (postIds == null) {
            postIds = new ArrayList<Long>();
        }
        return this.postIds;
    }

    /**
     * Gets the value of the postNames property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostNames() {
        return postNames;
    }

    /**
     * Sets the value of the postNames property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostNames(String value) {
        this.postNames = value;
    }

    /**
     * Gets the value of the roleIds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the roleIds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRoleIds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     */
    public List<Long> getRoleIds() {
        if (roleIds == null) {
            roleIds = new ArrayList<Long>();
        }
        return this.roleIds;
    }

    /**
     * Gets the value of the roleNames property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoleNames() {
        return roleNames;
    }

    /**
     * Sets the value of the roleNames property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoleNames(String value) {
        this.roleNames = value;
    }

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setUserId(Long value) {
        this.userId = value;
    }

    /**
     * Gets the value of the userType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserType() {
        return userType;
    }

    /**
     * Sets the value of the userType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserType(String value) {
        this.userType = value;
    }

}
