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
import jakarta.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element name="marca" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="modelo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="categoria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="anioLanzamiento" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="garantiaMeses" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element ref="{}proveedor"/&gt;
 *         &lt;element name="tipoConexion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="precio" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="descuento" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element ref="{}costes"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="codigo" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="numeroSerie" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "marca",
    "modelo",
    "categoria",
    "anioLanzamiento",
    "garantiaMeses",
    "proveedor",
    "tipoConexion",
    "precio",
    "descuento",
    "costes"
})
@XmlRootElement(name = "producto")
public class Producto {

    @XmlElement(required = true)
    protected String marca;
    @XmlElement(required = true)
    protected String modelo;
    @XmlElement(required = true)
    protected String categoria;
    protected int anioLanzamiento;
    protected int garantiaMeses;
    @XmlElement(required = true)
    protected Proveedor proveedor;
    @XmlElement(required = true)
    protected String tipoConexion;
    @XmlElement(required = true)
    protected BigDecimal precio;
    @XmlElement(required = true)
    protected BigDecimal descuento;
    @XmlElement(required = true)
    protected Costes costes;
    @XmlAttribute(name = "codigo", required = true)
    protected String codigo;
    @XmlAttribute(name = "numeroSerie", required = true)
    protected String numeroSerie;

    /**
     * Obtiene el valor de la propiedad marca.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Define el valor de la propiedad marca.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMarca(String value) {
        this.marca = value;
    }

    /**
     * Obtiene el valor de la propiedad modelo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Define el valor de la propiedad modelo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModelo(String value) {
        this.modelo = value;
    }

    /**
     * Obtiene el valor de la propiedad categoria.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Define el valor de la propiedad categoria.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategoria(String value) {
        this.categoria = value;
    }

    /**
     * Obtiene el valor de la propiedad anioLanzamiento.
     * 
     */
    public int getAnioLanzamiento() {
        return anioLanzamiento;
    }

    /**
     * Define el valor de la propiedad anioLanzamiento.
     * 
     */
    public void setAnioLanzamiento(int value) {
        this.anioLanzamiento = value;
    }

    /**
     * Obtiene el valor de la propiedad garantiaMeses.
     * 
     */
    public int getGarantiaMeses() {
        return garantiaMeses;
    }

    /**
     * Define el valor de la propiedad garantiaMeses.
     * 
     */
    public void setGarantiaMeses(int value) {
        this.garantiaMeses = value;
    }

    /**
     * Obtiene el valor de la propiedad proveedor.
     * 
     * @return
     *     possible object is
     *     {@link Proveedor }
     *     
     */
    public Proveedor getProveedor() {
        return proveedor;
    }

    /**
     * Define el valor de la propiedad proveedor.
     * 
     * @param value
     *     allowed object is
     *     {@link Proveedor }
     *     
     */
    public void setProveedor(Proveedor value) {
        this.proveedor = value;
    }

    /**
     * Obtiene el valor de la propiedad tipoConexion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoConexion() {
        return tipoConexion;
    }

    /**
     * Define el valor de la propiedad tipoConexion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoConexion(String value) {
        this.tipoConexion = value;
    }

    /**
     * Obtiene el valor de la propiedad precio.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPrecio() {
        return precio;
    }

    /**
     * Define el valor de la propiedad precio.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPrecio(BigDecimal value) {
        this.precio = value;
    }

    /**
     * Obtiene el valor de la propiedad descuento.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDescuento() {
        return descuento;
    }

    /**
     * Define el valor de la propiedad descuento.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDescuento(BigDecimal value) {
        this.descuento = value;
    }

    /**
     * Obtiene el valor de la propiedad costes.
     * 
     * @return
     *     possible object is
     *     {@link Costes }
     *     
     */
    public Costes getCostes() {
        return costes;
    }

    /**
     * Define el valor de la propiedad costes.
     * 
     * @param value
     *     allowed object is
     *     {@link Costes }
     *     
     */
    public void setCostes(Costes value) {
        this.costes = value;
    }

    /**
     * Obtiene el valor de la propiedad codigo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Define el valor de la propiedad codigo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigo(String value) {
        this.codigo = value;
    }

    /**
     * Obtiene el valor de la propiedad numeroSerie.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroSerie() {
        return numeroSerie;
    }

    /**
     * Define el valor de la propiedad numeroSerie.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroSerie(String value) {
        this.numeroSerie = value;
    }

}
