package edlab.eda.database.shbc;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Container {

  public static final String CONFIG_FILE_NAME = "shbc.xml";
  public static final String TYPE_ID = "type";
  public static final String STRING_ID = "string";
  public static final String INT_ID = "string";

  private Map<String, Container> containers;
  private Map<String, Property> properties;

  private Container(Map<String, Container> containers,
      Map<String, Property> properties) {
    this.containers = containers;
    this.properties = properties;
  }

  public boolean addProperty(String name, Property property) {

    if (this.properties.keySet().contains(name)) {
      return false;
    } else {
      this.properties.put(name, property);
      return true;
    }
  }

  public boolean removeProperty(String name) {

    if (this.containers.keySet().contains(name)) {
      this.containers.remove(name);
      return true;
    } else {
      return false;
    }
  }

  public boolean addContainer(String name, Container container) {

    if (this.containers.keySet().contains(name)) {
      return false;
    } else {
      this.containers.put(name, container);
      return true;
    }
  }

  public boolean removeContainer(String name) {

    if (this.containers.keySet().contains(name)) {
      this.containers.remove(name);
      return true;
    } else {
      return false;
    }
  }

  public static Container read(File dir) {

    if (dir.isDirectory() && dir.canRead()) {

      File config = new File(dir, CONFIG_FILE_NAME);

      if (config.isFile() && config.canRead()) {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;

        try {
          dBuilder = dbFactory.newDocumentBuilder();
          Document doc = dBuilder.parse(config);

          doc.getDocumentElement().normalize();

          Node top = doc.getDocumentElement();
          NodeList nodeList = top.getChildNodes();
          Node elem;
          NamedNodeMap map;

          Map<String, Container> containers = new HashMap<String, Container>();
          Map<String, Property> properties = new HashMap<String, Property>();

          for (int i = 0; i < nodeList.getLength(); i++) {
            elem = nodeList.item(i);

            map = elem.getAttributes();

            switch (map.getNamedItem(TYPE_ID).getNodeValue()) {
            case STRING_ID:

              properties.put(elem.getNodeValue(),
                  new StringProperty(elem.getTextContent()));
              break;

            default:
              break;
            }
          }

          return new Container(containers, properties);

        } catch (ParserConfigurationException e) {
          e.printStackTrace();
          return null;
        } catch (SAXException e) {
          e.printStackTrace();
          return null;
        } catch (IOException e) {
          e.printStackTrace();
          return null;
        }
      } else {

        return null;
      }
    } else {
      return null;
    }
  }
  
  public String toString() {
    return null;

  }
  
  private String toString(String intdent) {
    return intdent;  
  }

}
