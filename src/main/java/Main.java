import java.net.URI;
import java.io.IOException;
import java.awt.BorderLayout;
import java.io.File;
import java.awt.*;
import java.awt.event.*;
import javafx.util.Duration;
import javax.swing.*;
import javax.swing.JFrame;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.embed.swing.JFXPanel;
import javafx.util.Duration;

import java.io.File;

import javafx.geometry.Rectangle2D;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.layout.FlowPane;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.binding.Bindings;
import javafx.application.*;
// import weka.core.pmml.jaxbbindings.Application;

/**
 * Main class to run the Emotiv WebSocket client.
 *
 *  @author javiersgs
 *  @version 0.1
 */

public class Main extends Application {

    private static EyeTracker eyeTracker;
    private static Stage window;
    private static Scene engScene, excScene, focScene, strScene, fruScene;
    private static Duration dur;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    private void rewThirty(MediaPlayer player) {
        if (player.getCurrentTime().lessThan(dur)) {
            player.seek(Duration.ZERO);
        } else {
            player.seek(player.getCurrentTime().subtract(dur));
        }
    }

    public void start(Stage mainStage) throws Exception {

        window = mainStage;

        //Create the frame
        JFrame frame = new JFrame("DancEmote");
        frame.setSize(2000, 2000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dur = Duration.millis(30000);

        //Create the JFXPanel
        JFXPanel VFXPanel=new JFXPanel();
        File video_source = new File("videos/test.mp4");
        Media m=new Media(video_source.toURI().toString());
        MediaPlayer player=new MediaPlayer(m);
        MediaView viewer=new MediaView(player);
        FlowPane root=new FlowPane();
        Scene scene=new Scene(root);

        // center video position
        Rectangle2D screen=Screen.getPrimary().getVisualBounds();
        viewer.setX(0);//getWidth()-videoPanel
        viewer.setY(0);

        // resize video based on screen size
        DoubleProperty width=viewer.fitWidthProperty();
        DoubleProperty height=viewer.fitHeightProperty();
        width.bind(Bindings.selectDouble(viewer.sceneProperty(),"width"));
        height.bind(Bindings.selectDouble(viewer.sceneProperty(),"height"));
        viewer.setPreserveRatio(true);


        //Create the objects required to hold the data
        final NumberAxis timeAxis = new NumberAxis();
        final NumberAxis emotionAxis = new NumberAxis();
        emotionAxis.setForceZeroInRange(false);
        timeAxis.setLabel("Seconds since Video Start");
        LineChart<Number, Number> emotivPlot = new LineChart<>(timeAxis, emotionAxis);


        emotivPlot.setTitle("Emotion Data");
        emotivPlot.setAlternativeColumnFillVisible(false);
        emotivPlot.setAlternativeRowFillVisible(false);

        //Open the socket and connect to it
        DanceDelegate delegate = new DanceDelegate();
        URI uri = new URI("wss://localhost:6868");
        DanceSocket ws = new DanceSocket(uri, delegate, emotivPlot);
        ws.connect();

        // Create buttons to go to engagement

        Button engB = new Button("Toggle\nEngagement\n(Off)");
        engB.setOnAction(e -> delegate.toggleEngagement(emotivPlot, engB));

        Button excB = new Button("Toggle\nExcitement\n(Off)");
        excB.setOnAction(e -> delegate.toggleExcitement(emotivPlot, excB));

        Button strB = new Button("Toggle\nStress\n(Off)");
        strB.setOnAction(e -> delegate.toggleStress(emotivPlot, strB));

        Button focB = new Button("Toggle\nFocus\n(Off)");
        focB.setOnAction(e -> delegate.toggleFocus(emotivPlot, focB));

        Button intB = new Button("Toggle\nInterest\n(Off)");
        intB.setOnAction(e -> delegate.toggleInterest(emotivPlot, intB));

        Button relB = new Button("Toggle\nRelaxation\n(Off)");
        relB.setOnAction(e -> delegate.toggleRelaxation(emotivPlot, relB));

        Button backThirty = new Button("Rew. 30s");
        backThirty.setOnAction(e -> rewThirty(player));

        delegate.toggleEngagement(emotivPlot, engB);
        delegate.toggleExcitement(emotivPlot, excB);
        delegate.toggleStress(emotivPlot, strB);
        delegate.toggleFocus(emotivPlot, focB);
        delegate.toggleInterest(emotivPlot, intB);
        delegate.toggleRelaxation(emotivPlot, relB);

        // add video to stackpane
        StackPane video = new StackPane();
        video.getChildren().add(viewer);
        root.getChildren().add(video);
        root.getChildren().add(emotivPlot);
        root.getChildren().add(engB);
        root.getChildren().add(excB);
        root.getChildren().add(strB);
        root.getChildren().add(focB);
        root.getChildren().add(intB);
        root.getChildren().add(relB);
        root.getChildren().add(backThirty);

        VFXPanel.setScene(scene);
        player.play();
        frame.add(VFXPanel, BorderLayout.NORTH);  // add the panel to the frame



        window.setScene(scene);
        window.setTitle("TeleCoach");
        window.show();

        // StrFocB.setOnAction(e -> window.setScene(focScene));

        //Terminate fetching emotiv data when the video stops
        player.setOnEndOfMedia(() -> {
            ws.close();
        });
    }


    //Code for initializing the eye tracker.
    //Not included as the eye tracking algorithm performs poorly
    private static void initEyeTracker(StackPane overlayPane) {
        // Create an instance of the EyeTracker class
        eyeTracker = new EyeTracker();

        // Run the eye tracker in a separate thread
        Thread eyeTrackerThread = new Thread(() -> {
            try {
                eyeTracker.trackEyeGaze(overlayPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        eyeTrackerThread.start();
    }
}