package edlab.eda.database.shbc;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

abstract public class Property {

  public abstract boolean equal(Object o);

  abstract Element build(String name, Document document, File file);
}
