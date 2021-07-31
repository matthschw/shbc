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

  public static DoubleMatrixProperty create(double[] data) {

    return new DoubleMatrixProperty(new int[] { data.length }, data);
  }

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