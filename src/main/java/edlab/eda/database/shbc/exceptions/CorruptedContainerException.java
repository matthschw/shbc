package edlab.eda.database.shbc.exceptions;

import java.io.File;

public class CorruptedContainerException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

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