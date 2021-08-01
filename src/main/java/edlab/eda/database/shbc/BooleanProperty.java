package edlab.eda.database.shbc;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Boolean Container Property
 *
 */
public class BooleanProperty extends SimpleProperty {

  static final String TYPE_ID = "bool";
  static final String TRUE = "true";
  static final String FALSE = "false";

  private boolean property;

  /**
   * Create a BooleanProperty
   * 
   * @param property Property value
   */
  public BooleanProperty(boolean property) {
    this.property = property;
  }

  /**
   * Get boolean property
   * 
   * @return property
   */
  public boolean getValue() {
    return this.property;
  }

  @Override
  Element build(String name, Document document) {

    Element element = document.createElement(Container.ENTRY_ID);

    if (name != null) {
      element.setAttribute(Container.NAME_ID, name);
    }

    element.setAttribute(Container.TYPE_ID, TYPE_ID);

    if (property) {
      element.setTextContent(TRUE);
    } else {
      element.setTextContent(FALSE);
    }

    return element;
  }

  /**
   * Create a BooleanProperty from a XML-Node
   * 
   * @param node XML-Node
   * @param file Directory where the data is stored
   * @return BooleanProperty
   */
  static SimpleProperty build(Node node, File file) {

    if (node.getTextContent().equals(TRUE)) {
      return new BooleanProperty(true);
    } else {
      return new BooleanProperty(false);
    }
  }

  @Override
  public boolean equals(Object o) {

    if (o instanceof BooleanProperty) {
      BooleanProperty obj = (BooleanProperty) o;
      return obj.property == this.property;
    } else {
      return false;
    }
  }
}