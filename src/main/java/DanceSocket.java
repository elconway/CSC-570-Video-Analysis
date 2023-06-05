import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.util.Vector;

public class DanceSocket extends EmotivSocket{

    DanceDelegate danceDelegate;
    LineChart<Number, Number> emotivPlot;

    public DanceSocket(URI serverURI, DanceDelegate delegate, LineChart<Number, Number> plot) throws Exception {
        super(serverURI, delegate);
        danceDelegate = delegate;
        emotivPlot = plot;
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
            danceDelegate.pad(object, emotivPlot);
        }
    }
}
