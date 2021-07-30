package edlab.eda.database.shbc;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class StringProperty extends SimpleProperty {

  public static final String TYPE_ID = "string";
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

  public boolean equal(Object o) {

    if (o instanceof StringProperty) {
      StringProperty stringProperty = (StringProperty) o;
      return stringProperty.property.equals(this.property);
    } else {
      return false;
    }
  }

  @Override
  Element build(String name, Document document) {
    
    Element element = document.createElement(name);
    element.setAttribute(Container.TYPE_ID, TYPE_ID);
    element.setTextContent(this.property);
    return element;
  }

  @Override
  Element build(String name, Document document, File file) {
    return build(name, document);
  }

}