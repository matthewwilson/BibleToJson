import com.google.gson.Gson;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by matthew on 11/09/2016.
 */
public class Main {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        File file = new File("/Users/matthew/Dev/BibleToJson/data/kjv.xml");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);

        //optional, but recommended
        //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        doc.getDocumentElement().normalize();

        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

        NodeList bookNodes = doc.getElementsByTagName("book");

        Bible bible = new Bible();

        ArrayList<Book> books = new ArrayList<Book>();

        for (int i = 0; i < bookNodes.getLength(); i++) {

            Node bookNode = bookNodes.item(i);

            if (bookNode.getNodeType() == Node.ELEMENT_NODE) {

                Element bookElement = (Element) bookNode;

                Book book = new Book();

                book.setName(bookElement.getAttribute("num"));

                ArrayList<Chapter> chapters = new ArrayList<Chapter>();

                NodeList chapterNodes = bookElement.getElementsByTagName("chapter");

                for (int j = 0; j < chapterNodes.getLength(); j++) {

                    Node chapterNode = chapterNodes.item(j);

                    if (chapterNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element chapterElement = (Element) chapterNode;

                        Chapter chapter = new Chapter();
                        chapter.setNum(Integer.parseInt(chapterElement.getAttribute("num")));

                        NodeList verseNodes = chapterElement.getElementsByTagName("verse");

                        ArrayList<Verse> verses = new ArrayList<Verse>();

                        for (int k = 0; k < verseNodes.getLength(); k++) {

                            Node verseNode = verseNodes.item(k);

                            if (verseNode.getNodeType() == Node.ELEMENT_NODE) {

                                Element verseElement = (Element) verseNode;

                                Verse verse = new Verse();
                                verse.setNum(Integer.parseInt(verseElement.getAttribute("num")));
                                verse.setText(verseElement.getTextContent());

                                verses.add(verse);
                            }
                        }

                        chapter.setVerses(verses);
                        chapters.add(chapter);

                    }
                }

                book.setChapters(chapters);

                books.add(book);

            }
        }

        bible.setBooks(books);

        System.out.println();

        Gson gson = new Gson();

        String json = gson.toJson(bible);

        PrintWriter out = new PrintWriter("/Users/matthew/Dev/BibleToJson/data/kjv.json");
        out.println(json);
        out.close();

        for(Book book : bible.getBooks()) {
            String bookName = book.getName();
            json = gson.toJson(book);
            PrintWriter out2 = new PrintWriter("/Users/matthew/Dev/BibleToJson/data/bible/"+bookName+".json");
            out2.println(json);
            out2.close();
        }



    }

}
