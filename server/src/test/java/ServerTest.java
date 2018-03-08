import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import server.Server;

public class ServerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(System.out);
        System.setErr(System.err);
    }

    @Test
    public void testServer() {
        String args[] = {"6000"};
        Server.main(args);
        Assert.assertEquals("Listening on port number 6000\n" +
                "Initializing HTTP Server\n" +
                "Creating contexts\n" +
                "Starting server\n" +
                "Server started\n", outContent.toString());

        args[0] = "65535";
        Server.main(args);
        Assert.assertEquals("Listening on port number 6000\n" +
                "Initializing HTTP Server\n" +
                "Creating contexts\n" +
                "Starting server\n" +
                "Server started\n" +
                "Listening on port number 65535\n" +
                "Initializing HTTP Server\n" +
                "Creating contexts\n" +
                "Starting server\n" +
                "Server started\n", outContent.toString());
        args[0] = "65536";
        Server.main(args);
        Assert.assertEquals("Listening on port number 6000\n" +
                "Initializing HTTP Server\n" +
                "Creating contexts\n" +
                "Starting server\n" +
                "Server started\n" +
                "Listening on port number 65535\n" +
                "Initializing HTTP Server\n" +
                "Creating contexts\n" +
                "Starting server\n" +
                "Server started\n" +
                "Usage: port must be between 6000-65535\n", outContent.toString());
        args[0] = "0";
        Server.main(args);
        Assert.assertEquals("Listening on port number 6000\n" +
                "Initializing HTTP Server\n" +
                "Creating contexts\n" +
                "Starting server\n" +
                "Server started\n" +
                "Listening on port number 65535\n" +
                "Initializing HTTP Server\n" +
                "Creating contexts\n" +
                "Starting server\n" +
                "Server started\n" +
                "Usage: port must be between 6000-65535\n" +
                "Usage: port must be between 6000-65535\n", outContent.toString());
    }

}
