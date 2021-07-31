package edlab.eda.database.shbc;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * List of {@link SimpleProperty} objects
 *
 */
public class PropertyList extends SimpleProperty
    implements Iterable<SimpleProperty> {

  static final String TYPE_ID = "list";

  private ArrayList<SimpleProperty> properties;

  /**
   * Create an empty PropertyList
   */
  public PropertyList() {
    this.properties = new ArrayList<SimpleProperty>();
  }

  /**
   * Create a PropertyList
   * 
   * @param properties List of {@link SimpleProperty}
   */
  public PropertyList(ArrayList<SimpleProperty> properties) {
    this.properties = properties;
  }

  /**
   * Add a {@link SimpleProperty} as first list element
   * 
   * @param property Simple property to be added
   */
  public void addFirst(SimpleProperty property) {
    this.properties.add(0, property);
  }

  /**
   * Add a {@link SimpleProperty} as last list element
   * 
   * @param property Simple property to be added
   */
  public void addLast(SimpleProperty property) {
    this.properties.add(properties.size(), property);
  }

  /**
   * Add a {@link SimpleProperty} at a specific index
   * 
   * @param property Simple property to be added
   * @param i        Index where the property is added
   */
  public void add(SimpleProperty property, int i) {
    this.properties.add(i, property);
  }

  /**
   * Remove a {@link SimpleProperty} from the list
   * 
   * @param property {@link SimpleProperty} to be removed
   * @return <code>true</code> if property was removed successfully,
   *         <code>false</code> otherwise
   */
  public boolean remove(SimpleProperty property) {
    return this.properties.remove(property);
  }

  /**
   * Get number of added properties
   * 
   * @return Number of properties in the list
   */
  public int getNumOfProperties() {
    return properties.size();
  }

  @Override
  public Iterator<SimpleProperty> iterator() {
    return properties.iterator();
  }

  @Override
  public boolean equals(Object o) {

    if (o instanceof PropertyList) {
      PropertyList propertyList = (PropertyList) o;

      if (this.properties.size() != propertyList.properties.size()) {
        return false;
      }

      for (int i = 0; i < this.properties.size(); i++) {

        if (!this.properties.get(i).equals(propertyList.properties.get(i))) {
          return false;
        }
      }

      return true;

    } else {
      return false;
    }
  }

  @Override
  Element build(String name, Document document) {

    Element element = document.createElement(Container.ENTRY_ID);

    if (name != null) {
      element.setAttribute(Container.NAME_ID, name);
    }

    element.setAttribute(Container.TYPE_ID, TYPE_ID);

    for (SimpleProperty simpleProperty : this.properties) {
      element.appendChild(simpleProperty.build(Container.ENTRY_ID, document));
    }

    return element;
  }

  /**
   * Create a PropertyList from a XML-Node
   * 
   * @param node XML-Node
   * @param file Directory where the data is stored
   * @return PropertyList
   */
  static SimpleProperty build(Node node, File file) {

    PropertyList list = new PropertyList();

    NodeList nodeList = node.getChildNodes();
    Node elem;
    NamedNodeMap map;

    for (int i = 0; i < nodeList.getLength(); i++) {

      elem = nodeList.item(i);

      map = elem.getAttributes();

      if (map != null && map.getNamedItem(Container.TYPE_ID) != null) {

        list.addLast(SimpleProperty.build(elem, file));
      }
    }
    return list;
  }
}
