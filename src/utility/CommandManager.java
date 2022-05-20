package utility;

import commands.*;

import java.util.HashMap;

/**
 * Управляет командами.
 */
public class CommandManager {
    private HashMap<String, Command> commands;


    public CommandManager(CollectionManager collectionManager, QuestionAboutPerson questionAboutPerson) {
        commands = new HashMap<>();
        commands.put("help", new HelpCommand(commands));
        commands.put("info", new InfoCommand(collectionManager));
        commands.put("show", new ShowCommand(collectionManager));
        commands.put("add", new AddCommand(collectionManager, questionAboutPerson));
        commands.put("update", new UpdateCommand(collectionManager, questionAboutPerson));
        commands.put("remove_by_id", new RemoveByIdCommand(collectionManager));
        commands.put("clear", new ClearCommand(collectionManager));
        commands.put("save", new SaveCommand(collectionManager));
        commands.put("execute_script", new ExecuteScriptCommand(this));
        commands.put("add_if_min", new AddIfMinCommand(collectionManager, questionAboutPerson));
        commands.put("remove_greater", new RemoveGreaterCommand(collectionManager, questionAboutPerson));
        commands.put("remove_lower", new RemoveLowerCommand(collectionManager, questionAboutPerson));
        commands.put("group_counting_by_id", new GroupCountingByIdCommand(collectionManager));
        commands.put("filter_starts_with_name", new FilterStartsWithNameCommand(collectionManager, questionAboutPerson));
        commands.put("print_unique_location", new PrintUniqueLocationCommand(collectionManager, questionAboutPerson));
        commands.put("exit", new ExitCommand());
    }

    public void execute(String commandName, String arguments) {
        try {
            Command command = commands.get(commandName);
            command.execute(arguments);
        } catch (NullPointerException exp) {
            System.out.println("Команда < " + commandName + " > не найдена.");
        }
    }


    public HashMap<String, Command> getCommands() {
        return commands;
    }
}

