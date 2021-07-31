package edlab.eda.database.shbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

public class ContainerTest {

  @Test
  void test() {

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

    level2_2.addProperty("randomNumbers", list);
    level1.addContainer("randomNumbers", level2_2);

    level1.save(first);

    Container level1_copy = Container.read(first);

    level1_copy.save(second);

    if (!level1.equals(level1_copy)) {
      fail("Unqueal container");
    }
    /*
     * try { FileUtils.deleteDirectory(first); } catch (IOException e) { }
     */
    try {
      FileUtils.deleteDirectory(second);
    } catch (IOException e) {
    }

  }
}
