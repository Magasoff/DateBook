package DateBook;


import java.time.LocalDateTime;
import java.util.Objects;

public class Task {

    private String headLine;
    private String description;
    private TaskType taskType;

    private LocalDateTime firstDate;
    private boolean archived;
    private static int counter = 1; // счётчик
    private int id;


    public Task(String headLine, String description, TaskType taskType, LocalDateTime firstDate) {
        this.headLine = headLine;
        this.description = description;
        this.taskType = taskType;
        this.firstDate = firstDate;
        this.archived = false;
        id = counter++;

    }

    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public LocalDateTime getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(LocalDateTime firstDate) {
        this.firstDate = firstDate;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Task.counter = counter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return archived == task.archived && id == task.id && headLine.equals(task.headLine) && description.equals(task.description) && taskType == task.taskType && firstDate.equals(task.firstDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headLine, description, taskType, firstDate, archived, id);
    }

    @Override
    public String toString() {
        return "Task{" +
                "headLine='" + headLine + '\'' +
                ", description='" + description + '\'' +
                ", taskType=" + taskType +
                ", firstDate=" + firstDate +
                ", archived=" + archived +
                ", id=" + id +
                '}';
    }
}






