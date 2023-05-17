import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.util.Vector;

public class DanceSocket extends EmotivSocket{

    DanceDelegate danceDelegate;
    public DanceSocket(URI serverURI, DanceDelegate delegate) throws Exception {
        super(serverURI, delegate);
        danceDelegate = delegate;
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Received message from Emotiv server.");
        if (!delegate.isSubscribed()) {
            JSONObject response = new JSONObject(message);
            int id = response.getInt("id");
            Object result = response.get("result");
            delegate.handle (id, result, this);
        } else {
            JSONObject object = new JSONObject(message);
            danceDelegate.pad(object);
        }
    }
}
