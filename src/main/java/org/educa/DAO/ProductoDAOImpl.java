package org.educa.DAO;

import generated.Productos;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.apache.poi.ss.usermodel.*;
import org.educa.entity.ProductoEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

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

    @Override
    public void writeExcel(String path, List<ProductoEntity> productos) throws IOException {
        try (Workbook workbook = buildWorkbook(productos);
             FileOutputStream out = new FileOutputStream(path)) {
            workbook.write(out);
        }
    }

    private RowStyles createRowStyles(Workbook workbook, Font bold, IndexedColors background) {
        return new RowStyles(
                createStyle(workbook, background, bold, HorizontalAlignment.CENTER, null),
                createStyle(workbook, background, null, HorizontalAlignment.LEFT, null),
                createStyle(workbook, background, null, HorizontalAlignment.RIGHT, MONEY_FORMAT),
                createStyle(workbook, background, null, HorizontalAlignment.RIGHT, PERCENT_FORMAT));
    }

    private CellStyle createStyle(Workbook workbook, IndexedColors background, Font font,
                                  HorizontalAlignment alignment, String dataFormat) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(background.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(alignment);
        if (font != null) {
            style.setFont(font);
        }
        if (dataFormat != null) {
            style.setDataFormat(workbook.createDataFormat().getFormat(dataFormat));
        }
        return style;
    }

}
