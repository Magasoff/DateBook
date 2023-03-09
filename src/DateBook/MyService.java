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


    public static List<Repetable> findTasksByDate(LocalDate localDate) {
        List<Repetable> tasks = new ArrayList<>();
        for (Repetable task : actualTask.values()) {
            if (task.checkOccurance(localDate.atStartOfDay()))
                tasks.add(task);
        }
        return tasks;
    }


    private static Task createTask(String headLine, TaskType taskType, String description, int occurance, LocalDateTime localDateTime) throws WrongInputException {
        switch (occurance) {
            case 0 -> {
                SingleTask singleTask = new SingleTask(headLine, description, taskType, localDateTime);
                taskMap.put(singleTask.getId(), singleTask);
                return singleTask;

            }
            case 1 -> {
                DailyTask dailyTask = new DailyTask(headLine, description, taskType, localDateTime);
                taskMap.put(dailyTask.getId(), dailyTask);
                return dailyTask;
            }
            case 2 -> {
                WeeklyTask weeklyTask = new WeeklyTask(headLine, description, taskType, localDateTime);
                taskMap.put(weeklyTask.getId(), weeklyTask);
                return weeklyTask;
            }
            case 3 -> {
                MonthTask monthTask = new MonthTask(headLine, description, taskType, localDateTime);
                taskMap.put(monthTask.getId(), monthTask);
                return monthTask;

            }
            case 4 -> {
                YearTask yearTask = new YearTask(headLine, description, taskType, localDateTime);
                taskMap.put(yearTask.getId(), yearTask);
                return yearTask;
            }
        }
        ;
        return null;
    }


    public static void deleteTask(Scanner scanner) {
        System.out.println("Активные задачи\n");
        printActualTasks();
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


    public static void getTasksByDay(Scanner scanner) {
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


    public static void printArchivedTasks(Scanner scanner) {
        for (Repetable taskType : archivedTask.values()) {
            System.out.println(taskType);
        }
    }


    public static void getGroupByDate() {
        Map<LocalDate, ArrayList<Repetable>> taskMap = new HashMap<>();

        for (Map.Entry<Integer, Repetable> entry : actualTask.entrySet()) {
            Repetable task = entry.getValue();
            LocalDate localDate = task.getFirstDate().toLocalDate();
            if (taskMap.containsKey(localDate)) {
                ArrayList<Repetable> tasks = taskMap.get(localDate);
                tasks.add(task);
            } else {
                taskMap.put(localDate, new ArrayList<>(Collections.singletonList(task)));
            }
        }
        for (Map.Entry<LocalDate, ArrayList<Repetable>> taskEntry : taskMap.entrySet()) {
            System.out.println(taskEntry.getKey() + " : " + taskEntry.getValue());
        }
    }

    public static void editTask(Scanner scanner) {
        try {
            System.out.println("Редактирование задачи введите id");
            printActualTasks();
            int id = scanner.nextInt();
            if (!actualTask.containsKey(id)) {
                throw new WrongInputException("Задача не найдена");
            }
            System.out.println("Редактирование задачи 0 - заголовок, 1 - описание задачи");
            int menuCase = scanner.nextInt();
            switch (menuCase) {
                case 0 -> {
                    scanner.nextLine();
                    System.out.println("Введите название задачи");
                    String headLine = scanner.nextLine();
                    Task task = (Task) actualTask.get(id);

                    task.setHeadLine(headLine);

                }
                case 1 -> {
                    scanner.nextLine();
                    System.out.println("Опишите задачу");
                    String description = scanner.nextLine();
                    Repetable task = actualTask.get(id);
                    task.setHeadLine(description);
                }
            }
        } catch (WrongInputException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printActualTasks () {
        for (Repetable task: actualTask.values()){
            System.out.println(task);
        }
    }
}






































