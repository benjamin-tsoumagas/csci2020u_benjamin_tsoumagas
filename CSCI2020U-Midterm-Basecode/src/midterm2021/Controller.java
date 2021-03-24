package midterm2021;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class Controller {

    /**
     * Parses info.xml in order to retrieve information (id,name,email,description)
     * @return String array containing id, name, email, and description.
     */
    public String[] readXML() {

        String[] info = new String[4];

        try {
            File xml = new File("./resources/info.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document docu = db.parse(xml);
            docu.getDocumentElement().normalize();

            NodeList xmlNodes = docu.getElementsByTagName("info");

            for (int i = 0; i < xmlNodes.getLength(); i++){
                Node xmlNode = xmlNodes.item(i);
                if (xmlNode.getNodeType() == Node.ELEMENT_NODE){
                    Element xmlElement = (Element)xmlNode;
                    String studentID = xmlElement.getElementsByTagName("student").item(0).getTextContent();
                    String name = xmlElement.getElementsByTagName("name").item(0).getTextContent();
                    String email = xmlElement.getElementsByTagName("email").item(0).getTextContent();
                    String desc = xmlElement.getElementsByTagName("software-description").item(0).getTextContent();

                    //Gets rid of formatting around studentID and description
                    studentID = studentID.replace(name,"");
                    studentID = studentID.replace(email,"");
                    studentID = studentID.replace(" id= ", "");
                    desc = desc.substring(9);

                    info[0] = studentID;
                    info[1] = name;
                    info[2] = email;
                    info[3] = desc;
                    return info;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
