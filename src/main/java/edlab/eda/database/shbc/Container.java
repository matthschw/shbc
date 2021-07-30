package edlab.eda.database.shbc;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Container {

  public static final String CONFIG_FILE_NAME = "shbc.xml";
  public static final String TYPE_ID = "type";

  public static final String INT_ID = "string";

  private Map<String, Container> containers;
  private Map<String, Property> properties;

  public Container() {
    this.properties = new HashMap<String, Property>();
    this.containers = new HashMap<String, Container>();
  }

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

  public boolean equal(Object o) {

    if (o instanceof Object) {

      Container container = (Container) o;

      if (this.properties.size() != container.properties.size()) {
        return false;
      }

      for (String key : this.properties.keySet()) {

        if (container.properties.containsKey(key)) {

          if (!container.properties.get(key).equals(this.properties.get(key))) {
            return false;
          }

        } else {
          return false;
        }
      }

      if (this.containers.size() != container.containers.size()) {
        return false;
      }

      for (String key : this.containers.keySet()) {

        if (container.containers.containsKey(key)) {

          if (!container.containers.get(key).equals(this.containers.get(key))) {
            return false;
          }

        } else {
          return false;
        }
      }

      return true;

    } else {
      return false;
    }
  }

  public boolean save(File dir) {

    if (dir.exists()) {
      dir.delete();
    }

    dir.mkdir();

    DocumentBuilderFactory documentFactory = DocumentBuilderFactory
        .newInstance();

    DocumentBuilder documentBuilder;

    try {
      documentBuilder = documentFactory.newDocumentBuilder();

      Document document = documentBuilder.newDocument();

      Element root = document.createElement("container");

      document.appendChild(root);

      for (String key : this.properties.keySet()) {

        root.appendChild(this.properties.get(key).build(key, document, dir));
      }

      for (String key : this.containers.keySet()) {

        this.containers.get(key).save(new File(dir, key));
      }

      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");

      DOMSource domSource = new DOMSource(document);
      StreamResult streamResult = new StreamResult(
          new File(dir, CONFIG_FILE_NAME));

      transformer.transform(domSource, streamResult);

      return true;

    } catch (ParserConfigurationException e) {
      return false;
    } catch (TransformerConfigurationException e) {
      return false;
    } catch (TransformerException e) {
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
            case StringProperty.TYPE_ID:

              properties.put(elem.getNodeValue(),
                  new StringProperty(elem.getTextContent()));
              break;
              
            case NumericProperty.TYPE_ID:

              properties.put(elem.getNodeValue(),
                  new NumericProperty(new BigDecimal(elem.getTextContent())));
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
