import java.net.URI;
import java.io.IOException;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javafx.embed.swing.JFXPanel;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.beans.property.DoubleProperty;
import javafx.beans.binding.Bindings;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//
//import org.mp4parser.IsoFile;
//import org.mp4parser.PropertyBoxParserImpl;
//import org.mp4parser.SkipBox;
//import org.mp4parser.boxes.iso14496.part12.MovieBox;
//import org.mp4parser.boxes.iso14496.part12.TrackHeaderBox;
//import org.mp4parser.tools.Path;
//import org.mp4parser.test.tools.PathTest;

/**
 * Main class to run the Emotiv WebSocket client.
 *
 *  @author javiersgs
 *  @version 0.1
 */

public class Main {

    public static void main(String[] args) throws Exception {

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JFrame frame = new JFrame("DancEmote");
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        root.getChildren().add(viewer);
        VFXPanel.setScene(scene);
        // player.play();
        frame.add(VFXPanel, BorderLayout.NORTH);  // add the panel to the frame
        // player.pause();

        //JButton SubmitNewWord = new JButton("Submit word");
        //JPanel panel2 = new JPanel();
        //panel2.add(SubmitNewWord);
        //frame.add(panel2, BorderLayout.SOUTH);

        frame.setVisible(true);
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
}