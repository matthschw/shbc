package edlab.eda.database.shbc;

public abstract class MatrixProperty extends ComplexProperty {

  protected int[] dimensions;
  protected int[] scalings;
  protected SimpleProperty[] names;

  public MatrixProperty(int[] dimensions, SimpleProperty[] names) {

    this.dimensions = dimensions;
    this.names = names;

    this.scalings = new int[dimensions.length - 1];

    if (dimensions.length > 1) {

      this.scalings[0] = 1;

      for (int i = 1; i < this.scalings.length; i++) {
        this.scalings[i] = scalings[i - 1] * dimensions[i - 1];
      }
    }
  }
  
  
}
