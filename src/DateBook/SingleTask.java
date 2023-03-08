package DateBook;
import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.time.LocalDateTime;


public class SingleTask extends Task implements Repeatable {
    public SingleTask(String headLine, String description, TaskType taskType, LocalDateTime firstDate) throws WrongInputException {
        super(headLine, description, taskType, firstDate);
    }

    @Override
    public Class<? extends Annotation> value() {return null;}

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
