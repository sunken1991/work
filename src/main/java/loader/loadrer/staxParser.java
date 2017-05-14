package loader.loadrer;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class staxParser{
  
  

  public ArrayList<HashMap<String, String>> DocumentList(String xmlFilePath){
	  
      boolean bDocument = false;
      HashMap<String, String> doclist = new HashMap<String, String>();
      ArrayList<HashMap<String, String>> arraylist = new ArrayList<HashMap<String, String>>();
      
      try {
         XMLInputFactory factory = XMLInputFactory.newInstance();
         XMLEventReader eventReader =
         factory.createXMLEventReader(
            new FileReader(xmlFilePath));

            while(eventReader.hasNext()){
               XMLEvent event = eventReader.nextEvent();
               switch(event.getEventType()){
                  case XMLStreamConstants.START_ELEMENT:
                     StartElement startElement = event.asStartElement();
                     String qName = startElement.getName().getLocalPart();
                     if (qName.equalsIgnoreCase("DOCUMENTDATA")) {
                    	 bDocument= true;
                     } 	
                     if(bDocument==true && qName.equalsIgnoreCase("DOCUMENTFIELD")){
                         Iterator < Attribute > attributes = startElement.getAttributes();
               	     
               	      while (attributes.hasNext()) {
               	       Attribute myAttribute = attributes.next();
               	       		//System.out.println(attributes.next().getValue().toString()+":"+myAttribute.getValue().toString());
               	          doclist.put(attributes.next().getValue().toString(), myAttribute.getValue().toString());
               	      }
               	      
                     }
                      break;
                  
                  case  XMLStreamConstants.END_ELEMENT:
                     EndElement endElement = event.asEndElement();
                     if(endElement.getName().getLocalPart().equalsIgnoreCase("DOCUMENT")){
                    	 
                    	 arraylist.add(new HashMap<String, String>(doclist));
                     }
                     break;
               }		    
            }
            //System.out.println("DocumentList:" + arraylist.toString());
            
         } catch (FileNotFoundException e) {
            e.printStackTrace();
         } catch (XMLStreamException e) {
            e.printStackTrace();
      }
	return arraylist;
 
	  
  }
}