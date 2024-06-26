package xm59test;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLReader {

    public static void main(String[] args) {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            DocumentBuilder builder = factory.newDocumentBuilder();

            Document d = builder.parse("Student.xml");
            
            d.getDocumentElement().normalize();
            
            Element root =d.getDocumentElement();
            
            System.out.println("Root Element :"+root.getNodeName());
            
            NodeList nodeList=d.getElementsByTagName("Student");
            
            for(int i=0;i<nodeList.getLength();i++){
            
                Node node =nodeList.item(i);
                
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // Get and print each element's value
                    String id = element.getElementsByTagName("id").item(0).getTextContent();
                    String name = element.getElementsByTagName("name").item(0).getTextContent();
                    String age = element.getElementsByTagName("age").item(0).getTextContent();
                    String subject = element.getElementsByTagName("subject").item(0).getTextContent();

                    System.out.println("Student ID: " + id);
                    System.out.println("Name: " + name);
                    System.out.println("Position: " + age);
                    System.out.println("Subject: " + subject);
                    System.out.println("---------------------------");
                
            }
            }
        } catch (IOException | ParserConfigurationException | DOMException | SAXException e) {
        }

    }
    }

