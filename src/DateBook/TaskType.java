package DateBook;

public enum TaskType {

    taskType("Рабочие"),
    taskType1("Личные");

    private String name;

    TaskType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Тип задачи " + name;
    }
}
