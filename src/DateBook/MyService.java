package DateBook;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class MyService {

    private static final Map<Integer, Repetable> actualTask = new HashMap<>();
    private static final Map<Integer, Repetable> archivedTask = new HashMap<>();
    private static final Map<Integer, Task> taskMap = new HashMap<>();


    public static void addTask(Scanner scanner) {
        try {
            System.out.println("Введите задачу");
            String headLine = ValidateUtils.checkString(scanner.nextLine());
            System.out.println("Опишите задачу");
            String description = ValidateUtils.checkString(scanner.nextLine());
            System.out.println("Введите Тип задачи");
            TaskType taskType = TaskType.values()[scanner.nextInt()];
            System.out.println("Введите повторяемость задачи: 0 -  однократная, 1 - ежедневная, 2 - еженедельная, 3 - ежемесячная, 4 - ежегоданая");
            int occurance = scanner.nextInt();
            System.out.println("Введите дату dd.MM.yyy HH:mm");
            scanner.nextLine();
            createEvent(scanner, headLine, taskType, description, occurance);
            System.out.println("Для выхода нажмите Enter");
            scanner.nextLine();
        } catch (WrongInputException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createEvent(Scanner scanner, String headLine, TaskType taskType, String description, int occurance) {
        try {
            LocalDateTime eventDate = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            Task task = null;
            try {
                task = createTask(headLine, taskType, description, occurance, eventDate);
                System.out.println("Создана задача" + task);
            } catch (WrongInputException e) {
                System.out.println(e.getMessage());
            }
        } catch (DateTimeParseException e) {
            System.out.println("Проверьте формат ввода dd.MM.yyyy HH:mm и попробуйте еще раз");
            createEvent(scanner, headLine, taskType, description, occurance);
        }
    }



    public static List <Repetable> findTasksByDate (LocalDate localDate) {
        List <Repetable> tasks = new ArrayList<>();
        for (Repetable task : actualTask.values()){
            if (task.checkOccurance(localDate.atStartOfDay()))
                tasks.add(task);
        }
        return tasks;
    }




    private static Task createTask (String headLine, TaskType taskType, String description, int occurance, LocalDateTime localDateTime) throws WrongInputException {
        return switch (occurance) {
            case 0 -> {
                SingleTask singleTask = new SingleTask(headLine, description, taskType, localDateTime);
                taskMap.put(singleTask.getId(), singleTask);
                yield singleTask;
            }
            case 1 -> {
                DailyTask dailyTask = new DailyTask(headLine, description, taskType, localDateTime);
                taskMap.put(dailyTask.getId(), dailyTask);
                yield dailyTask;
            }
            case 2 -> {
                WeeklyTask weeklyTask  = new WeeklyTask(headLine, description, taskType, localDateTime);
                taskMap.put(weeklyTask.getId(), weeklyTask);
                yield weeklyTask;
            }
            case 3 -> {
                MonthTask monthTask = new MonthTask(headLine, description, taskType, localDateTime);
                taskMap.put(monthTask.getId(), monthTask);
                yield monthTask;
            }
            case 4 -> {
                YearTask yearTask = new YearTask(headLine, description, taskType, localDateTime);
                taskMap.put(yearTask.getId(), yearTask);
                yield yearTask;
            }
            default -> null;
        };
    }


    public static void deleteTask(Scanner scanner) {
        System.out.println("Активные задачи\n");
        printActiveTasks();
        try {
            System.out.println("Для удаления введите id задачи\n");
            int id = scanner.nextInt();
            if (actualTask.containsKey(id)) {
                Repetable deleteTask = actualTask.get(id);
                System.out.println("Задача " + id + " удалена\n");
            } else {
                throw new WrongInputException();
            }
        } catch (WrongInputException e) {
            e.printStackTrace();
            System.out.println("Задача по id не найдена\n");
        }
    }





    public static void getTasksByDay (Scanner scanner) {
        System.out.println("Введите дату в формате dd.MM.yyyy");
        try {
            String date = scanner.next();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate requestedDate = LocalDate.parse(date, dateTimeFormatter);
            List<Repetable> foundEvents = findTasksByDate(requestedDate);
            System.out.println("Задачи на " + requestedDate + ": ");
            for (Repetable task : foundEvents) {
                System.out.println(task);
            }
        } catch (DateTimeParseException e) {
            System.out.println("Проверьте формат ввода dd.MM.yyyy и попробуйте еще раз");
        }
        scanner.nextLine();
        System.out.println("Для выхода нажмите Enter\n");
    }





    private static void printActiveTasks () {
        for (Repetable taskType : archivedTask.values()) {
            System.out.println(taskType);
        }
    }






    public static void getGroupByDate (Scanner scanner) {
        Map <LocalDate, ArrayList<Repetable>> taskMap = new HashMap<>();

        for (Map.Entry <Integer,Repetable> entry: actualTask.entrySet()) {
            Repetable task = entry.getValue();
            LocalDate localDate = task.getFirstDate().toLocalDate();
            if (taskMap.containsKey(localDate)) {
                ArrayList<Repetable> tasks = taskMap.get(localDate);
                tasks.add(task);
            } else {
                taskMap.put(localDate, new ArrayList<>(Collections.singletonList(task)));
            }
        }
        for (Map.Entry<LocalDate, ArrayList<Repetable>> taskEntry: taskMap.entrySet()) {
            System.out.println(taskEntry.getKey() + " : " + taskEntry.getValue());
        }
    }

}



































