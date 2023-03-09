package DateBook;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

public class MonthTask extends Task implements Repetable {
    public MonthTask(String headLine, String description, TaskType taskType, LocalDateTime firstDate) throws WrongInputException {
        super(headLine, description, taskType, firstDate);
    }

    @Override
    public boolean checkOccurance(LocalDateTime localDateTime) {
        return false;
    }
}

