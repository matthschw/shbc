package edlab.eda.database.shbc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class Container2Test {

  @Test
  void test() {

    Container a = new Container();
    Container b = new Container();
    Container c = new Container();

    a.addContainer("b", b);
    b.addContainer("c", c);

    if (c.addContainer("a", a)) {
      fail("Loop not detected");
    }

  }
}
