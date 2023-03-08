package DateBook;
import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.Type;
import java.time.LocalDateTime;

public class MonthTask extends Task implements Repeatable {
    public MonthTask (String headLine, String description, TaskType taskType, LocalDateTime firstDate) throws  WrongInputException {
        super(headLine, description, taskType, firstDate);
    }

    @Override
    public Class<? extends Annotation> value() {
        return null;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}