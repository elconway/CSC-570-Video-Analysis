import java.net.URI;
import java.io.IOException;
import java.awt.BorderLayout;
import java.io.File;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JFrame;
// import javax.swing.JButton;
// import javax.swing.JLabel;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.layout.StackPane;
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

    public void start(Stage mainStage) {

        window = mainStage;

        // Create buttons to go to engagement

        Label engL = new Label("Engagement");
        Button engB = new Button("Engagement");
        engB.setLayoutX(100);

        Label ExcEngL = new Label("ExcEngagement");
        Button ExcEngB = new Button("Engagement");
        ExcEngB.setOnAction(e -> window.setScene(engScene));
        engB.setLayoutX(100);

        Label FocEngL = new Label("FocEngagement");
        Button FocEngB = new Button("Engagement");
        FocEngB.setOnAction(e -> window.setScene(engScene));
        engB.setLayoutX(100);

        Label StrEngL = new Label("StrEngagement");
        Button StrEngB = new Button("Engagement");
        StrEngB.setOnAction(e -> window.setScene(engScene));
        engB.setLayoutX(100);

        Label FruEngL = new Label("FruEngagement");
        Button FruEngB = new Button("Engagement");
        FruEngB.setOnAction(e -> window.setScene(engScene));
        engB.setLayoutX(100);

        // Create buttons to go to excitement

        Label EngExcL = new Label("EngExcitement");
        Button EngExcB = new Button("Excitement");
        EngExcB.setOnAction(e -> window.setScene(excScene));
        EngExcB.setLayoutX(300);

        Label excL = new Label("Excitement");
        Button excB = new Button("Excitement");
        excB.setLayoutX(300);

        Label FocExcL = new Label("FocExcitement");
        Button FocExcB = new Button("Excitement");
        FocExcB.setOnAction(e -> window.setScene(excScene));
        FocExcB.setLayoutX(300);

        Label StrExcL = new Label("StrExcitement");
        Button StrExcB = new Button("Excitement");
        StrExcB.setOnAction(e -> window.setScene(excScene));
        StrExcB.setLayoutX(300);

        Label FruExcL = new Label("FruExcitement");
        Button FruExcB = new Button("Excitement");
        FruExcB.setOnAction(e -> window.setScene(excScene));
        FruExcB.setLayoutX(300);

        // Create buttons to go to focus

        Label EngFocL = new Label("EngFocus");
        Button EngFocB = new Button("Focus");
        EngFocB.setOnAction(e -> window.setScene(focScene));
        EngFocB.setLayoutX(500);

        Label ExcFocL = new Label("ExcFocus");
        Button ExcFocB = new Button("Focus");
        ExcFocB.setOnAction(e -> window.setScene(focScene));
        ExcFocB.setLayoutX(500);

        Label focL = new Label("Focus");
        Button focB = new Button("Focus");
        focB.setLayoutX(500);

        Label StrFocL = new Label("StrFocus");
        Button StrFocB = new Button("Focus");
        StrFocB.setOnAction(e -> window.setScene(focScene));
        StrFocB.setLayoutX(500);

        Label FruFocL = new Label("FruFocus");
        Button FruFocB = new Button("Focus");
        FruFocB.setOnAction(e -> window.setScene(focScene));
        FruFocB.setLayoutX(500);

        // Create buttons to go to stress

        Label EngStrL = new Label("EngStress");
        Button EngStrB = new Button("Stress");
        EngStrB.setOnAction(e -> window.setScene(strScene));
        EngStrB.setLayoutX(700);

        Label ExcStrL = new Label("ExcStress");
        Button ExcStrB = new Button("Stress");
        ExcStrB.setOnAction(e -> window.setScene(strScene));
        ExcStrB.setLayoutX(700);

        Label FocStrL = new Label("FocStress");
        Button FocStrB = new Button("Stress");
        FocStrB.setOnAction(e -> window.setScene(strScene));
        FocStrB.setLayoutX(700);

        Label strL = new Label("Stress");
        Button strB = new Button("Stress");
        strB.setLayoutX(700);

        Label FruStrL = new Label("FruStress");
        Button FruStrB = new Button("Stress");
        FruStrB.setOnAction(e -> window.setScene(strScene));
        FruStrB.setLayoutX(700);

        // Create buttons to go to frustration

        Label EngFruL = new Label("EngSFrustration");
        Button EngFruB = new Button("Frustration");
        EngFruB.setOnAction(e -> window.setScene(fruScene));
        EngFruB.setLayoutX(900);

        Label ExcFruL = new Label("ExcFrustration");
        Button ExcFruB = new Button("Frustration");
        ExcFruB.setOnAction(e -> window.setScene(fruScene));
        ExcFruB.setLayoutX(900);

        Label FocFruL = new Label("FocFrustration");
        Button FocFruB = new Button("Frustration");
        FocFruB.setOnAction(e -> window.setScene(fruScene));
        FocFruB.setLayoutX(900);

        Label StrFruL = new Label("StrFrustration");
        Button StrFruB = new Button("Frustration");
        StrFruB.setOnAction(e -> window.setScene(fruScene));
        StrFruB.setLayoutX(900);

        Label fruL = new Label("Frustration");
        Button fruB = new Button("Frustration");
        fruB.setLayoutX(900);

        StackPane engLay = new StackPane();
        engLay.getChildren().addAll(engB, engL);
        engLay.getChildren().addAll(EngExcB, EngExcL);
        engLay.getChildren().addAll(EngFocB, EngFocL);
        engLay.getChildren().addAll(EngStrB, EngStrL);
        engLay.getChildren().addAll(EngFruB, EngFruL);
        engScene = new Scene(engLay, 1000, 1000);

        StackPane excLay = new StackPane();
        excLay.getChildren().addAll(ExcEngB, ExcEngL);
        excLay.getChildren().addAll(excB, excL);
        excLay.getChildren().addAll(ExcFocB, ExcFocL);
        excLay.getChildren().addAll(ExcStrB, ExcStrL);
        excLay.getChildren().addAll(ExcFruB, ExcFruL);
        excScene = new Scene(excLay, 1000, 1000);

        StackPane focLay = new StackPane();
        focLay.getChildren().addAll(FocEngB, FocEngL);
        focLay.getChildren().addAll(StrExcB, FocExcL);
        focLay.getChildren().addAll(focB, focL);
        focLay.getChildren().addAll(FocStrB, FocStrL);
        focLay.getChildren().addAll(FocFruB, FocFruL);
        focScene = new Scene(focLay, 1000, 1000);

        StackPane strLay = new StackPane();
        strLay.getChildren().addAll(StrEngB, StrEngL);
        strLay.getChildren().addAll(StrExcB, StrExcL);
        strLay.getChildren().addAll(StrFocB, StrFocL);
        strLay.getChildren().addAll(strB, strL);
        strLay.getChildren().addAll(StrFruB, StrFruL);
        strScene = new Scene(strLay, 1000, 1000);

        StackPane fruLay = new StackPane();
        fruLay.getChildren().addAll(FruEngB, FruEngL);
        fruLay.getChildren().addAll(FruExcB, FruExcL);
        fruLay.getChildren().addAll(FruFocB, FruFocL);
        fruLay.getChildren().addAll(FruStrB, FruStrL);
        fruLay.getChildren().addAll(fruB, fruL);
        fruScene = new Scene(fruLay, 1000, 1000);

        // JPanel container = new JPanel();
        // container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JFrame frame = new JFrame("DancEmote");
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // TODO: Re-add
        JFXPanel VFXPanel=new JFXPanel();
        //File video_source=new File("C:\\Users\\Everett\\IdeaProjects\\CSC 570\\src\\CSC-570-Video-Analysis\\cloudofdots-lab2\\JavaEmotivClient\\src\\main\\java\\test.mp4");
        File video_source = new File("videos/test.mp4");
        Media m=new Media(video_source.toURI().toString());
        MediaPlayer player=new MediaPlayer(m);
        MediaView viewer=new MediaView(player);
        StackPane root=new StackPane();
        Scene scene=new Scene(root);
        // center video position
        javafx.geometry.Rectangle2D screen=Screen.getPrimary().getVisualBounds();
        viewer.setX(0);//getWidth()-videoPanel
        viewer.setY(0);
        // resize video based on screen size
        DoubleProperty width=viewer.fitWidthProperty();
        DoubleProperty height=viewer.fitHeightProperty();
        width.bind(Bindings.selectDouble(viewer.sceneProperty(),"width"));
        height.bind(Bindings.selectDouble(viewer.sceneProperty(),"height"));
        viewer.setPreserveRatio(true);



        // add video to stackpane
        // TODO: Re-add
        root.getChildren().add(viewer);
        StackPane overlayPane = new StackPane();
        root.getChildren().add(overlayPane);
        VFXPanel.setScene(scene);
        player.play();
        initEyeTracker(overlayPane);
        frame.add(VFXPanel, BorderLayout.NORTH);  // add the panel to the frame
        player.pause();


        window.setScene(engScene);
        window.setTitle("DancEmote");
        window.show();


        /* Create scene for excitement
        Label excL = new Label("Excitement");
        Button excB = new Button("Excitement");
        excB.setOnAction(e -> window.setScene(excScene));

        // Create scene for focus
        Label focL = new Label("Focus");
        Button focB = new Button("Focus");
        focB.setOnAction(e -> window.setScene(focScene));

        // Create scene for stress
        Label strL = new Label("Stress");
        Button strB = new Button("Stress");
        strB.setOnAction(e -> window.setScene(strScene));

        // Create scene for frustration
        Label fruL = new Label("Frustration");
        Button fruB = new Button("Frustration");
        fruB.setOnAction(e -> window.setScene(fruScene));*/

        //JButton SubmitNewWord = new JButton("Submit word");
        //JPanel panel2 = new JPanel();
        //panel2.add(SubmitNewWord);
        //frame.add(panel2, BorderLayout.SOUTH);

        // frame.setVisible(true);
//        FileInputStream fis = new FileInputStream("test.mp4");
//        isoFile = new IsoFile(fis.getChannel(), new PropertyBoxParserImpl().skippingBoxes("mdat", "mvhd"));
//        fis.close();
//        MovieBox movieBox = isoFile.getMovieBox();
//        DanceDelegate delegate = new DanceDelegate();
//        URI uri = new URI("wss://localhost:6868");
//        DanceSocket ws = new DanceSocket(uri, delegate);
//        ws.connect();
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
