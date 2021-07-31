package edlab.eda.database.shbc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

import org.apache.commons.io.FileUtils;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edlab.eda.database.shbc.exceptions.CorruptedContainerException;
import edlab.eda.database.shbc.exceptions.MatrixNotAvailable;
import edlab.eda.database.shbc.exceptions.UnknownTypeException;

public class Container {

  static final String CONFIG_FILE_NAME = "shbc.xml";
  static final String TYPE_ID = "type";
  static final String NAME_ID = "name";
  static final String ENTRY_ID = "entry";

  static final String INT_ID = "string";

  private Map<String, Container> containers;
  private Map<String, Property> properties;

  /**
   * Create an empty container
   */
  public Container() {
    this.properties = new HashMap<String, Property>();
    this.containers = new HashMap<String, Container>();
  }

  /**
   * @hidden
   */
  private Container(Map<String, Container> containers,
      Map<String, Property> properties) {
    this.containers = containers;
    this.properties = properties;
  }

  /**
   * Add a property to a container
   * 
   * @param name     Name (Key) of the Property
   * @param property Property to be added
   * @return <code>true</code> if property was added successfully,
   *         <code>false</code> otherwise
   */
  public boolean addProperty(String name, Property property) {

    if (this.properties.keySet().contains(name)) {
      return false;
    } else {
      this.properties.put(name, property);
      return true;
    }
  }

  /**
   * Remove a property from a container
   * 
   * @param name Name (Key) of the Property
   * @return <code>true</code> if property was removed successfully,
   *         <code>false</code> otherwise
   */
  public boolean removeProperty(String name) {

    if (this.containers.keySet().contains(name)) {
      this.containers.remove(name);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Add a container to a container
   * 
   * @param name      Name (Key) of the Container
   * @param container Container to be added
   * @return <code>true</code> if container was added successfully,
   *         <code>false</code> otherwise
   */
  public boolean addContainer(String name, Container container) {

    if (this.containers.keySet().contains(name)) {
      return false;
    } else {
      this.containers.put(name, container);
      return true;
    }
  }

  /**
   * Remove a container from a container
   * 
   * @param name Name (Key) of the Container
   * @return <code>true</code> if container was removed successfully,
   *         <code>false</code> otherwise
   */
  public boolean removeContainer(String name) {

    if (this.containers.keySet().contains(name)) {
      this.containers.remove(name);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Check if two containers are equal
   */
  public boolean equals(Object o) {

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

  /**
   * Save a Container to a directory. If the directory already exists, it will
   * be overwritten
   * 
   * @param dir Directory on the hard disk where the container should be created
   * @return <code>true</code> if container was saved successfully on the drive,
   *         <code>false</code> otherwise
   */
  public boolean save(File dir) {

    if (dir.exists()) {
      try {
        FileUtils.deleteDirectory(dir);
      } catch (IOException e) {
      }
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

      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");

      DOMSource domSource = new DOMSource(document);
      StreamResult streamResult = new StreamResult(
          new File(dir, CONFIG_FILE_NAME));

      transformer.transform(domSource, streamResult);

      for (String key : this.containers.keySet()) {

        this.containers.get(key).save(new File(dir, key));
      }

      return true;

    } catch (ParserConfigurationException e) {
      try {
        FileUtils.deleteDirectory(dir);
      } catch (IOException e1) {
      }
      return false;
    } catch (TransformerConfigurationException e) {
      try {
        FileUtils.deleteDirectory(dir);
      } catch (IOException e1) {
      }
      return false;
    } catch (TransformerException e) {
      try {
        FileUtils.deleteDirectory(dir);
      } catch (IOException e1) {
      }
      return false;
    }
  }

  /**
   * Read a container from the hard disk
   * 
   * @param dir Directory on the hard disk where the container is saved
   * @return <code>true</code> if container was read successfully from the
   *         drive, <code>false</code> otherwise
   * @throws CorruptedContainerException
   * @throws FileNotFoundException
   * @throws UnknownTypeException 
   * @throws MatrixNotAvailable 
   */
  public static Container read(File dir)
      throws CorruptedContainerException, FileNotFoundException, UnknownTypeException, MatrixNotAvailable {

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
          Node node;
          NamedNodeMap map;

          Map<String, Container> containers = new HashMap<String, Container>();
          Map<String, Property> properties = new HashMap<String, Property>();

          for (int i = 0; i < nodeList.getLength(); i++) {

            node = nodeList.item(i);

            map = node.getAttributes();

            if (map != null && map.getNamedItem(TYPE_ID) != null
                && map.getNamedItem(NAME_ID) != null) {

              try {
                properties.put(map.getNamedItem(NAME_ID).getTextContent(),
                    Property.build(node, dir));
              } catch (DOMException e) {
                throw CorruptedContainerException
                    .getCorruptedXMLException(config);
              }
            }
          }

          for (File file : dir.listFiles()) {
            if (file.isDirectory() && file.canRead()) {
              containers.put(file.getName(), read(file));
            }
          }

          return new Container(containers, properties);

        } catch (ParserConfigurationException e) {
          throw CorruptedContainerException.getCorruptedXMLException(config);
        } catch (SAXException e) {
          throw CorruptedContainerException.getCorruptedXMLException(config);
        } catch (IOException e) {
          throw CorruptedContainerException.getCorruptedXMLException(config);
        }

      } else {
        throw new FileNotFoundException(config.getAbsolutePath());
      }
    } else {
      return null;
    }
  }
}