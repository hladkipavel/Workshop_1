import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    private static final String[] OPTIONS = {"list", "add", "remove", "delete", "exit"};
    private static final String FILE_NAME = "tasks.csv";
    private static final Scanner scanner = new Scanner(System.in);
    private static String[][] taskList;

    public static void main(String[] args) {
        taskList = readFromFile(FILE_NAME);
        while (true){
            System.out.println(ConsoleColors.BLUE + "Please select an options: " + ConsoleColors.RESET);
            for (String str : OPTIONS){
                System.out.println(str);
            }
            String operation = scanner.nextLine();
            switch(operation){
                case "list": list();
                    break;
                case "add": add();
                    break;
                case "remove": remove();
                    break;
                case "delete": delete();
                    break;
                case "exit":
                    System.out.println(ConsoleColors.YELLOW_BOLD + "Bye Bye!");
                    writeToFile(FILE_NAME, taskList);
                    System.exit(0);
                    break;
                default:
                    System.out.println(ConsoleColors.RED + "Select correct operations!"
                            + ConsoleColors.RESET);
                    break;
            }
        }
    }

    public static void list(){
        for (int i = 0; i < taskList.length; i++) {
            System.out.print(String.format("%d : ", i));
            for (int j = 0; j < taskList[i].length; j++) {
                System.out.print(taskList[i][j] + " ");
            }
            System.out.print("\n");
        }

    }
    public static void add(){
        System.out.println(ConsoleColors.PURPLE + "Please add task description: ");
        String description = scanner.nextLine();
        System.out.println(ConsoleColors.PURPLE + "Please add task due date: ");
        String date = scanner.nextLine();
        while(!checkCorrectDate(date)){
            System.out.println(ConsoleColors.RED_BOLD + "Enter correct date");
            date = scanner.nextLine();
        }
        System.out.println(ConsoleColors.PURPLE + "Is your task is important: true/false");
        String important = scanner.nextLine();
        taskList = Arrays.copyOf(taskList, taskList.length + 1);
        taskList[taskList.length -1] = new String[3];
        taskList[taskList.length -1][0] = description;
        taskList[taskList.length -1][1] = date;
        taskList[taskList.length -1][2] = important;
    }
    public static void remove(){
        System.out.println(ConsoleColors.PURPLE + "Select the task number you want to remove"
                + ConsoleColors.RESET);
        String number = scanner.nextLine();
        if(NumberUtils.isParsable(number)){
            int value = Integer.parseInt(number);
                if (value < taskList.length) {
                    taskList = ArrayUtils.remove(taskList, value);
                    System.out.println("Task removed");
                }else{
                    System.out.println(ConsoleColors.RED + "There is no such number in the table!"
                            + ConsoleColors.RESET);
                    remove();
                }
        }else{
            System.out.println(ConsoleColors.RED + "Please enter the correct number!"
            + ConsoleColors.RESET);
            remove();
        }
    }
    public static void delete() {
        taskList = new String[0][0];
        System.out.println(ConsoleColors.GREEN + "Task list deleted! Choose next operations."
                + ConsoleColors.RESET);
    }
    public static void checkExistFile(Path path){
        if(!Files.exists(path)){
            System.out.println("File not exist, can you create new file? yes/no");
            String answer = scanner.nextLine();
            switch (answer){
                case "yes":
                    try {
                        Files.createFile(path);
                        readFromFile(FILE_NAME);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "no":
                    System.exit(0);
                default:
                    System.out.println("Enter correct answer");
                    readFromFile(FILE_NAME);
                    break;
            }
        }
    }
    public static String[][] readFromFile(String fileName){
        Path path = Paths.get(fileName);
        checkExistFile(path);
        String[][] task = null;
        try {
            List<String> lines = Files.readAllLines(path);
            if(lines.size() <= 0){
                return new String[0][0];
            }else {
                task = new String[lines.size()][lines.get(0).split(", ").length];
                for (int i = 0; i < lines.size(); i++) {
                    String[] line = lines.get(i).split(", ");
                    for (int j = 0; j < line.length; j++) {
                        task[i][j] = line[j];
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return task;
    }
    public static void writeToFile(String fileName, String[][] tasks) {
        Path path = Paths.get(fileName);

        List<String> array = new ArrayList<>();
        for (int i = 0; i < tasks.length; i++) {
            array.add(StringUtils.join(tasks[i], " "));
        }

        try {
            Files.write(path, array);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static boolean checkCorrectDate(String date){
        String regex = "[0-9]{4}-{1}[0-9]{2}-{1}[0-9]{2}";
        return date.matches(regex);
    }

}
