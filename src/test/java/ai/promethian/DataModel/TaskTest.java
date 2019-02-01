package ai.promethian.DataModel;
import ai.promethean.DataModel.Task;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TaskTest {
    private Task t1= new Task(1, 10);

    @Test
    void checkEquals() { assertEquals(t1.getDuration(),(10));}


}


