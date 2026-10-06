package org.educa.DAO;

import generated.Productos;
import jakarta.xml.bind.JAXBException;

import java.io.File;
import java.io.IOException;

public interface ProductoDAO {

    Productos obtainProducts(String fileXml) throws JAXBException;
    void writeSummary(String path, String xml) throws IOException;


}