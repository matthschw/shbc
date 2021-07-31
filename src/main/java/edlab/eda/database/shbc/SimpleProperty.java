package edlab.eda.database.shbc;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public abstract class SimpleProperty extends Property {

  abstract Element build(String name, Document document);
  public abstract boolean equals(Object o);

  static SimpleProperty build(Node node, File dir) {

    NamedNodeMap map = node.getAttributes();

    switch (map.getNamedItem(Container.TYPE_ID).getNodeValue()) {

    case StringProperty.TYPE_ID:

      return StringProperty.build(node, dir);

    case NumericProperty.TYPE_ID:

      return NumericProperty.build(node, dir);

    case PropertyList.TYPE_ID:

      return PropertyList.build(node, dir);

    }
    
    return null;
  }
}