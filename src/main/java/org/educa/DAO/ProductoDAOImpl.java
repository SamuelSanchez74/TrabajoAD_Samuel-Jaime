package org.educa.DAO;

import generated.Producto;
import generated.Productos;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.educa.entity.ProductoEntity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {

    /**
     *
     * @param fileXml Ruta del XML
     * @return Devuelve un objeto de la clase Productos (una lista de Producto)
     * @throws JAXBException Excepcion
     */
    @Override
    public Productos obtainProducts(String fileXml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Productos.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (Productos) unmarshaller.unmarshal(new File(fileXml));
    }

    /**
     *
     * @param path ruta
     * @param content texto a imprimir
     * @throws IOException excepcion al escribir en fichero
     */
    @Override
    public void writeSummary(String path, String content) throws IOException {
        File file = new File(path);
        File parent = file.getParentFile();
        if (parent != null) {
            parent.mkdirs();
        }

        try (FileWriter writer = new FileWriter(path)) {
            writer.write(content);
        }
    }

    private void createTextCell(Row row, int column, String value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }


    }
