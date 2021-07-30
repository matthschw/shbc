package edlab.eda.database.shbc;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class SimpleProperty extends Property {

  abstract Element build(String name, Document document);
}