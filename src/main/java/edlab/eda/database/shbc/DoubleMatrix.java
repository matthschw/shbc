package edlab.eda.database.shbc;

public class DoubleMatrix {

  private int[] dimensions;
  private double[] values;

  private DoubleMatrix(int[] dimensions, double[] values) {
    this.dimensions = dimensions;
    this.values = values;
  }

  public int getDimenions() {
    return dimensions.length;
  }

  public double get(int[] indices) {

    if (this.dimensions.length == indices.length) {

      int i = 0;
      int scale =1;

      for (int j = 0; j < indices.length; j++) {
        
        if (indices[j] >= 0 && indices[j] < this.dimensions[j]) {
          
          i += indices[j] * scale;
          
          scale *= this.dimensions[j];
          
        } else {
          return Double.NaN;
        }
      }

      return values[i];

    } else {
      return Double.NaN;
    }
  }



}
