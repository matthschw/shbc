package edlab.eda.database.shbc;

import java.io.File;

/**
 * Exception which is thrown when a a container is corrupted
 *
 */
public class CorruptedContainerException extends Exception {
  static final long serialVersionUID = 1L;

  private CorruptedContainerException(String message) {
    super(message);
  }

  public CorruptedContainerException(File dir) {
    super("Container @ " + dir.getAbsolutePath() + " is corrupted");
  }

  public static CorruptedContainerException getCorruptedXMLException(File xml) {

    return new CorruptedContainerException(
        "XML @ " + xml.getAbsolutePath() + " is corrupted");
  }
}