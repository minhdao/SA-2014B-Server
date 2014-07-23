/**
 * Created by minh on 7/23/14.
 * Test class to create test object
 * Used to read/write between sockets
 * TODO this class should be replaced latter
 */
public class Test {
    private String message;

    public Test(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
