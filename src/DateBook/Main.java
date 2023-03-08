package DateBook;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            label:
            while (true) {
                System.out.println("Выберите пункт меню:");
                printMenu();
                if (scanner.hasNextInt()) {
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            MyService.addTask(scanner);
                            break;
                        case 2:
                            MyService.deleteTask(scanner);
                            break;
                        case 3:
                            MyService.getGroupByDate(scanner);
                            break;
                        case 0:
                            break label;
                    }
                } else {
                    scanner.next();
                    System.out.println("Выберите пункт меню из списка!");
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println("""
                "1. Добавить задачу" +
                " 2. Удалить задачу" +
                " 3. Получить задачи на указанный день " +
                "0. Выход"""
        );
    }
}
              
    
