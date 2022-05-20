package utility;

import classesandenums.Location;
import classesandenums.Person;
import utility.parser.FromXml;
import utility.parser.ToXml;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class CollectionManager {
    private static LinkedHashSet<Person> personCollection = new LinkedHashSet<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private final ToXml toXml;
    private FromXml fromXml;
    private String pathFile;

    public CollectionManager(String pathFile) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.toXml = new ToXml();
        this.fromXml = null;
        this.pathFile = pathFile;
        loadCollection();//Загружает коллекцию из файла
    }

    public Person[] collectionToArray() {
        Object[] arrayObjectPeople = personCollection.toArray();
        Person[] arrayPeople = new Person[arrayObjectPeople.length];
        for (int i = 0; i < arrayPeople.length; i++) {
            arrayPeople[i] = (Person) arrayObjectPeople[i];
        }
        return arrayPeople;
    }

    /**
     * выводит уникальные значения поля location всех элементов в коллекции
     */
    public void getArrayOfUniqueLocation() {
        Person[] arrayPeople = collectionToArray();
        Set<Location> locationsSet = new HashSet<>();
        for (int i = 0; i < arrayPeople.length; i++) {
            locationsSet.add(arrayPeople[i].getLocation());
        }
        Object[] locArray = locationsSet.toArray();
        Location[] locations = new Location[locArray.length];
        for (int j = 0; j < locArray.length; j++) {
            locations[j] = (Location) locArray[j];
        }
        for (int k = 0; k < locations.length; k++) {
            System.out.println(locations[k]);
        }
    }

    /**
     * выводит элементы, значение поля name которых начинается с заданной подстроки
     */
    public void filterStartsWithName(String substring) {
        Person[] arrayPeople = collectionToArray();
        Set<Person> selectedPeopleList = new HashSet<>();
        for (int i = 0; i < arrayPeople.length; i++) {
            if (arrayPeople[i].getName().substring(0, substring.length() + 1).equals(substring)) {
                selectedPeopleList.add(arrayPeople[i]);
            }
        }
        Person[] selectedPeopleArray = new Person[selectedPeopleList.size()];
        Object[] selectedObjects = selectedPeopleList.toArray();
        for (int k = 0; k < selectedObjects.length; k++) {
            selectedPeopleArray[k] = (Person) selectedObjects[k];
            System.out.println(selectedPeopleArray[k]);
        }

    }

    /**
     * сгруппировывает элементы коллекции по значению ID поля
     */
    public void groupCountingById() {
        Person[] arrayPeople = collectionToArray();
        Set<Long> selectedIdList = new HashSet<>();
        for (int i = 0; i < arrayPeople.length; i++) {
            if (arrayPeople[i].getId() % 2 == 0) {
                Long id = arrayPeople[i].getId();
                selectedIdList.add(id);
            }
        }

        Integer count = selectedIdList.size();
        Integer idLeft = arrayPeople.length - count;
        System.out.println("Количество четных ID: " + count + ". " + "Количество нечетных ID: " + idLeft + ".");
    }


    /**
     * возращает время последней инициализации или null, если инициализации не было.
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * возращает последнее сохранение времени или ноль, если не было сохранения.
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * возращает название типа коллекции.
     */
    public String collectionType() {
        return personCollection.getClass().getName();
    }

    /**
     * возращает размер коллекции.
     */
    public int collectionSize() {
        return personCollection.size();
    }

    /**
     * возращает первый элемент коллекции или null, если коллекция пуста.
     */
    public Person getFirst() {
        if (personCollection.isEmpty()) return null;
        Person[] people = collectionToArray();
        return people[0];
    }

    /**
     * параметр id персона.
     * возвращает персона по его идентификатору или null, если персон не найден.
     */
    public Person getById(Long id) {
        for (Person person : personCollection) {
            if (person.getId().equals(id)) return person;
        }
        return null;
    }

    /**
     * personToFind ищет уникального персона.
     */
    public Person getByValue(Person personToFind) {
        for (Person person : personCollection) {
            if (person.equals(personToFind)) return person;
        }
        return null;
    }

    /**
     * Добавляет нового персона в коллекцию.
     * Добавяет параметр персона.
     */
    public void addToCollection(Person person) {
        personCollection.add(person);
    }

    /**
     * Удаляет персона из коллекции.
     * Удаляет параметр персона.
     */
    public void removeFromCollection(Person person) {
        personCollection.remove(person);
    }

    /**
     * Удаляет персонов меньше, чем заданный.
     * Параметр personToCompare персона для сравнения.
     */
    public void removeLower(Person personToCompare) {
        personCollection.removeIf(person -> person.compareTo(personToCompare) < 0);
    }

    /**
     * Удаляет персонов больше, чем заданный.
     * Параметр personToCompare персона для сравнения.
     */
    public void removeGreater(Person personToCompare) {
        personCollection.removeIf(person -> person.compareTo(personToCompare) > 0);
    }

    /**
     * Очищает коллекцию.
     */
    public void clearCollection() {
        personCollection.clear();
    }

    /**
     * Генерирует следующий идентификатор.
     * Возращает Next ID.
     */

    public Long generateNextId() {
        if (personCollection.isEmpty()) return 1L;
        int a = (int) (Math.random() * 400000 + 100000);
        Long ID = Long.valueOf(a);
        return ID;
    }

    /**
     * Сохраняет коллекцию в файл.
     */
    public void saveCollection() {
        String xml = toXml.parseToXml(personCollection, pathFile);
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(pathFile));
            try {
                outputStreamWriter.write(xml);
            } catch (IOException exception) {
                Console.printerror("Ошибка записи в файл.");
            } finally {
                outputStreamWriter.flush();
                outputStreamWriter.close();
                lastSaveTime = LocalDateTime.now();
            }
        } catch (IOException exception) {
            Console.printerror("Ошибка доступа к файлу.");
        }
    }

    /**
     * Загружает коллекцию из файла.
     */
    private void loadCollection() {
        personCollection = FromXml.readCollection();
        lastInitTime = LocalDateTime.now();
    }


    public String toString() {
        if (personCollection.isEmpty()) return "Коллекция пуста!";
        Person[] people = collectionToArray();
        String info = "";
        for (Person person : personCollection) {
            info += person;
            if (person != people[people.length - 1]) info += "\n\n";
        }
        return info;
    }

}
