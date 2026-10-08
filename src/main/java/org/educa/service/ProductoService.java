package org.educa.service;

import generated.Producto;
import generated.Productos;
import jakarta.xml.bind.JAXBException;
import org.educa.DAO.ProductoDAO;
import org.educa.DAO.ProductoDAOImpl;
import org.educa.entity.ProductoEntity;
import org.educa.entity.SummaryEntity;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.io.FilenameUtils.removeExtension;

public class ProductoService {

    // El servicio usa el DAO para leer y escribir ficheros, el DAO lo creamos una sola vez
    private final ProductoDAO productoDAO = new ProductoDAOImpl();

    // Partes del nombre de los ficheros que se generan (result_junio2026.txt y export_junio2026.xlsx)
    private static final String SUMMARY_PREFIX = "result";
    private static final String SUMMARY_EXTENSION = ".txt";
    private static final String EXCEL_PREFIX = "export_";
    private static final String EXCEL_EXTENSION = ".xlsx";

    /**
     *
     * @param fileXml Ruta del XML
     * @return  Devuelve una lista de ProductoEntity
     * @throws JAXBException Excepcion
     */
    public List<ProductoEntity> readFile(String fileXml) throws JAXBException {
        //Lista donde voy guardando cada producto con sus calculos hechos
        List<ProductoEntity> lista = new ArrayList<>();

        //Le pido al DAO que lea el XML y me devuelva los productos
        Productos productos = productoDAO.obtainProducts(fileXml);

        //Recorro todos los productos del XML
        for (Producto prod : productos.getProducto()) {
            ProductoEntity entity = new ProductoEntity();
            entity.setProducto(prod);

            //Saco los numeros del producto para trabajar con ellos
            BigDecimal precio = prod.getPrecio();
            BigDecimal descuento = prod.getDescuento();
            BigDecimal costeEnvio = prod.getCostes().getCostesEnvio();
            BigDecimal costeAlmacenaje = prod.getCostes().getCostesAlmacenaje();

            // Coste = CostesEnvio + CostesAlmacenaje
            BigDecimal costeTotal = costeEnvio.add(costeAlmacenaje);
            entity.setCost(costeTotal);

            // Precio final = Precio - (Precio * Descuento / 100)
            //El descuento viene en porcentaje (15.50), asi que lo divido entre 100 para tener 0.1550
            BigDecimal factorDescuento = descuento.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
            BigDecimal importeDescuento = precio.multiply(factorDescuento);
            //Lo dejo con 2 decimales porquqe es dinero
            BigDecimal precioFinal = precio.subtract(importeDescuento).setScale(2, RoundingMode.HALF_UP);
            entity.setPrecioFinal(precioFinal);

            // Beneficio = Precio final - Coste
            BigDecimal beneficio = precioFinal.subtract(costeTotal).setScale(2, RoundingMode.HALF_UP);
            entity.setProfit(beneficio);
            //Añado el producto ya calculado a la lista
            lista.add(entity);
        }

        return lista;
    }

    /**
     *
     * @param path path where the file is created
     * @param fileXml path of the xml file
     * @throws JAXBException excepcion while procesing the xml
     * @throws IOException excepcion while creating the file
     */
    public void exportSummary(String path, String fileXml) throws JAXBException, IOException {
        //Reutilizo readFile para tener los productos con el beneficio calculado
        List<ProductoEntity> productos = readFile(fileXml);

        //Datos del fichero XML: nombre sin extension (inventario_junio2026) y fecha (junio2026)
        File xml = new File(fileXml);
        String fileName = removeExtension(xml.getName());
        //El +1 es para saltarme el guion bajo y quedarme solo con la fecha
        String date = fileName.substring(fileName.lastIndexOf('_')+ 1);

        //Sumo el beneficio de todos los productos empezando desde 0
        BigDecimal totalProfit = productos.stream().map(ProductoEntity::getProfit).reduce(BigDecimal.ZERO, BigDecimal::add);

        //Creo el resumen con los datos que pide el ejercicio
        SummaryEntity summary = new SummaryEntity(date, productos.size(), totalProfit, xml.getAbsolutePath(), fileName, xml.length());

        //Ruta final del txt: carpeta + result_ + fecha + .txt
        String outputFile = new File(path, SUMMARY_PREFIX + date + SUMMARY_EXTENSION).getPath();
        //El DAO es el que escribe el fichero
        productoDAO.writeSummary(outputFile, summary.toPrint());

    }

    /**
     *
     * @param path ruta carpeta export
     * @param fileXml ruta fichero XML productos
     * @throws JAXBException al procesar el XMl
     * @throws IOException al escribir
     * @throws ParseException al parsear
     */
    public void exportExcel(String path, String fileXml) throws JAXBException, IOException, ParseException {
        //Leo los productos del XML con sus calculos
        List<ProductoEntity> productos = readFile(fileXml);

        //Saco la fecha del nombre del XML: desde el ultimo _ hasta el ultimo punto
        String fileName = new File(fileXml).getName();
        String date = fileName.substring(fileName.lastIndexOf('_') + 1, fileName.lastIndexOf('.'));

        //Creo la carpeta export por si todavia no existe
        new File(path).mkdirs();
        //El DAO construye y guarda el Excel (export_junio2026.xlsx)
        productoDAO.writeExcel(new File(path, EXCEL_PREFIX + date + EXCEL_EXTENSION).getPath(), productos);
    }
}
