package edlab.eda.database.shbc;

import java.util.ArrayList;
import java.util.Iterator;

public class PropertyList extends SimpleProperty
    implements Iterable<SimpleProperty> {
  private ArrayList<SimpleProperty> properties;

  public PropertyList() {
    this.properties = new ArrayList<SimpleProperty>();
  }

  public PropertyList(ArrayList<SimpleProperty> properties) {
    this.properties = properties;
  }

  public void addFirst(SimpleProperty property) {
    this.properties.add(0, property);
  }

  public void addLast(SimpleProperty property) {
    this.properties.add(properties.size(), property);
  }

  public void addLast(SimpleProperty property, int i) {
    this.properties.add(i, property);
  }

  public boolean remove(SimpleProperty property) {
    return this.properties.remove(property);
  }

  public int getNumOfProperties() {
    return properties.size();
  }

  public Iterator<SimpleProperty> iterator() {
    return properties.iterator();
  }
}
