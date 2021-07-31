package edlab.eda.database.shbc;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class StringProperty extends SimpleProperty {

  static final String TYPE_ID = "string";
  private String property;

  public StringProperty(String property) {
    this.property = property;
  }

  public String getString() {
    return this.property;
  }

  public String toString() {
    return this.property.toString();
  }

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

  @Override
  Element build(String name, Document document, File file) {
    return build(name, document);
  }

  static SimpleProperty build(Node node, File file) {
    return new StringProperty(node.getTextContent());
  }
}