
package de.othr.blackcastle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for stringIdEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="stringIdEntity">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.oth.swr.de/}singleIdEntity">
 *       &lt;sequence>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "stringIdEntity")
@XmlSeeAlso({
    Game.class,
    GameDeveloper.class,
    User.class,
    Email.class
})
public class StringIdEntity
    extends SingleIdEntity
{


}
