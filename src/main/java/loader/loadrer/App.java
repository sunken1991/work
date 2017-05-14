package loader.loadrer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 * Hello world!
 *
 */
public class App 
{
	static String xmlFilePath= "c:\\users\\rakesh elangovan\\desktop\\kofax_sample.xml";
    public static void main( String[] args )
    {
    	ArrayList<HashMap<String, Object>> fldrDocList = new ArrayList<HashMap<String, Object>>();
    	App s =new App();
    	staxParser d = new staxParser();
    	HashMap<String, Object> result = s.folderList();
    	if(result.size() > 0){
    		
    		 ArrayList<HashMap<String, String>> docdata = d.DocumentList(xmlFilePath);
    		 result.put("Document", docdata);
    		 fldrDocList.add(new HashMap<String, Object>(result));
    		 System.out.println(fldrDocList.toString());
    		 
    		 //looping result set to get folder and document
    		/* for (HashMap<String, Object> result1: fldrDocList){
    			 
    			 System.out.println(result1.get("Document").toString());
    			 result1.remove("Document");
    			 System.out.println(result1.toString());
    		 }*/
    	}
    	else{
    		ArrayList<HashMap<String, String>> onlydocdata = d.DocumentList(xmlFilePath);
    		System.out.println(onlydocdata.toString());
    		
    	}
    	   
    }
         
    
    public HashMap<String, Object> folderList(){
    	boolean bFolder=false;
   	 HashMap<String, Object> fldrlist = new HashMap<String, Object>();
   
   	 try {
   		
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader =
            factory.createXMLEventReader(
               new FileReader(xmlFilePath));
            	//	new FileReader("c:\\rakesh\\kofax.xml"));

               while(eventReader.hasNext()){
                  XMLEvent event = eventReader.nextEvent();
                  switch(event.getEventType()){
                     case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        if (qName.equalsIgnoreCase("FOLDERDATA")) {
                       	 bFolder= true;
                        } 	
                        if(bFolder==true && qName.equalsIgnoreCase("FolderFIELD")){
                            Iterator < Attribute > attributes = startElement.getAttributes();
                  	     
                  	      while (attributes.hasNext()) {
                  	       Attribute myAttribute = attributes.next();
                  	       		//System.out.println(attributes.next().getValue().toString()+":"+myAttribute.getValue().toString());
                  	          fldrlist.put(attributes.next().getValue().toString(), myAttribute.getValue().toString());
                  	      }
                  	      
                        }
                         break;
                     
                     case  XMLStreamConstants.END_ELEMENT:
                        EndElement endElement = event.asEndElement();
                        if(endElement.getName().getLocalPart().equalsIgnoreCase("Folder")){
                       	System.out.println("Retieved folder details Successfully"); 
                        }
                        break;
                  }		    
               }
               //System.out.println("FolderList:" + arraylist.toString());
               
            } catch (FileNotFoundException e) {
               e.printStackTrace();
            } catch (XMLStreamException e) {
               e.printStackTrace();
         }
	return fldrlist;
   	

    }
    }

