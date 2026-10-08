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
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {

    //Formato de las celdas: euros con 2 decimales y porcentaje
    private static final String MONEY_FORMAT = "#,##0.00 \"€\"";
    private static final String PERCENT_FORMAT = "0.00%";
    //Titulos de las columnas del Excel, en el mismo orden que en el enunciado
    private static final String[] EXCEL_HEADERS = {
            "Codigo", "Número de Serie", "Precio", "Descuento",
            "Precio Final", "Costes Envío", "Costes Almacenaje", "Beneficio"};

    //Agrupa los 4 estilos que usa cada fila (codigo, texto, dinero y porcentaje)
    //asi no tengo que pasar los 4 por separado
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
        //JAXB convierte el XML en objetos Java
        JAXBContext context = JAXBContext.newInstance(Productos.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (Productos) unmarshaller.unmarshal(new File(fileXml));
    }

    /**
     *
     * @param path    ruta
     * @param content texto a imprimir
     * @throws IOException excepcion al escribir en fichero
     */
    @Override
    public void writeSummary(String path, String content) throws IOException {
        File file = new File(path);
        //Cojo la carpeta donde va el fichero y la creo si no existe
        File parent = file.getParentFile();
        if (parent != null) {
            parent.mkdirs();
        }

        //El try cierra el FileWriter solo cuando termina
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(content);
        }
    }

    /**
     *
     * @param path      ruta archivo a escribir
     * @param productos lista de productos
     * @throws IOException al escribir
     */
    @Override
    public void writeExcel(String path, List<ProductoEntity> productos) throws IOException {
        //Construyo el Excel en memoria y despues lo escribo en el fichero
        //El try cierra tanto el workbook como el fichero al terminar
        try (Workbook workbook = buildWorkbook(productos);
             FileOutputStream out = new FileOutputStream(path)) {
            workbook.write(out);
        }
    }

    //Crea los 4 estilos de una fila con el mismo color de fondo
    private RowStyles createRowStyles(Workbook workbook, Font bold, IndexedColors background) {
        return new RowStyles(
                //Codigo: negrita y centrado
                createStyle(workbook, background, bold, HorizontalAlignment.CENTER, null),
                //Texto normal: a la izquierda
                createStyle(workbook, background, null, HorizontalAlignment.LEFT, null),
                //Dinero: a la derecha con formato de euros
                createStyle(workbook, background, null, HorizontalAlignment.RIGHT, MONEY_FORMAT),
                //Descuento: a la derecha con formato de porcentaje
                createStyle(workbook, background, null, HorizontalAlignment.RIGHT, PERCENT_FORMAT));

    }

    // Crea un estilo de celda con color de fondo, alineacion y, si hace falta, fuente y formato
    private CellStyle createStyle(Workbook workbook, IndexedColors background, Font font,
                                  HorizontalAlignment alignment, String dataFormat) {
        CellStyle style = workbook.createCellStyle();
        //Color de fondo (hay que poner tambien el patron SOLID para que se vea)
        style.setFillForegroundColor(background.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(alignment);
        //La fuente y el formato son opcionales, solo los pongo si me los pasan
        if (font != null) {
            style.setFont(font);
        }
        if (dataFormat != null) {
            style.setDataFormat(workbook.createDataFormat().getFormat(dataFormat));
        }
        return style;
    }

    //Escribe un texto en una celda de la fila con su estilo
    private void createTextCell(Row row, int column, String value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    //Escribe un numero en una celda (asi en Excel se puede sumar y no es texto)
    private void createNumberCell(Row row, int column, BigDecimal value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value.doubleValue());
        cell.setCellStyle(style);
    }

    //Monta el Excel: la cabecera y una fila por cada producto
    private Workbook buildWorkbook(List<ProductoEntity> productos) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Inventario");

        //Fuente en negrita para la cabecera y la columna del codigo
        Font bold = workbook.createFont();
        bold.setBold(true);

        //Cabecera: primera fila (la 0) con los titulos
        CellStyle headerStyle = createStyle(workbook, IndexedColors.GREY_25_PERCENT, bold,
                HorizontalAlignment.CENTER, null);
        Row header = sheet.createRow(0);
        for (int i = 0; i < EXCEL_HEADERS.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(EXCEL_HEADERS[i]);
            cell.setCellStyle(headerStyle);
        }

        //Dos estilos que se alternan por filas: verde y blanco
        RowStyles[] rowStyles = {
                createRowStyles(workbook, bold, IndexedColors.LIGHT_GREEN),
                createRowStyles(workbook, bold, IndexedColors.WHITE)};

        //Una fila por producto, empiezan en la fila 1 porque la 0 es la cabecera
        for (int i = 0; i < productos.size(); i++) {
            ProductoEntity entity = productos.get(i);
            Producto producto = entity.getProducto();
            //i % 2 da 0 o 1, asi se van alternando los colores
            RowStyles styles = rowStyles[i % rowStyles.length];
            Row row = sheet.createRow(i + 1);

            createTextCell(row, 0, producto.getCodigo(), styles.code());
            createTextCell(row, 1, producto.getNumeroSerie(), styles.text());
            createNumberCell(row, 2, producto.getPrecio(), styles.money());
            //El descuento viene como 15.50, lo divido entre 100 (movePointLeft(2)) para que Excel muestre 15,50%
            createNumberCell(row, 3, producto.getDescuento().movePointLeft(2), styles.percent());
            createNumberCell(row, 4, entity.getPrecioFinal(), styles.money());
            createNumberCell(row, 5, producto.getCostes().getCostesEnvio(), styles.money());
            createNumberCell(row, 6, producto.getCostes().getCostesAlmacenaje(), styles.money());
            createNumberCell(row, 7, entity.getProfit(), styles.money());
        }

        //Ajusto el ancho de cada columna al contenido
        for (int i = 0; i < EXCEL_HEADERS.length; i++) {
            sheet.autoSizeColumn(i);
        }
        return workbook;
    }


}
