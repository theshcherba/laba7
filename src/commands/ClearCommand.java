package commands;

import utility.CollectionManager;
import utility.Console;

public class ClearCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
    }

    public boolean execute(String argument) {
        collectionManager.clearCollection();
        Console.println("Коллекция очищена!");
        return true;

    }
}
