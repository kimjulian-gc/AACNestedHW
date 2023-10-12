import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import structures.AssociativeArray;
import structures.KeyNotFoundException;

/**
 * Keeps track of the complete set of AAC mappings.
 *
 * @author Julian Kim
 */
public class AACMappings {
  /**
   * Fields
   */
  AssociativeArray<String, AACCategory> categories;
  AACCategory currentCategory;

  /**
   * Constructor
   */
  public AACMappings(String filename) throws Exception {
    categories = new AssociativeArray<String, AACCategory>();

    BufferedReader input = new BufferedReader(new FileReader(filename));

    String currImageLoc = "";
    for (String line = input.readLine(); line != null; line = input.readLine()) {
      if (!line.startsWith(">")) {
        String[] lineParsed = line.split(" ");
        AACCategory newCat = new AACCategory(lineParsed[1]);
        currImageLoc = lineParsed[0];
        categories.set(currImageLoc, newCat);
      }
      else {
        line = line.substring(1);
        String[] lineParsed = line.split(" ");
        AACCategory addingCat = categories.get(currImageLoc);
        addingCat.addItem(lineParsed[0], lineParsed[1]);
      }
    }

    input.close();
  }

  /**
   * Methods
   */
  public void add(String imageLoc, String text) {
    if (isHomeScreen()) {
      categories.set(imageLoc, new AACCategory(text));
      return;
    }

    currentCategory.addItem(imageLoc, text);
  }

  public String getCurrentCategory() {
    return currentCategory == null ? "" : currentCategory.getCategory();
  }

  public String[] getImageLocs() {
    if (isHomeScreen()) {
      return categories.getKeys();
    }
    return currentCategory.getImages();
  }

  public String getText(String imageLoc) throws KeyNotFoundException {
    if (isHomeScreen()) {
      currentCategory = categories.get(imageLoc);
      return currentCategory.getCategory();
    }
    return currentCategory.getText(imageLoc);
  }

  public boolean isCategory(String imageLoc) {
    try {
      categories.get(imageLoc);
      return true;
    } catch (KeyNotFoundException e) {
      return false;
    }
  }

  public void reset() {
    currentCategory = null;
  }

  public void writeToFile(String filename) throws Exception {
    PrintWriter out = new PrintWriter(new FileWriter(filename));

    for (String imageLoc : categories.getKeys()) {
      // should not throw KeyNotFoundException...
      // if that happens, that is a rather hard question to be answered
      AACCategory cat = categories.get(imageLoc);
      out.println(imageLoc + " " + cat.getCategory());
      for (String itemImage : cat.getImages()) {
        out.println(">" + itemImage + " " + cat.getText(itemImage));
      }
    }

    out.close();
  }

  private boolean isHomeScreen() {
    return getCurrentCategory() == "";
  }
}
