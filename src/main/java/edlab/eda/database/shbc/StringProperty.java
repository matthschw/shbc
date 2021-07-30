package edlab.eda.database.shbc;

public class StringProperty extends SimpleProperty {

  private String property;

  public StringProperty(String property) {
    this.property = property;
  }

  public String getString() {
    return this.property;
  }
  
  public String toString() {
    return this.property.toString();
  }
}