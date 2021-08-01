package edlab.eda.database.shbc;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * String Container Property
 */
public class StringProperty extends SimpleProperty {

  static final String TYPE_ID = "string";
  private String property;

  /**
   * Create a StringProperty
   * 
   * @param property Property value
   */
  public StringProperty(String property) {
    this.property = property;
  }

  public String getString() {
    return this.property;
  }

  @Override
  public String toString() {
    return this.property.toString();
  }

  @Override
  public boolean equals(Object o) {

    if (o instanceof StringProperty) {

      StringProperty stringProperty = (StringProperty) o;

      return stringProperty.property.equals(this.property);

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

    element.setTextContent(this.property);
    return element;
  }

  /**
   * Create a StringProperty from a XML-Node
   * 
   * @param node XML-Node
   * @param file Directory where the data is stored
   * @return StringProperty
   */
  static SimpleProperty build(Node node, File file) {
    return new StringProperty(node.getTextContent());
  }
}