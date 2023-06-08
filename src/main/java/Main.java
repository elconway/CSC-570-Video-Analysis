import java.net.URI;
import java.io.IOException;
import java.awt.BorderLayout;
import java.io.File;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JFrame;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.embed.swing.JFXPanel;

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

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void start(Stage mainStage) throws Exception {

        window = mainStage;

        JFrame frame = new JFrame("DancEmote");
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JFXPanel VFXPanel=new JFXPanel();
        File video_source = new File("videos/test.mp4");
        Media m=new Media(video_source.toURI().toString());
        MediaPlayer player=new MediaPlayer(m);
        MediaView viewer=new MediaView(player);
        FlowPane root=new FlowPane();
        Scene scene=new Scene(root);
        // center video position
        Rectangle2D screen=Screen.getPrimary().getVisualBounds();
        viewer.setX(0);
        viewer.setY(0);
        // resize video based on screen size
        DoubleProperty width=viewer.fitWidthProperty();
        DoubleProperty height=viewer.fitHeightProperty();
        width.bind(Bindings.selectDouble(viewer.sceneProperty(),"width"));
        height.bind(Bindings.selectDouble(viewer.sceneProperty(),"height"));
        viewer.setPreserveRatio(true);


        final NumberAxis timeAxis = new NumberAxis();
        final NumberAxis emotionAxis = new NumberAxis();
        emotionAxis.setForceZeroInRange(false);
        timeAxis.setLabel("Seconds since Video Start");
        LineChart<Number, Number> emotivPlot = new LineChart<>(timeAxis, emotionAxis);

        emotivPlot.setTitle("Emotion Data");
        emotivPlot.setAlternativeColumnFillVisible(false);
        emotivPlot.setAlternativeRowFillVisible(false);


        DanceDelegate delegate = new DanceDelegate();
        URI uri = new URI("wss://localhost:6868");
        DanceSocket ws = new DanceSocket(uri, delegate, emotivPlot);
        ws.connect();

        // Create buttons to go to engagement

        Label engL = new Label("Engagement");
        Button engB = new Button("Engagement");
        engB.setOnAction(e -> delegate.toggleEngagement(emotivPlot));

        Label excL = new Label("Excitement");
        Button excB = new Button("Excitement");
        excB.setOnAction(e -> delegate.toggleExcitement(emotivPlot));

        Label strL = new Label("Stress");
        Button strB = new Button("Stress");
        strB.setOnAction(e -> delegate.toggleStress(emotivPlot));

        Label focL = new Label("Focus");
        Button focB = new Button("Focus");
        focB.setOnAction(e -> delegate.toggleFocus(emotivPlot));

        Label intL = new Label("Interest");
        Button intB = new Button("Interest");
        intB.setOnAction(e -> delegate.toggleInterest(emotivPlot));

        Label relL = new Label("Relaxation");
        Button relB = new Button("Relaxation");
        relB.setOnAction(e -> delegate.toggleRelaxation(emotivPlot));

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
        VFXPanel.setScene(scene);
        player.play();
//        initEyeTracker(video);
        frame.add(VFXPanel, BorderLayout.NORTH);  // add the panel to the frame
//        player.pause();


        window.setScene(scene);
        window.setTitle("TeleCoach");
        window.show();

        // StrFocB.setOnAction(e -> window.setScene(focScene));

        player.setOnEndOfMedia(() -> {
            ws.close();
        });
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