import java.net.URI;
import java.io.IOException;
/**
 * Main class to run the Emotiv WebSocket client.
 *
 *  @author javiersgs
 *  @version 0.1
 */
public class Main {

    public static void main(String[] args) throws Exception {
        DanceDelegate delegate = new DanceDelegate();
        URI uri = new URI("wss://localhost:6868");
        DanceSocket ws = new DanceSocket(uri, delegate);
        ws.connect();
//        Thread eyeTrackerThread = new Thread(() -> {
//            try {
//                EyeTracker.trackEyeGaze();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//
//        eyeTrackerThread.start();
//        eyeTrackerThread.join();

    }
}
