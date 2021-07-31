package edlab.eda.database.shbc;

/**
 * Matrix Container Property
 *
 */
public abstract class MatrixProperty extends ComplexProperty {

  static final String DIM_ID = "dim";
  protected int[] dimensions;
  protected int[] scalings = null;

  protected MatrixProperty(int[] dimensions) {

    this.dimensions = dimensions;

    if (dimensions.length > 1) {

      this.scalings = new int[dimensions.length - 1];

      this.scalings[0] = 1;

      for (int i = 1; i < this.scalings.length; i++) {
        this.scalings[i] = scalings[i - 1] * dimensions[i - 1];
      }
    }
  }

  /**
   * Get the dimension of the matrix
   * 
   * @return Number of Dimensions
   */
  public int getDimenions() {
    return dimensions.length;
  }

  /**
   * @hidden
   */
  int getDatabaseIndex(int[] indices) {

    if (this.dimensions.length == indices.length) {

      int index = 0;

      for (int i = 0; i < indices.length; i++) {
        index += indices[i] * scalings[i];
      }

      return index;

    } else {
      return -1;
    }
  }
}