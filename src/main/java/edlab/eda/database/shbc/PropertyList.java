package edlab.eda.database.shbc;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PropertyList extends SimpleProperty
    implements Iterable<SimpleProperty> {

  public static final String TYPE_ID = "list";
  public static final String ENTRY_ID = "entry";

  private ArrayList<SimpleProperty> properties;

  public PropertyList() {
    this.properties = new ArrayList<SimpleProperty>();
  }

  public PropertyList(ArrayList<SimpleProperty> properties) {
    this.properties = properties;
  }

  public void addFirst(SimpleProperty property) {
    this.properties.add(0, property);
  }

  public void addLast(SimpleProperty property) {
    this.properties.add(properties.size(), property);
  }

  public void addLast(SimpleProperty property, int i) {
    this.properties.add(i, property);
  }

  public boolean remove(SimpleProperty property) {
    return this.properties.remove(property);
  }

  public int getNumOfProperties() {
    return properties.size();
  }

  public Iterator<SimpleProperty> iterator() {
    return properties.iterator();
  }

  public boolean equal(Object o) {

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
    
    Element element = document.createElement(name);
    element.setAttribute(Container.TYPE_ID, TYPE_ID);

    for (SimpleProperty simpleProperty : this.properties) {
      element.appendChild(simpleProperty.build(ENTRY_ID, document));
    }
    
    return element;
  }
  
  @Override
  Element build(String name, Document document, File file) {
    return build(name, document);
  }
}