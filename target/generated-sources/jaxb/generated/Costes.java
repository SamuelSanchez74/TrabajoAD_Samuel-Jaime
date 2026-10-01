//
// Este archivo ha sido generado por Eclipse Implementation of JAXB v3.0.0 
// Visite https://eclipse-ee4j.github.io/jaxb-ri 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2026.09.25 a las 06:54:47 PM CEST 
//


package generated;

import java.math.BigDecimal;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="costesEnvio" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="costesAlmacenaje" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "costesEnvio",
    "costesAlmacenaje"
})
@XmlRootElement(name = "costes")
public class Costes {

    @XmlElement(required = true)
    protected BigDecimal costesEnvio;
    @XmlElement(required = true)
    protected BigDecimal costesAlmacenaje;

    /**
     * Obtiene el valor de la propiedad costesEnvio.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCostesEnvio() {
        return costesEnvio;
    }

    /**
     * Define el valor de la propiedad costesEnvio.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCostesEnvio(BigDecimal value) {
        this.costesEnvio = value;
    }

    /**
     * Obtiene el valor de la propiedad costesAlmacenaje.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCostesAlmacenaje() {
        return costesAlmacenaje;
    }

    /**
     * Define el valor de la propiedad costesAlmacenaje.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCostesAlmacenaje(BigDecimal value) {
        this.costesAlmacenaje = value;
    }

}
