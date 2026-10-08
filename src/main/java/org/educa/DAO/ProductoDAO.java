package org.educa.DAO;

import generated.Productos;
import jakarta.xml.bind.JAXBException;
import org.educa.entity.ProductoEntity;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ProductoDAO {

    Productos obtainProducts(String fileXml) throws JAXBException;
    void writeSummary(String path, String xml) throws IOException;
    void writeExcel(String path, List<ProductoEntity> productos) throws IOException;

}