package edlab.eda.database.shbc;

import java.io.File;
import java.math.BigDecimal;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Numeric Container Property
 */
public class NumericProperty extends SimpleProperty {

  static final String TYPE_ID = "numeric";
  private BigDecimal property;

  /**
   * Create a NumericProperty
   * 
   * @param property Property value
   */
  public NumericProperty(BigDecimal property) {
    this.property = property;
  }

  /**
   * Get the value of the NumericProperty
   * 
   * @return Numeric Value
   */
  public BigDecimal getNumber() {
    return this.property;
  }

  @Override
  public String toString() {
    return this.property.toString();
  }

  @Override
  public boolean equals(Object o) {

    if (o instanceof NumericProperty) {
      NumericProperty numericProperty = (NumericProperty) o;
      return numericProperty.property.equals(this.property);
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

    element.setTextContent(this.property.toString());
    return element;
  }


  /**
   * Create a NumericProperty from a XML-Node
   * 
   * @param node XML-Node
   * @param file Directory where the data is stored
   * @return NumericProperty
   */
  static SimpleProperty build(Node node, File file) {
    return new NumericProperty(new BigDecimal(node.getTextContent()));
  }
}