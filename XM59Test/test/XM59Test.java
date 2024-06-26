


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;


public class XM59Test {

   
    public static void main(String[] args) {
      
        SAXParserFactory factory =SAXParserFactory.newDefaultInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(true);
        
        System.out.println("Parser Will "+(factory.isNamespaceAware()?"":"not")+"be name space awer");
        
        System.out.println("Parser Will "+(factory.isValidating()?"":"not")+"Validate XML");
        
        SAXParser parser =null;
        
        
        try {
            parser=factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException ex) {
            Logger.getLogger(XM59Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("parse object is :"+parser);
    }
    
}
