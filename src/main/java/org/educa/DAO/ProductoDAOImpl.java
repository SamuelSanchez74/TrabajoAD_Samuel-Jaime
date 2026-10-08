package org.educa.DAO;

import generated.Productos;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.apache.poi.ss.usermodel.CellStyle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ProductoDAOImpl implements ProductoDAO {

    private static final String MONEY_FORMAT = "#,##0.00 \"€\"";
    private static final String PERCENT_FORMAT = "0.00%";
    private static final String[] EXCEL_HEADERS = {
            "Codigo", "Número de Serie", "Precio", "Descuento",
            "Precio Final", "Costes Envío", "Costes Almacenaje", "Beneficio"};

    private record RowStyles(CellStyle code, CellStyle text, CellStyle money, CellStyle percent) {
    }

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
}
