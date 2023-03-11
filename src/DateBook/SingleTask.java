package DateBook;

import java.time.LocalDateTime;


public class SingleTask extends Task implements Repetable {
    public SingleTask(String headLine, String description, TaskType taskType, LocalDateTime firstDate) throws WrongInputException {
        super(headLine, description, taskType, firstDate);
    }

    @Override
    public boolean checkOccurance(LocalDateTime localDateTime) {
        return false;
    }
}


