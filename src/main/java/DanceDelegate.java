import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import org.java_websocket.client.WebSocketClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jzy3d.chart.Chart;
import org.jzy3d.chart.factories.*;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.Scatter;
import org.jzy3d.plot3d.rendering.canvas.Quality;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Random;
import java.util.Scanner;

public class DanceDelegate extends EmotivDelegate {

    public String clientID, clientSecret;
    private XYChart.Series<Number, Number> engagementSeries;
    private XYChart.Series<Number, Number> excitementSeries;
    private XYChart.Series<Number, Number> stressSeries;
    private XYChart.Series<Number, Number> relaxationSeries;
    private XYChart.Series<Number, Number> interestSeries;
    private XYChart.Series<Number, Number> focusSeries;
    private boolean first;
    private long start = 0;

    public DanceDelegate() {
        //File file = new File("../emotiv.secret");

//        try {
            //Scanner scanner = new Scanner(file);

            //String line = scanner.nextLine();
            System.out.println("Client ID and secret set");
            clientSecret = "jahu1mUAhCrJjgWskfkS1PU8RA62n3mydugMvJczPErKcw155XzmnIaj4ke0LR83zwlT0guWyUJPnWe5VMkrnCPpUubXDNJVm8EYD7cN6SguFoEb7bO6AuQLztaKcTvG";
            clientID = "qoOGabeFVT4rzrQQQheF8mdFxSuFsU17i8yo7Qk0";

            //scanner.close();
            engagementSeries = new XYChart.Series<>();
            excitementSeries = new XYChart.Series<>();
            stressSeries = new XYChart.Series<>();
            relaxationSeries = new XYChart.Series<>();
            interestSeries = new XYChart.Series<>();
            focusSeries = new XYChart.Series<>();
            engagementSeries.setName("Engagement");
            excitementSeries.setName("Excitement");
            stressSeries.setName("Stress");
            relaxationSeries.setName("Relaxation");
            interestSeries.setName("Interest");
            focusSeries.setName("Focus");
            first = true;
            start = Instant.now().getEpochSecond();
    }

    @Override
    public void handle(int id, Object result, WebSocketClient ws) {
        System.out.println("Handle: " + id);
        JSONObject myJsonObj;
        switch (id) {
            case 0:
                getCortexInfo(ws);
                break;
            case 1:
                requestAccess(ws);
                break;
            case 2:
                authorize(ws);
                break;
            case 3:
                myJsonObj = new JSONObject(result.toString());
                cortexToken = myJsonObj.getString("cortexToken");
                getUserInformation(ws);
                break;
            case 4:
                myJsonObj = new JSONObject(result.toString());
                System.out.println(">>>> getUserInformation: " + myJsonObj.toString());
                firstName = myJsonObj.getString("firstName");
                lastName = myJsonObj.getString("lastName");
                userName = myJsonObj.getString("username");
                queryHeadsets(ws);
                break;
            case 5:
                JSONArray myJsonArr = new JSONArray(result.toString());
                headset = myJsonArr.getJSONObject(0).getString("id");
                createSession(ws);
                break;
            case 6:
                myJsonObj = new JSONObject(result.toString());
                session = myJsonObj.getString("id");
                subscribe(ws);
                break;
            case 7:
                subscribed = true;
                // This gets called the first time we receive data and subscribe
        }
    }
    @Override
    public void requestAccess(WebSocketClient ws) {
        System.out.println("requestAccess: done!");
        JSONObject message = new JSONObject();
        message.put("id", 2);
        message.put("jsonrpc", "2.0");
        message.put("method", "requestAccess");
        JSONObject params = new JSONObject();
        params.put("clientId", clientID);
        params.put("clientSecret", clientSecret);
        message.put("params", params);
        ws.send(message.toString());
    }

    @Override
    public void authorize(WebSocketClient ws) {
        System.out.println("authorize: done!");
        JSONObject message = new JSONObject();
        message.put("id", 3);
        message.put("jsonrpc", "2.0");
        message.put("method", "authorize");
        JSONObject params = new JSONObject();
        params.put("clientId", clientID);
        params.put("clientSecret", clientSecret);
        params.put("debit", 1);
        message.put("params", params);
        ws.send(message.toString());
    }

