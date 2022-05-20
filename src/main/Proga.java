package main;

import utility.CollectionManager;
import utility.CommandManager;
import utility.QuestionAboutPerson;
import utility.parser.FromXml;

import java.util.Scanner;

public class Proga {
    public static final String PS2 = "> ";

    public static void main(String[] args) {
        Scanner userScanner = new Scanner(System.in);
        QuestionAboutPerson questionAboutPerson = new QuestionAboutPerson(userScanner);
        String pathFile = System.getenv("laba_path");
        CollectionManager collectionManager = new CollectionManager(pathFile);
        CommandManager commandManager = new CommandManager(collectionManager, questionAboutPerson);

        try {
            if (pathFile.length() > 0) {
                FromXml parserFromXml = new FromXml();
                parserFromXml.parse(pathFile);
            }
        } catch (NullPointerException exception) {
            System.out.println("File not found");
            return;
        }

        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("Введите команду");
            String arguments;
            String[] commandNameAndArguments = input.nextLine().split(" ");
            String commandName = commandNameAndArguments[0];
            if (commandNameAndArguments.length > 1) {
                arguments = commandNameAndArguments[1];
            } else arguments = "";
            commandManager.execute(commandName, arguments);
        }
    }

}
