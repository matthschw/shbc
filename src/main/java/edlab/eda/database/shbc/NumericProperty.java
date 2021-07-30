package edlab.eda.database.shbc;

import java.io.File;
import java.math.BigDecimal;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class NumericProperty extends SimpleProperty {

  public static final String TYPE_ID = "numeric";
  private BigDecimal property;

  public NumericProperty(BigDecimal property) {
    this.property = property;
  }

  public BigDecimal getNumber() {
    return this.property;
  }

  public String toString() {
    return this.property.toString();
  }

  public boolean equal(Object o) {

    if (o instanceof NumericProperty) {
      NumericProperty numericProperty = (NumericProperty) o;
      return numericProperty.property.equals(this.property);
    } else {
      return false;
    }
  }

  @Override
  Element build(String name, Document document) {
    Element element = document.createElement(name);
    element.setAttribute(Container.TYPE_ID, TYPE_ID);
    element.setTextContent(this.property.toString());
    return element;
  }

  @Override
  Element build(String name, Document document, File file) {
    return build(name, document);
  }
}