package commands;

import utility.CollectionManager;
import utility.Console;

public class SaveCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public SaveCommand(CollectionManager collectionManager) {
        super("save", "сохранить коллекцию в файл");
        this.collectionManager = collectionManager;
    }

    public boolean execute(String argument) {
        collectionManager.saveCollection();
        Console.println("Коллекция успешно сохранена!");
        return true;
    }
}
