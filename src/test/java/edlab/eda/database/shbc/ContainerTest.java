package edlab.eda.database.shbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class ContainerTest {

  @Test
  void test() {

    Container container = new Container();

    container.addProperty("firstName", new StringProperty("John"));
    container.addProperty("lastName", new StringProperty("Doe"));

    container.addProperty("height", new NumericProperty(new BigDecimal("181")));

    PropertyList list = new PropertyList();

    list.addLast(new NumericProperty(new BigDecimal("01")));
    list.addLast(new NumericProperty(new BigDecimal("09")));
    list.addLast(new NumericProperty(new BigDecimal("1975")));

    container.addProperty("birth", list);

    Container container2 = new Container();

    container2.addProperty("firstName", new StringProperty("Jane"));
    container2.addProperty("lastName", new StringProperty("Doe"));

    container2.addProperty("height",
        new NumericProperty(new BigDecimal("165")));

    list = new PropertyList();

    list.addLast(new NumericProperty(new BigDecimal("12")));
    list.addLast(new NumericProperty(new BigDecimal("04")));
    list.addLast(new NumericProperty(new BigDecimal("1978")));

    container2.addContainer("wife", container2);

    container.save(new File("./container"));

  }
}
