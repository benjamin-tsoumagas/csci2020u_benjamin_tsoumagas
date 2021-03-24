import java.io.*;
import java.net.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

public class BikeShare{
	
	public static void main(String[] args) {
		try{
			String sURL = "https://api.mockaroo.com/api/e9cc2e00?count=20&key=e99c5530";
			URL netURL = new URL(sURL);
			
			URLConnection conn = netURL.openConnection();
			conn.setDoOutput(false);
			conn.setDoInput(true);
			
			InputStream inStream = conn.getInputStream();
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
			Document document = docBuilder.parse(inStream);
			document.getDocumentElement().normalize();

			NodeList itemNodes = document.getElementsByTagName("station");

			for (int i = 0; i < itemNodes.getLength(); i++) {
			  
			  Element itemElement = (Element)itemNodes.item(i);		  
			  
			  // reading the properties of each of station objs
			  String lat = getTagValue("lat", itemElement);
			  String lon = getTagValue("long", itemElement);
			  String name = getTagValue("name", itemElement);
			  String num = getTagValue("nbBikes", itemElement);
			  String id = getTagValue("id", itemElement);
			  
			  System.out.printf("[%s] %s (%s,%s): %s bikes\n",
							  id,
							  name,
							  lat,
							  lon,
							  num);
		   
			}	
			inStream.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	private static String getTagValue(String tagName, Element elem){
		NodeList tags = elem.getElementsByTagName(tagName);
		if (tags.getLength()>0){
			return tags.item(0).getTextContent();
		}
		return null;
	}
}