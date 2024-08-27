package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Map;

@SpringBootApplication
public class XmlToJsonApplication {

    public static void main(String[] args) {
        SpringApplication.run(XmlToJsonApplication.class, args);
    }
}

@RestController
@RequestMapping("/api")
class XmlToJsonController {

    @GetMapping("/convert")
    public Map<String, Object> convertXmlToJson() throws Exception {
        String xml = "<response>\n" +
                "    <status>success</status>\n" +
                "    <data>\n" +
                "        <user>\n" +
                "            <id>123</id>\n" +
                "            <name>John Doe</name>\n" +
                "            <email>john.doe@example.com</email>\n" +
                "        </user>\n" +
                "    </data>\n" +
                "</response>";

        // Parse XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new org.xml.sax.InputSource(new java.io.StringReader(xml)));
        Element rootElement = document.getDocumentElement();

        // Convert to Map
        return XMLToJsonConverter.xmlToMap(rootElement);
    }
}
