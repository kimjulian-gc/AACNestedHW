import structures.AssociativeArray;
import structures.KeyNotFoundException;

public class AACCategory {
  /**
   * Fields
   */
  String categoryName;
  AssociativeArray<String, String> textImageMap;

  /**
   * Constructor
   */
  public AACCategory(String name) {
    categoryName = name;
    textImageMap = new AssociativeArray<String, String>();
  }

  /**
   * Methods
   */
  public void addItem(String imageLoc, String text) {
    textImageMap.set(imageLoc, text);
  }

  public String getCategory() {
    return categoryName;
  }

  public String[] getImages() {
    return textImageMap.getKeys();
  }

  public String getText(String imageLoc) throws KeyNotFoundException {
    // if this throws an exception, we have screwed up somewhere.
    // probably forgot to add an image.
    return textImageMap.get(imageLoc);
  }
  
  public boolean hasImage(String imageLoc) {
    try {
      textImageMap.get(imageLoc);
      return true;
    } catch (KeyNotFoundException e) {
      return false;
    }
    // stub
  }
}
