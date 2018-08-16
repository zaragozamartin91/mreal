package com.mz.mreal;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.sql.*;

public class JdbcTest {
    public static void main(String[] args) throws SQLException, ParserConfigurationException, TransformerException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.99.100:3306/uniweb_0000", "root", "root");
        System.out.println("Connection successful");

        PreparedStatement preparedStatement = conn.prepareStatement("{call GetPinned(?)}");
        preparedStatement.setInt(1, 1);
        ResultSet resultSet = preparedStatement.executeQuery();

        //        Statement statement = conn.createStatement();
        //        ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
        Document document = toDocument(resultSet);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        //        StreamResult result = new StreamResult(new File("C:\\file.xml"));

        // Output to console for testing
        StreamResult result = new StreamResult(System.out);

        transformer.transform(source, result);

        conn.close();
    }

    public static Document toDocument(ResultSet rs)
            throws ParserConfigurationException, SQLException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        Element results = document.createElement("Results");
        document.appendChild(results);

        ResultSetMetaData rsMetaData = rs.getMetaData();
        int colCount = rsMetaData.getColumnCount();

        while (rs.next()) {
            Element row = document.createElement("Row");
            results.appendChild(row);

            for (int columnIndex = 1; columnIndex <= colCount; columnIndex++) {
                String columnName = rsMetaData.getColumnName(columnIndex);
                Object value = rs.getObject(columnIndex);

                Element node = document.createElement(columnName);
                String nodeValue = rs.wasNull() ? "" : value.toString();
                node.appendChild(document.createTextNode(nodeValue));
                row.appendChild(node);
            }
        }
        return document;
    }
}
