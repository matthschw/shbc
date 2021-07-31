package edlab.eda.database.shbc.exceptions;

import java.io.File;

public class MatrixNotAvailable extends Exception {

  private static final long serialVersionUID = 1L;

  public MatrixNotAvailable(File file) {
    super("Matrix @ " + file.getAbsolutePath() + " not available");
  }
}
