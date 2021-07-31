package edlab.eda.database.shbc;

import org.w3c.dom.Node;

/**
 * Exception which is thrown when a type in XML is not provided or not valid
 *
 */
public class UnknownTypeException extends Exception {

  private static final long serialVersionUID = 80948913457985872L;

  public UnknownTypeException(String type) {
    super("Identified unknown type " + type);
  }

  public UnknownTypeException(Node node) {
    super("No type is specified for node " + node.getNodeName());
  }
}