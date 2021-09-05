package edlab.eda.database.shbc;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Property in the Container
 *
 */
abstract public class Property {

  /**
   * Check if two properties are equal
   */
  public abstract boolean equals(Object o);

  /**
   * Create a XML-Element from a Container Property
   * 
   * @param name     Name of the XML-Node Element in the XML
   * @param document XML-Document
   * @param dir      Directory where the container is created
   * @return XML-Element
   */
  abstract Element build(String name, Document document, File dir);

  /**
   * Create a Container Property from a XML-Node
   * 
   * @param node XML-Node
   * @param dir  Directory where the container is stored
   * @return Container Property
   * @throws UnknownTypeException
   */
  static Property build(Node node, File dir)
      throws UnknownTypeException, MatrixNotAvailable {

    NamedNodeMap map = node.getAttributes();

    if (map != null && map.getNamedItem(Container.TYPE_ID) != null) {

      String type = map.getNamedItem(Container.TYPE_ID).getNodeValue();

      if (type.equals(StringProperty.TYPE_ID)
          || type.equals(NumericProperty.TYPE_ID)
          || type.equals(PropertyList.TYPE_ID)
          || type.equals(BooleanProperty.TYPE_ID)
          || type.equals(PropertyMap.TYPE_ID)) {

        return SimpleProperty.build(node, dir);
      }

      if (type.equals(DoubleMatrixProperty.TYPE_ID)) {
        return DoubleMatrixProperty.build(node, dir);
      }

      throw new UnknownTypeException(type);

    }

    throw new UnknownTypeException(node);
  }
}