package edlab.eda.database.shbc;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;

import edlab.eda.database.shbc.exceptions.CorruptedContainerException;
import edlab.eda.database.shbc.exceptions.UnknownTypeException;

public class Demo {

  public static void main(String[] args) throws FileNotFoundException,
      CorruptedContainerException, UnknownTypeException {

    // Create a new container
    Container top = new Container();

    // Add a string property
    top.addProperty("property1", new StringProperty("value of property 1"));

    // Add a numeric property
    top.addProperty("property2", new NumericProperty(new BigDecimal(42)));

    // Create a list of properties
    PropertyList propertyList = new PropertyList();
    propertyList.addLast(new StringProperty("value of property 2"));
    propertyList.addLast(new StringProperty("value of property 3"));

    // Add propertyList to container
    top.addProperty("property list", propertyList);

    // Create 1-D DoubleMatrixProperty
    DoubleMatrixProperty matrix1 = DoubleMatrixProperty
        .create(new double[] { 1.0, 1.6, 1.9 });
    top.addProperty("matrix1", matrix1);

    // Create 2-D DoubleMatrixProperty
    DoubleMatrixProperty matrix2 = DoubleMatrixProperty.create(new double[][] {
        { 4.56, 15455.7, 1119.8 }, { 0.001, 0.5423424, 0.26323 } });
    top.addProperty("matrix2", matrix2);

    // Create a new container
    Container sub = new Container();
    top.addProperty("property4", new StringProperty("value of property 4"));

    // Add a container to another container
    top.addContainer("sub", sub);

    File file = new File("./container");

    // Save container to hard disk
    top.save(file);

    // Read container from hard disk
    Container c = Container.read(file);
  }
}