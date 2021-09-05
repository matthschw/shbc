package edlab.eda.database.shbc;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Map of {@link SimpleProperty} objects
 *
 */
public class PropertyMap extends SimpleProperty
    implements Map<String, SimpleProperty> {

  /**
   * Type-Identifier in XML
   */
  public static final String TYPE_ID = "map";

  private Map<String, SimpleProperty> properties;

  public PropertyMap() {
    this.properties = new HashMap<String, SimpleProperty>();
  }

  public PropertyMap(Map<String, SimpleProperty> properties) {
    this.properties = properties;
  }

  @Override
  Element build(String name, Document document) {

    Element element = document.createElement(Container.ENTRY_ID);

    element.setAttribute(Container.TYPE_ID, TYPE_ID);
    element.setAttribute(Container.NAME_ID, name);
    
    Element sub;

    for (String key : this.properties.keySet()) {

      sub = this.properties.get(key).build(Container.ENTRY_ID, document);

      sub.setAttribute(Container.NAME_ID, key);

      element.appendChild(sub);
    }

    return element;
  }

  @Override
  public boolean equals(Object o) {

    if (o instanceof PropertyMap) {

      PropertyMap propertyMap = (PropertyMap) o;

      for (String key : propertyMap.keySet()) {

        if (!this.keySet().contains(key)) {
          return false;
        }

        if (!propertyMap.get(key).equals(this.get(key))) {
          return false;
        }
      }

      return true;
    } else {
      return false;
    }
  }

  @Override
  public void clear() {
    this.properties.clear();
  }

  @Override
  public boolean containsKey(Object arg0) {

    return this.properties.containsKey(arg0);
  }

  @Override
  public boolean containsValue(Object arg0) {

    return this.properties.containsValue(arg0);
  }

  @Override
  public Set<Entry<String, SimpleProperty>> entrySet() {

    return this.properties.entrySet();
  }

  @Override
  public SimpleProperty get(Object arg0) {

    return this.properties.get(arg0);
  }

  @Override
  public boolean isEmpty() {

    return this.properties.isEmpty();
  }

  @Override
  public Set<String> keySet() {

    return this.properties.keySet();
  }

  @Override
  public SimpleProperty put(String key, SimpleProperty property) {

    return this.properties.put(key, property);
  }

  @Override
  public void putAll(Map<? extends String, ? extends SimpleProperty> arg0) {

    this.properties.putAll(arg0);
  }

  @Override
  public SimpleProperty remove(Object arg0) {

    return this.properties.remove(arg0);
  }

  @Override
  public int size() {

    return this.properties.size();
  }

  @Override
  public Collection<SimpleProperty> values() {

    return this.properties.values();
  }

  /**
   * Create a PropertyMap from a XML-Node
   * 
   * @param node XML-Node
   * @param file Directory where the data is stored
   * @return PropertyList
   */
  static SimpleProperty build(Node node, File file) {

    Map<String, SimpleProperty> properties = new HashMap<String, SimpleProperty>();

    NodeList nodeList = node.getChildNodes();
    Node elem;
    NamedNodeMap map;

    for (int i = 0; i < nodeList.getLength(); i++) {

      elem = nodeList.item(i);

      map = elem.getAttributes();

      if (map != null && map.getNamedItem(Container.NAME_ID) != null) {

        properties.put(map.getNamedItem(Container.NAME_ID).getTextContent(),
            SimpleProperty.build(elem, file));
      }
    }

    return new PropertyMap(properties);
  }
}