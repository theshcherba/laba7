package commands;

import classesandenums.Person;
import exceptions.IncorrectInputInScriptException;
import utility.CollectionManager;
import utility.Console;
import utility.QuestionAboutPerson;

import java.time.LocalDateTime;

public class AddIfMinCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    private QuestionAboutPerson questionAboutPerson;

    public AddIfMinCommand(CollectionManager collectionManager, QuestionAboutPerson questionAboutPerson) {
        super("add_if_min {element}", "добавить новый элемент, если его значение меньше, чем у наименьшего");
        this.collectionManager = collectionManager;
        this.questionAboutPerson = questionAboutPerson;
    }

    public boolean execute(String argument) {

        Person personToAdd = null;
        try {
            personToAdd = new Person(
                    collectionManager.generateNextId(),
                    questionAboutPerson.askName(),
                    questionAboutPerson.askCoordinates(),
                    LocalDateTime.now(),
                    questionAboutPerson.askHeight(),
                    questionAboutPerson.askEyeColour(),
                    questionAboutPerson.askHairColour(),
                    questionAboutPerson.askNationality(),
                    questionAboutPerson.askLocation()
            );
        } catch (IncorrectInputInScriptException e) {
            Console.printerror("Возникла ошибка при сборе данных");
        }
        if (collectionManager.collectionSize() == 0 || personToAdd.compareTo(collectionManager.getFirst()) < 0) {
            collectionManager.addToCollection(personToAdd);
            Console.println("Человек успешно добавлен!");
            return true;
        } else Console.printerror("Значение человека больше, чем значение наименьшего из людей!");
        return false;
    }
}


