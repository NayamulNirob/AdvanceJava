
package testxml59;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

public class TestXML59 {

   
    public static void main(String[] args) {
        
        SAXParserFactory saxpf = SAXParserFactory.newInstance();
                
        saxpf.setNamespaceAware(true);
        saxpf.setValidating(true);
        
        System.out.println("parser will "+(saxpf.isNamespaceAware()? " " : "not")
                +"be namespace aware");
        
         System.out.println("Parser will " + (saxpf.isValidating()? "" : "Not ")
                + "validate XML");
         
         SAXParser parser =null;
         
        try {
            parser =saxpf.newSAXParser();
        } catch (ParserConfigurationException | SAXException ex) {
            Logger.getLogger(TestXML59.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Parser object is :"+parser);
    }
    
}
