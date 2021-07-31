package edlab.eda.database.shbc;

import java.io.File;

/**
 * Exception which is thrown when a a matrix is not available
 *
 */
public class MatrixNotAvailable extends Exception {

  private static final long serialVersionUID = 1L;

  public MatrixNotAvailable(File file) {
    super("Matrix @ " + file.getAbsolutePath() + " not available");
  }
}
