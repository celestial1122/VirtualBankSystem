/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: chengxiang.Huang
 * Date: 2024-05-26
 * Time: 19:30
 */
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {
    private Task task;

    @BeforeEach
    public void setUp() {
        // 初始化一个Task对象用于测试
        task = new Task(1, "Clean Room", 20.0);
    }

    @Test
    public void testTaskInitialization() {
        // 测试任务对象是否正确初始化
        assertEquals(1, task.getId());
        assertEquals("Clean Room", task.getName());
        assertEquals(20.0, task.getReward());
        assertFalse(task.isCompleted());
        assertFalse(task.isAcceptedByChild());
    }

    @Test
    public void testSetAndGetId() {
        // 测试setId和getId方法
        task.setId(2);
        assertEquals(2, task.getId());
    }

    @Test
    public void testSetAndGetName() {
        // 测试setName和getName方法
        task.setName("Do Homework");
        assertEquals("Do Homework", task.getName());
    }

    @Test
    public void testSetAndGetReward() {
        // 测试setReward和getReward方法
        task.setReward(30.0);
        assertEquals(30.0, task.getReward());
    }

    @Test
    public void testSetCompleted() {
        // 测试设置任务为已完成的状态
        task.setCompleted(true);
        assertTrue(task.isCompleted());
    }

    @Test
    public void testSetAcceptedByChild() {
        // 测试任务被孩子接受的状态
        task.setAcceptedByChild(true);
        assertTrue(task.isAcceptedByChild());
    }

    @Test
    public void testToString() {
        // 测试toString方法是否返回正确的字符串表示
        String expectedToString = "ID: 1, Name: Clean Room, Reward: 20.0, Completed: false, Accepted: false";
        assertEquals(expectedToString, task.toString());
    }
}