package instagram4j;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.utils.IGUtils;
import okhttp3.OkHttpClient;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static instagram4j.TestUtils.*;

public class ExceptionsTest {
    private static final Logger log = Logger.getLogger(ExceptionsTest.class);

    private static IGClient client;

    @BeforeClass
    public static void setUp() throws IOException, ClassNotFoundException {
        BasicConfigurator.configure();
        log.setLevel(Level.ALL);
        OkHttpClient.Builder clientBuilder = IGUtils.defaultHttpClientBuilder();

        setUpProxy(clientBuilder);

        client = IGClient.deserialize(new File(CLIENT_FILE), new File(COOKIE_FILE), clientBuilder);

    }

    @AfterClass
    public static void tearDown() throws IOException {
        client.serialize(new File(CLIENT_FILE), new File(COOKIE_FILE));
        log.info("Client has been serialized successfully");
    }

    @Test
    public void testChallengeException() {
        try {
            client.actions().clips()
                    .uploadClip(new File(PATH + "test.mp4"),
                            new File(PATH + "test.jpg"),
                            "my caption")
                    .get();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
