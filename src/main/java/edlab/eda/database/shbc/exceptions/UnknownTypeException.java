package edlab.eda.database.shbc.exceptions;

import org.w3c.dom.Node;

public class UnknownTypeException extends Exception {

  private static final long serialVersionUID = 80948913457985872L;

  public UnknownTypeException(String type) {
    super("Identified unknown type " + type);
  }

  public UnknownTypeException(Node node) {
    super("No type is specified for node " + node.getNodeName());
  }
}