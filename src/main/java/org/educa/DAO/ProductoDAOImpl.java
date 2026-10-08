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
import java.math.BigDecimal;
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

    private void createNumberCell(Row row, int column, BigDecimal value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value.doubleValue());
        cell.setCellStyle(style);
    }

    private Workbook buildWorkbook(List<ProductoEntity> productos) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Inventario");

        Font bold = workbook.createFont();
        bold.setBold(true);

        // Cabecera
        CellStyle headerStyle = createStyle(workbook, IndexedColors.GREY_25_PERCENT, bold,
                HorizontalAlignment.CENTER, null);
        Row header = sheet.createRow(0);
        for (int i = 0; i < EXCEL_HEADERS.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(EXCEL_HEADERS[i]);
            cell.setCellStyle(headerStyle);
        }

        // Dos estilos que se alternan por filas
        RowStyles[] rowStyles = {
                createRowStyles(workbook, bold, IndexedColors.LIGHT_GREEN),
                createRowStyles(workbook, bold, IndexedColors.WHITE)};

        for (int i = 0; i < productos.size(); i++) {
            ProductoEntity entity = productos.get(i);
            Producto producto = entity.getProducto();
            RowStyles styles = rowStyles[i % rowStyles.length];
            Row row = sheet.createRow(i + 1);

            createTextCell(row, 0, producto.getCodigo(), styles.code());
            createTextCell(row, 1, producto.getNumeroSerie(), styles.text());
            createNumberCell(row, 2, producto.getPrecio(), styles.money());
            createNumberCell(row, 3, producto.getDescuento().movePointLeft(2), styles.percent());
            createNumberCell(row, 4, entity.getPrecioFinal(), styles.money());
            createNumberCell(row, 5, producto.getCostes().getCostesEnvio(), styles.money());
            createNumberCell(row, 6, producto.getCostes().getCostesAlmacenaje(), styles.money());
            createNumberCell(row, 7, entity.getProfit(), styles.money());
        }

        for (int i = 0; i < EXCEL_HEADERS.length; i++) {
            sheet.autoSizeColumn(i);
        }
        return workbook;
    }


    }
