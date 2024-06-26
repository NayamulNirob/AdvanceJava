
package testxml59;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class XMLWriter {
    public static void main(String[] args) throws TransformerConfigurationException, TransformerException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            
            DocumentBuilder builder= factory.newDocumentBuilder();
            
            Document document = builder.newDocument();
            
            Element root= document.createElement("company");
            document.appendChild(root);
            
            Element employee1 = document.createElement("employee");
            root.appendChild(employee1);
            
            Element id1=document.createElement("id");
            id1.appendChild(document.createTextNode("1"));
            employee1.appendChild(id1);
            
            
            Element name1=document.createElement("name");
            name1.appendChild(document.createTextNode("Najmul islam"));
            employee1.appendChild(name1);
            
            
            Element position1=document.createElement("position");
            position1.appendChild(document.createTextNode("Software Engineer"));
            employee1.appendChild(position1);
            
            
            Element salary1 =document.createElement("Salary");
            salary1.appendChild(document.createTextNode("70000"));
            employee1.appendChild(salary1);
            
            
            
            Element employee2 = document.createElement("employee");
            root.appendChild(employee2);
            
            Element id2=document.createElement("id");
            id2.appendChild(document.createTextNode("2"));
            employee2.appendChild(id2);
            
            
            Element name2=document.createElement("name");
            name2.appendChild(document.createTextNode("Neyamul islam"));
            employee2.appendChild(name2);
            
            
            Element position2=document.createElement("position");
            position2.appendChild(document.createTextNode("Software Engineer"));
            employee2.appendChild(position2);
            
            
            Element salary2 =document.createElement("Salary");
            salary2.appendChild(document.createTextNode("80000"));
            employee2.appendChild(salary2);
            
            
            
            
            Element employee3 = document.createElement("employee");
            root.appendChild(employee3);
            
            Element id3=document.createElement("id");
            id3.appendChild(document.createTextNode("3"));
            employee3.appendChild(id3);
            
            
            Element name3=document.createElement("name");
            name3.appendChild(document.createTextNode("Nirjash islam"));
            employee3.appendChild(name3);
            
            
            Element position3=document.createElement("position");
            position3.appendChild(document.createTextNode("Software Engineer"));
            employee3.appendChild(position3);
            
            
            Element salary3 =document.createElement("Salary");
            salary3.appendChild(document.createTextNode("85000"));
            employee3.appendChild(salary3);
            
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            
            Transformer transformer = transformerFactory.newTransformer();
            
            DOMSource source = new DOMSource(document);
            
            StreamResult result = new StreamResult(new File("dataXML.xml"));
            
            transformer.transform(source, result);
            
            System.out.println("XML file created successfully!");
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
