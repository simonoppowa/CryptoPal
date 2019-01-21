
package de.othr.blackcastle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getGameTitel complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getGameTitel">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BestellId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getGameTitel", propOrder = {
    "bestellId"
})
public class GetGameTitel {

    @XmlElement(name = "BestellId")
    protected Long bestellId;

    /**
     * Gets the value of the bestellId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getBestellId() {
        return bestellId;
    }

    /**
     * Sets the value of the bestellId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setBestellId(Long value) {
        this.bestellId = value;
    }

}
