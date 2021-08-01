package edlab.eda.database.shbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

public class Container1Test {

  @Test
  void test() throws FileNotFoundException, CorruptedContainerException, UnknownTypeException, MatrixNotAvailable {

    File first = new File("./first");
    File second = new File("./second");

    Container level1 = new Container();

    level1.addProperty("firstName", new StringProperty("John"));
    level1.addProperty("lastName", new StringProperty("Doe"));

    level1.addProperty("height", new NumericProperty(new BigDecimal("181")));

    PropertyList list = new PropertyList();

    list.addLast(new NumericProperty(new BigDecimal("01")));
    list.addLast(new NumericProperty(new BigDecimal("09")));
    list.addLast(new NumericProperty(new BigDecimal("1975")));

    level1.addProperty("birth", list);

    Container level2_1 = new Container();

    level2_1.addProperty("firstName", new StringProperty("Jane"));
    level2_1.addProperty("lastName", new StringProperty("Doe"));

    level2_1.addProperty("height", new NumericProperty(new BigDecimal("165")));

    list = new PropertyList();

    list.addLast(new NumericProperty(new BigDecimal("12")));
    list.addLast(new NumericProperty(new BigDecimal("04")));
    list.addLast(new NumericProperty(new BigDecimal("1978")));

    level2_1.addProperty("birth", list);

    level1.addContainer("wife", level2_1);

    Container level2_2 = new Container();

    list = new PropertyList();

    Random rand = new Random();

    for (int i = 0; i < 1000; i++) {
      list.addLast(
          new NumericProperty(new BigDecimal(rand.nextInt(100000000))));
    }

    double[] matrix1 = new double[rand.nextInt(100) + 1];

    for (int i = 0; i < matrix1.length; i++) {
      matrix1[i] = rand.nextDouble();
    }

    DoubleMatrixProperty doubleMatrix1 = DoubleMatrixProperty.create(matrix1);

    level2_2.addProperty("matrix1", doubleMatrix1);

    double[][] matrix2 = new double[rand.nextInt(100) + 1][rand.nextInt(100)
        + 1];

    for (int i = 0; i < matrix2.length; i++) {
      for (int j = 0; j < matrix2[0].length; j++) {
        matrix2[i][j] = rand.nextDouble();
      }
    }

    DoubleMatrixProperty doubleMatrix2 = DoubleMatrixProperty.create(matrix2);

    level2_2.addProperty("matrix2", doubleMatrix2);

    double[][][] matrix3 = new double[rand.nextInt(100) + 1][rand.nextInt(100)
        + 1][rand.nextInt(100) + 1];

    for (int i = 0; i < matrix3.length; i++) {
      for (int j = 0; j < matrix3[0].length; j++) {
        for (int k = 0; k < matrix3[0][0].length; k++) {
          matrix3[i][j][k] = rand.nextDouble();
        }
      }
    }

    DoubleMatrixProperty doubleMatrix3 = DoubleMatrixProperty.create(matrix3);

    level2_2.addProperty("matrix3", doubleMatrix3);

    double[][][][] matrix4 = new double[rand.nextInt(100) + 1][rand.nextInt(100)
        + 1][rand.nextInt(100) + 1][rand.nextInt(100) + 1];

    for (int i = 0; i < matrix4.length; i++) {
      for (int j = 0; j < matrix4[0].length; j++) {
        for (int k = 0; k < matrix4[0][0].length; k++) {
          for (int l = 0; l < matrix4[0][0][0].length; l++) {
            matrix4[i][j][k][l] = rand.nextDouble();
          }
        }
      }
    }

    DoubleMatrixProperty doubleMatrix4 = DoubleMatrixProperty.create(matrix4);

    level2_2.addProperty("matrix4", doubleMatrix4);

    level2_2.addProperty("randomNumbers", list);
    level1.addContainer("randomNumbers", level2_2);

    level1.save(first);

    Container level1_copy = Container.read(first);

    level1_copy.save(second);

    if (!level1.equals(level1_copy)) {
      fail("Unqueal container");
    }

    try {
      FileUtils.deleteDirectory(first);
    } catch (IOException e) {
    }

    try {
      FileUtils.deleteDirectory(second);
    } catch (IOException e) {
    }
  }
}
