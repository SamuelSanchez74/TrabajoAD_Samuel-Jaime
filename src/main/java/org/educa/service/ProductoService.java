package org.educa.service;

import generated.Producto;
import generated.Productos;
import jakarta.xml.bind.JAXBException;
import org.educa.DAO.ProductoDAOImpl;
import org.educa.entity.ProductoEntity;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ProductoService {
    /**
     *
     * @param fileXml Ruta del XML
     * @return  Devuelve una lista de ProductoEntity
     * @throws JAXBException Excepcion
     */
    public List<ProductoEntity> readFile(String fileXml) throws JAXBException {
        //TODO: Implementar

        ProductoDAOImpl ProductoDAO = new ProductoDAOImpl();

        List<ProductoEntity> lista = new ArrayList<>();

        Productos productos = ProductoDAO.obtainProducts(fileXml);

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

    public void exportSummary(String path, String fileXml) throws JAXBException, IOException {
        //TODO: Implementar

    }

    public void exportExcel(String path, String fileXml) throws JAXBException, IOException, ParseException {
        //TODO: Implementar
    }
}
