package edlab.eda.database.shbc;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

abstract public class Property {

  public abstract boolean equals(Object o);

  abstract Element build(String name, Document document, File file);

  static Property build(Node node, File dir) {

    NamedNodeMap map = node.getAttributes();

    if (map != null && map.getNamedItem(Container.TYPE_ID) != null) {

      String type = map.getNamedItem(Container.TYPE_ID).getNodeValue();

      if (type.equals(StringProperty.TYPE_ID)
          || type.equals(NumericProperty.TYPE_ID)
          || type.equals(PropertyList.TYPE_ID)) {

        return SimpleProperty.build(node, dir);
      }
    }

    return null;
  }
}