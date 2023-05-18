import java.net.URI;

/**
 * Main class to run the Emotiv WebSocket client.
 *
 *  @author javiersgs
 *  @version 0.1
 */
public class Main {

    public static void main(String[] args) throws Exception {
//        DanceDelegate delegate = new DanceDelegate();
//        URI uri = new URI("wss://localhost:6868");
//        DanceSocket ws = new DanceSocket(uri, delegate);
//        ws.connect();
        EyeTracker.trackEyeGaze();

    }
}
