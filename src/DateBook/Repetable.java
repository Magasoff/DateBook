package DateBook;

import java.time.LocalDateTime;
import java.lang.reflect.Type;

public interface Repetable {
    boolean checkOccurance(LocalDateTime localDateTime);

    void setHeadLine(String headLine);

    void setArchived(boolean archived);

    LocalDateTime getFirstDate();
}



