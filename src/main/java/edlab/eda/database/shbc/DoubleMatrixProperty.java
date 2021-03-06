package edlab.eda.database.shbc;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Double-Matrix Container Property
 * 
 */
public class DoubleMatrixProperty extends MatrixProperty {

  static final String TYPE_ID = "double";
  private double[] values;

  private DoubleMatrixProperty(int[] dimensions) {
    super(dimensions);
  }

  private DoubleMatrixProperty(int[] dimensions, double[] values) {
    super(dimensions);
    this.values = values;
  }

  /**
   * Create a DoubleMatrixProperty from 4-dimensional double array
   * 
   * @param data 4-dimensional double array
   * @return DoubleMatrixProperty
   */
  public static DoubleMatrixProperty create(double[][][][] data) {

    double[] refracturedData = new double[data.length * data[0].length
        * data[0][0].length * data[0][0][0].length];

    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[0].length; j++) {
        for (int k = 0; k < data[0][0].length; k++) {
          for (int l = 0; l < data[0][0][0].length; l++) {

            refracturedData[i * data[0][0][0].length * data[0][0].length
                * data[0].length + data[0][0][0].length * data[0][0].length * j
                + data[0][0][0].length * k + l] = data[i][j][k][l];
          }
        }
      }
    }

    return new DoubleMatrixProperty(new int[] { data.length, data[0].length,
        data[0][0].length, data[0][0][0].length }, refracturedData);
  }

  /**
   * Convert the DoubleMatrixProperty to a 4-dimensional double array
   * 
   * @return 4-dimensional double array
   */
  public double[][][][] convertTo4D() {

    if (getDimenions() == 4) {

      double[][][][] retval = new double[this.dimensions[0]][this.dimensions[1]][this.dimensions[2]][this.dimensions[3]];

      for (int i = 0; i < dimensions[0]; i++) {
        for (int j = 0; j < dimensions[1]; j++) {
          for (int k = 0; k < dimensions[2]; k++) {
            for (int l = 0; l < dimensions[3]; l++) {

              retval[i][j][k][l] = this.values[this.dimensions[3]
                  * this.dimensions[2] * this.dimensions[1] * i
                  + this.dimensions[3] * this.dimensions[2] * j
                  + this.dimensions[3] * k + l];
            }
          }
        }
      }

      return retval;

    } else {
      return new double[0][0][0][0];
    }
  }

  /**
   * Create a DoubleMatrixProperty from 3-dimensional double array
   * 
   * @param data 3-dimensional double array
   * @return DoubleMatrixProperty
   */
  public static DoubleMatrixProperty create(double[][][] data) {

    double[] refracturedData = new double[data.length * data[0].length
        * data[0][0].length];

    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[0].length; j++) {
        for (int k = 0; k < data[0][0].length; k++) {

          refracturedData[i * data[0][0].length * data[0].length
              + data[0][0].length * j + k] = data[i][j][k];
        }
      }
    }

    return new DoubleMatrixProperty(
        new int[] { data.length, data[0].length, data[0][0].length },
        refracturedData);

  }

  /**
   * Convert the DoubleMatrixProperty to a 3-dimensional double array
   * 
   * @return 3-dimensional double array
   */
  public double[][][] convertTo3D() {

    if (getDimenions() == 3) {

      double[][][] retval = new double[this.dimensions[0]][this.dimensions[1]][this.dimensions[2]];

      for (int i = 0; i < dimensions[0]; i++) {
        for (int j = 0; j < dimensions[1]; j++) {
          for (int k = 0; k < dimensions[2]; k++) {

            retval[i][j][k] = this.values[this.dimensions[2]
                * this.dimensions[1] * i + this.dimensions[2] * j + k];
          }
        }
      }

      return retval;

    } else {
      return new double[0][0][0];
    }
  }

  /**
   * Create a DoubleMatrixProperty from 2-dimensional double array
   * 
   * @param data 2-dimensional double array
   * @return DoubleMatrixProperty
   */
  public static DoubleMatrixProperty create(double[][] data) {

    double[] refracturedData = new double[data.length * data[0].length];

    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[0].length; j++) {
        refracturedData[i * data[0].length + j] = data[i][j];
      }
    }

    return new DoubleMatrixProperty(new int[] { data.length, data[0].length },
        refracturedData);
  }

  /**
   * Convert the DoubleMatrixProperty to a 2-dimensional double array
   * 
   * @return 2-dimensional double array
   */
  public double[][] convertTo2D() {

    if (getDimenions() == 2) {

      double[][] retval = new double[this.dimensions[0]][this.dimensions[1]];

      for (int i = 0; i < dimensions[0]; i++) {
        for (int j = 0; j < dimensions[1]; j++) {

          retval[i][j] = this.values[this.dimensions[1] * i + j];
        }
      }

      return retval;

    } else {
      return new double[0][0];
    }
  }

  /**
   * Create a DoubleMatrixProperty from 1-dimensional double array
   * 
   * @param data 1-dimensional double array
   * @return DoubleMatrixProperty
   */
  public static DoubleMatrixProperty create(double[] data) {

    return new DoubleMatrixProperty(new int[] { data.length }, data);
  }

  /**
   * Convert the DoubleMatrixProperty to a 1-dimensional double array
   * 
   * @return 1-dimensional double array
   */
  public double[] convertTo1D() {

    if (getDimenions() == 1) {

      double[] retval = new double[this.dimensions[0]];

      for (int i = 0; i < dimensions[0]; i++) {
        retval[i] = this.values[i];
      }

      return retval;

    } else {
      return new double[0];
    }
  }

  @Override
  Element build(String name, Document document, File file) {

    Element element = document.createElement(Container.ENTRY_ID);

    if (name != null) {
      element.setAttribute(Container.NAME_ID, name);
    }

    element.setAttribute(Container.TYPE_ID, TYPE_ID);

    Element sub;

    for (int i = 0; i < dimensions.length; i++) {
      sub = document.createElement(Container.ENTRY_ID);
      sub.setAttribute(MatrixProperty.DIM_ID, "" + this.dimensions[i]);
      element.appendChild(sub);
    }

    try {
      BufferedOutputStream stream = new BufferedOutputStream(
          new FileOutputStream(new File(file, name)),
          this.values.length * Double.BYTES);

      byte[] bytes = new byte[Double.BYTES];

      for (int k = 0; k < this.values.length; k++) {

        ByteBuffer.wrap(bytes).putDouble(this.values[k]);

        stream.write(bytes);
      }

      stream.close();

      return element;

    } catch (FileNotFoundException e) {
    } catch (IOException e) {
    }

    return null;
  }

  @Override
  public boolean equals(Object o) {

    if (o instanceof DoubleMatrixProperty) {
      DoubleMatrixProperty doubleMatrixProperty = (DoubleMatrixProperty) o;

      if (doubleMatrixProperty.dimensions.length != this.dimensions.length) {
        return false;
      }

      for (int i = 0; i < dimensions.length; i++) {
        if (doubleMatrixProperty.dimensions[i] != this.dimensions[i]) {
          return false;
        }
      }

      for (int i = 0; i < values.length; i++) {
        if (doubleMatrixProperty.values[i] != this.values[i]) {
          return false;
        }
      }

      return true;

    } else {
      return false;
    }
  }

  /**
   * Create a DoubleMatrixProperty from a XML-Node
   * 
   * @param node XML-Node
   * @param file Directory where the data is stored
   * @return DoubleMatrixProperty
   */
  static DoubleMatrixProperty build(Node node, File file) {
    ArrayList<Integer> dims = new ArrayList<>();

    NodeList nodeList = node.getChildNodes();
    Node sub;
    NamedNodeMap map;

    for (int i = 0; i < nodeList.getLength(); i++) {

      sub = nodeList.item(i);
      map = sub.getAttributes();

      if (map != null && map.getNamedItem(DIM_ID) != null) {

        dims.add(Integer.parseInt(map.getNamedItem(DIM_ID).getTextContent()));
      }
    }

    int dimensions[] = new int[dims.size()];
    int size = 1;

    for (int i = 0; i < dimensions.length; i++) {
      dimensions[i] = dims.get(i);
      size *= dims.get(i);
    }

    map = node.getAttributes();

    double[] values = new double[size];
    byte[] bytes = new byte[values.length * Double.BYTES];

    DataInputStream stream;
    try {
      stream = new DataInputStream(new FileInputStream(new File(file,
          map.getNamedItem(Container.NAME_ID).getTextContent())));
      try {
        stream.read(bytes);
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      stream.close();

    } catch (FileNotFoundException e) {

    } catch (DOMException e) {

    } catch (IOException e) {

    }

    for (int i = 0; i < values.length; i++) {

      values[i] = ByteBuffer.wrap(bytes, Double.BYTES * i, Double.BYTES)
          .getDouble();
    }

    return new DoubleMatrixProperty(dimensions, values);
  }
}