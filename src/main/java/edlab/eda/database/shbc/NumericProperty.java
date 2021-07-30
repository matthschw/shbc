package edlab.eda.database.shbc;

import java.math.BigDecimal;

public class NumericProperty extends SimpleProperty {

  private BigDecimal property;

  public NumericProperty(BigDecimal property) {
    this.property = property;
  }

  public BigDecimal getNumber() {
    return this.property;
  }

  public String toString() {
    return this.property.toString();
  }
}