    public void toggleEngagement(LineChart<Number, Number> plot) {
        if (plot.getData().contains(engagementSeries)) {
            plot.getData().remove(engagementSeries);
        } else {
            plot.getData().add(engagementSeries);
        }
    };

    public void toggleExcitement(LineChart<Number, Number> plot) {
        if (plot.getData().contains(excitementSeries)) {
            plot.getData().remove(excitementSeries);
        } else {
            plot.getData().add(excitementSeries);
        }
    };

    public void toggleStress(LineChart<Number, Number> plot) {
        if (plot.getData().contains(stressSeries)) {
            plot.getData().remove(stressSeries);
        } else {
            plot.getData().add(stressSeries);
        }
    };

    public void toggleRelaxation(LineChart<Number, Number> plot) {
        if (plot.getData().contains(relaxationSeries)) {
            plot.getData().remove(relaxationSeries);
        } else {
            plot.getData().add(relaxationSeries);
        }
    };

    public void toggleInterest(LineChart<Number, Number> plot) {
        if (plot.getData().contains(interestSeries)) {
            plot.getData().remove(interestSeries);
        } else {
            plot.getData().add(interestSeries);
        }
    };

    public void toggleFocus(LineChart<Number, Number> plot) {
        if (plot.getData().contains(focusSeries)) {
            plot.getData().remove(focusSeries);
        } else {
            plot.getData().add(focusSeries);
        }
    };

    // Expects JSON Objects like:
    // {"time":1684333831.2766,"met":[true,0.791596,true,0.87237,0,true,0.830392,true,0.873179,true,0.841725,true,0.792859],"sid":"aa4890f1-ce53-4467-8c3f-fd5d5a4ede2e"}
    // https://emotiv.gitbook.io/cortex-api/data-subscription/data-sample-object
    // Comes in once every couple seconds (about 8 seconds)
    // met is in the following format:
    // [
    // "eng.isActive","eng",
    // "exc.isActive","exc","lex",
    // "str.isActive","str",
    // "rel.isActive","rel",
    // "int.isActive","int",
    // "foc.isActive","foc"
    // ]

    public void pad(JSONObject object, LineChart<Number, Number> plot) {
        int[] magic_indecies = new int[]{1, 3, 6, 8, 10, 12};
        double[][] met = new double[][]{{1, 1, 1}, {1, 1, -1}, {-1, 1, -1}, {1, -1, 1}, {1, 1, -1}, {1, -1, 1}};
        double[] norm = new double[]{-2, -1, 0};
        JSONArray arr = object.getJSONArray("met");
        for (int i = 0; i < magic_indecies.length; i++) {
            for (int j = 0; j < 3; j++) {
                norm[j] = norm[j] + (met[i][j] * ((BigDecimal)(arr.get(magic_indecies[i]))).doubleValue());
            }
        }
        for (int i = 0; i < 3; i++) {
            norm[i] = norm[i] / 3; //graph here?
        }

        Platform.runLater(() -> {
            long current = Instant.now().getEpochSecond() - start;
            engagementSeries.getData().add(new XYChart.Data<>(current, ((BigDecimal)(arr.get(magic_indecies[0]))).doubleValue()));
            excitementSeries.getData().add(new XYChart.Data<>(current, ((BigDecimal)(arr.get(magic_indecies[1]))).doubleValue()));
            stressSeries.getData().add(new XYChart.Data<>(current, ((BigDecimal)(arr.get(magic_indecies[2]))).doubleValue()));
            relaxationSeries.getData().add(new XYChart.Data<>(current, ((BigDecimal)(arr.get(magic_indecies[3]))).doubleValue()));
            interestSeries.getData().add(new XYChart.Data<>(current, ((BigDecimal)(arr.get(magic_indecies[4]))).doubleValue()));
            focusSeries.getData().add(new XYChart.Data<>(current, ((BigDecimal)(arr.get(magic_indecies[5]))).doubleValue()));

            if (first) {
                plot.getData().add(engagementSeries);
                plot.getData().add(excitementSeries);
                plot.getData().add(stressSeries);
                plot.getData().add(relaxationSeries);
                plot.getData().add(interestSeries);
                plot.getData().add(focusSeries);
                first = false;
            }
        });
   }

}
