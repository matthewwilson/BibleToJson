import java.util.ArrayList;

/**
 * Created by matthew on 11/09/2016.
 */
public class Book {

    private ArrayList<Chapter> chapters;
    private String name;

    public ArrayList<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(ArrayList<Chapter> chapters) {
        this.chapters = chapters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
