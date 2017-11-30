import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DequeTest {
    @Test
    public void test1() {
        Deque<Integer> deque = new Deque<Integer>();

        deque.addLast(1);
        deque.removeFirst();

        for(int item : deque) {
            int c = item;
        }
    }
}
