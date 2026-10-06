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

    private final ProductoDAO productoDAO = new ProductoDAOImpl();

    private static final String SUMMARY_PREFIX = "result";
    private static final String SUMMARY_EXTENSION = ".txt";

    /**
     *
     * @param fileXml Ruta del XML
     * @return  Devuelve una lista de ProductoEntity
     * @throws JAXBException Excepcion
     */
    public List<ProductoEntity> readFile(String fileXml) throws JAXBException {
        //TODO: Implementar

        List<ProductoEntity> lista = new ArrayList<>();

        Productos productos = productoDAO.obtainProducts(fileXml);

        for (Producto prod : productos.getProducto()) {
            ProductoEntity entity = new ProductoEntity();
            entity.setProducto(prod);

            // Obtener campos numéricos
            BigDecimal precio = prod.getPrecio();
            BigDecimal descuento = prod.getDescuento();
            BigDecimal costeEnvio = prod.getCostes().getCostesEnvio();
            BigDecimal costeAlmacenaje = prod.getCostes().getCostesAlmacenaje();

            // Coste = CostesEnvio + CostesAlmacenaje
            BigDecimal costeTotal = costeEnvio.add(costeAlmacenaje);
            entity.setCost(costeTotal);

            // Precio final = Precio - (Precio * Descuento / 100)
            BigDecimal factorDescuento = descuento.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
            BigDecimal importeDescuento = precio.multiply(factorDescuento);
            BigDecimal precioFinal = precio.subtract(importeDescuento).setScale(2, RoundingMode.HALF_UP);
            entity.setPrecioFinal(precioFinal);

            // Beneficio = Precio final - Coste
            BigDecimal beneficio = precioFinal.subtract(costeTotal).setScale(2, RoundingMode.HALF_UP);
            entity.setProfit(beneficio);

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
        //TODO: Implementar

        List<ProductoEntity> productos = readFile(fileXml);

        File xml = new File(fileXml);
        String fileName = removeExtension(xml.getName());
        String date = fileName.substring(fileName.lastIndexOf('_')+ 1);

        BigDecimal totalProfit = productos.stream().map(ProductoEntity::getProfit).reduce(BigDecimal.ZERO, BigDecimal::add);

        SummaryEntity summary = new SummaryEntity(date, productos.size(), totalProfit, xml.getAbsolutePath(), fileName, xml.length());

        String outputFile = new File(path, SUMMARY_PREFIX + date + SUMMARY_EXTENSION).getPath();
        productoDAO.writeSummary(outputFile, summary.toPrint());

    }

    public void exportExcel(String path, String fileXml) throws JAXBException, IOException, ParseException {
        //TODO: Implementar
    }
}
