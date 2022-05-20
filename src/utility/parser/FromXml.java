package utility.parser;

import classesandenums.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import utility.Console;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;

public class FromXml {
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_COORDINATE_X = "coordinate_x";
    private static final String TAG_COORDINATE_Y = "coordinate_y";
    private static final String TAG_CREATION_DATE = "creation_date";
    private static final String TAG_HEIGHT = "height";
    private static final String TAG_EYE_COLOR = "eye_color";
    private static final String TAG_HAIR_COLOR = "hair_color";
    private static final String TAG_NATIONALITY = "nationality";
    private static final String TAG_LOCATION_X = "location_x";
    private static final String TAG_LOCATION_Y = "location_y";
    private static final String TAG_LOCATION_Z = "location_z";
    private static final String TAG_LOCATION_NAME = "location_name";

    static LinkedHashSet<Person> collectionList = new LinkedHashSet<>();

    public void parse(String pathFile) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document document;
        File file = new File(pathFile);
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(file);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("person");
            for (int i = 0; i < nodeList.getLength(); i++) {
                if (check(nodeList.item(i))) {
                    collectionList.add(getPerson(nodeList.item(i)));
                } else Console.printerror("!В файле для чтения обнаружена ошибка!");

            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public boolean check(Node node) {
        Validator checkCorrectInput = new Validator();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            String locationName = getTagValue(TAG_LOCATION_NAME, element);
            double a = Double.parseDouble(getTagValue(TAG_COORDINATE_X, element));
            long b = Long.parseLong(getTagValue(TAG_COORDINATE_Y, element));
            int height = Integer.parseInt(getTagValue(TAG_HEIGHT, element));
            String name = getTagValue(TAG_NAME, element);
            String eyeColor = getTagValue(TAG_EYE_COLOR, element);
            String hairColor = getTagValue(TAG_HAIR_COLOR, element);
            String nationality = getTagValue(TAG_NATIONALITY, element);

            int isCorrectInput = 1;
            isCorrectInput *= checkCorrectInput.checkName(name);
            isCorrectInput *= checkCorrectInput.checkCoordinateX(a);
            isCorrectInput *= checkCorrectInput.checkCoordinateY(b);
            isCorrectInput *= checkCorrectInput.checkLocationName(locationName);
            isCorrectInput *= checkCorrectInput.checkHeight(height);
            isCorrectInput *= checkCorrectInput.checkEyeColor(eyeColor);
            isCorrectInput *= checkCorrectInput.checkHairColor(hairColor);
            isCorrectInput *= checkCorrectInput.checkCountry(nationality);

            if (isCorrectInput == 1) return true;
            return false;
        }
        return true;
    }


    // создаем из узла документа объект Person
    private static Person getPerson(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) { // проверка узла node на соответсвие элементу
            Element element = (Element) node;
            Double x = Double.valueOf(getTagValue(TAG_LOCATION_X, element));
            double y = Double.parseDouble(getTagValue(TAG_LOCATION_Y, element));
            Long z = Long.valueOf(getTagValue(TAG_LOCATION_Z, element));
            double a = Double.parseDouble(getTagValue(TAG_COORDINATE_X, element));
            long b = Long.parseLong(getTagValue(TAG_COORDINATE_Y, element));
            Person person = new Person(Long.valueOf(getTagValue(TAG_ID, element)),
                    getTagValue(TAG_NAME, element),
                    new Coordinates(a, b),
                    LocalDateTime.parse(getTagValue(TAG_CREATION_DATE, element)),
                    Integer.parseInt(getTagValue(TAG_HEIGHT, element)),
                    EColor.valueOf(getTagValue(TAG_EYE_COLOR, element)),
                    HColor.valueOf(getTagValue(TAG_HAIR_COLOR, element)),
                    Country.valueOf(getTagValue(TAG_NATIONALITY, element)),
                    new Location(x, y, z, getTagValue(TAG_LOCATION_NAME, element)));
            return person;
        }

        return null;
    }

    // получаем значение элемента по указанному тегу
    private static String getTagValue(String tag, Element element) {
        try {
            NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
            Node node = nodeList.item(0);
            return node.getNodeValue();
        } catch (NullPointerException exp) {
            System.out.println(exp);
        }
//        TODO:
//        throw UnableToGetTagValue()
        return "";
    }

    public static LinkedHashSet<Person> readCollection() {

        return collectionList;
    }

}
