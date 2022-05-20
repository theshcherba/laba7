package utility.parser;

import classesandenums.Person;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utility.Console;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.StringWriter;
import java.util.LinkedHashSet;


public class ToXml {
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
    private static final String TAG_COLLECTION = "people";
    private static final String TAG_ELEMENT = "person";

    public String parseToXml(LinkedHashSet<Person> collection, String pathFile) {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            Element elementCollection = document.createElement(TAG_COLLECTION);
            document.appendChild(elementCollection);
            Object[] arrayOfObjects = collection.toArray();
            Person[] arrayPeople = new Person[arrayOfObjects.length];
            for (int i = 0; i < arrayOfObjects.length; i++) {
                arrayPeople[i] = (Person) arrayOfObjects[i];
            }

            for (int i = 0; i < arrayPeople.length; i++) {

                Element elementPERSON = document.createElement(TAG_ELEMENT);
                elementCollection.appendChild(elementPERSON);

                Element elementId = document.createElement(TAG_ID);
                elementId.appendChild(document.createTextNode(String.valueOf(arrayPeople[i].getId())));
                elementPERSON.appendChild(elementId);

                Element elementName = document.createElement(TAG_NAME);
                elementName.appendChild(document.createTextNode(arrayPeople[i].getName()));
                elementPERSON.appendChild(elementName);

                Element elementCoordinateX = document.createElement(TAG_COORDINATE_X);
                elementCoordinateX.appendChild(document.createTextNode(String.valueOf(arrayPeople[i].getCoordinates().getX())));
                elementPERSON.appendChild(elementCoordinateX);

                Element elementCoordinateY = document.createElement(TAG_COORDINATE_Y);
                elementCoordinateY.appendChild(document.createTextNode(String.valueOf(arrayPeople[i].getCoordinates().getY())));
                elementPERSON.appendChild(elementCoordinateY);

                Element elementHeight = document.createElement(TAG_HEIGHT);
                elementHeight.appendChild(document.createTextNode(String.valueOf(arrayPeople[i].getHeight())));
                elementPERSON.appendChild(elementHeight);

                Element elementEyeColor = document.createElement(TAG_EYE_COLOR);
                elementEyeColor.appendChild(document.createTextNode(String.valueOf(arrayPeople[i].getEyeColor())));
                elementPERSON.appendChild(elementEyeColor);

                Element elementHairColor = document.createElement(TAG_HAIR_COLOR);
                elementHairColor.appendChild(document.createTextNode(String.valueOf(arrayPeople[i].getHairColor())));
                elementPERSON.appendChild(elementHairColor);

                Element elementNationality = document.createElement(TAG_NATIONALITY);
                elementNationality.appendChild(document.createTextNode(String.valueOf(arrayPeople[i].getNationality())));
                elementPERSON.appendChild(elementNationality);

                Element elementLocationX = document.createElement(TAG_LOCATION_X);
                elementLocationX.appendChild(document.createTextNode(String.valueOf(arrayPeople[i].getLocation().getX())));
                elementPERSON.appendChild(elementLocationX);

                Element elementLocationY = document.createElement(TAG_LOCATION_Y);
                elementLocationY.appendChild(document.createTextNode(String.valueOf(arrayPeople[i].getLocation().getY())));
                elementPERSON.appendChild(elementLocationY);

                Element elementLocationZ = document.createElement(TAG_LOCATION_Z);
                elementLocationZ.appendChild(document.createTextNode(String.valueOf(arrayPeople[i].getLocation().getZ())));
                elementPERSON.appendChild(elementLocationZ);

                Element elementLocationName = document.createElement(TAG_LOCATION_NAME);
                elementLocationName.appendChild(document.createTextNode(String.valueOf(arrayPeople[i].getLocation().getName())));
                elementPERSON.appendChild(elementLocationName);

                Element elementCreationDate = document.createElement(TAG_CREATION_DATE);
                elementCreationDate.appendChild(document.createTextNode(String.valueOf(arrayPeople[i].getCreationDate())));
                elementPERSON.appendChild(elementCreationDate);

            }
            File file = new File(pathFile);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(writer));
            String output = writer.getBuffer().toString().replaceAll("\n|\r", "");

            return output;
        } catch (ParserConfigurationException | TransformerException e) {
            Console.printerror("Ошибка записи файла!");
        }
        return null;
    }

}
