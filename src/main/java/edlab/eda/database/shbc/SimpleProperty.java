package edlab.eda.database.shbc;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Simple Container Property
 *
 */
public abstract class SimpleProperty extends Property {

  /**
   * Create a XML-Element from a Container Property
   * 
   * @param name     Name of the XML-Node Element in the XML
   * @param document XML-Document
   * @return XML-Element
   */
  abstract Element build(String name, Document document);

  @Override
  public Element build(String name, Document document, File dir) {
    return this.build(name, document);
  }

  @Override
  public abstract boolean equals(Object o);

  /**
   * Create a SimpleProperty from a XML-Node
   * 
   * @param node XML-Node
   * @param file Directory where the data is stored
   * @return SimpleProperty
   */
  static SimpleProperty build(Node node, File dir) {

    NamedNodeMap map = node.getAttributes();

    switch (map.getNamedItem(Container.TYPE_ID).getNodeValue()) {

    case StringProperty.TYPE_ID:

      return StringProperty.build(node, dir);

    case NumericProperty.TYPE_ID:

      return NumericProperty.build(node, dir);

    case PropertyList.TYPE_ID:

      return PropertyList.build(node, dir);

    case BooleanProperty.TYPE_ID:

      return BooleanProperty.build(node, dir);

    }

    return null;
  }
}