package commands;

import classesandenums.Person;
import exceptions.CollectionIsEmptyException;
import exceptions.IncorrectInputInScriptException;
import exceptions.PersonNotFoundException;
import utility.CollectionManager;
import utility.Console;
import utility.QuestionAboutPerson;

import java.time.LocalDateTime;

public class RemoveGreaterCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    private QuestionAboutPerson questionAboutPerson;

    public RemoveGreaterCommand(CollectionManager collectionManager, QuestionAboutPerson questionAboutPerson) {
        super("remove_greater {element}", "удалить из коллекции все элементы, превышающие заданный");
        this.collectionManager = collectionManager;
        this.questionAboutPerson = questionAboutPerson;
    }


    public boolean execute(String argument) {
        try {
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            if (!argument.isEmpty()) {
                Console.println("Зачем аргумент? Ну да ладно");
            }
            Person personToFind = new Person(
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
            Person personFromCollection = collectionManager.getByValue(personToFind);
            if (personFromCollection == null) throw new PersonNotFoundException();
            collectionManager.removeGreater(personFromCollection);
            Console.println("Люди успешно удалены!");
            return true;
        } catch (CollectionIsEmptyException exception) {
            Console.printerror("Коллекция пуста!");
        } catch (PersonNotFoundException exception) {
            Console.printerror("Человека с такими характеристиками в коллекции нет!");
        } catch (IncorrectInputInScriptException e) {
            Console.printerror("Возникла ошибка при сборе данных");
        }
        return false;
    }
}
