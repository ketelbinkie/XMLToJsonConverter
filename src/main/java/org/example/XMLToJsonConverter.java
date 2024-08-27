package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class XMLToJsonConverter {

    public static Map<String, Object> xmlToMap(Element element) {
        Map<String, Object> map = new HashMap<>();
        NodeList nodeList = element.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node instanceof Element) {
                Element childElement = (Element) node;
                if (childElement.getChildNodes().getLength() > 1) {
                    map.put(childElement.getTagName(), xmlToMap(childElement));
                } else {
                    map.put(childElement.getTagName(), childElement.getTextContent());
                }
            }
        }
        return map;
    }

    public static void main(String[] args) {
        try {
            // File containing the XML
            File xmlFile = new File("src/main/resources/input.xml");

            // Parse the XML file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            Element rootElement = document.getDocumentElement();

            // Convert XML to Map
            Map<String, Object> map = xmlToMap(rootElement);

            // Convert Map to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);

            // Print JSON
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}