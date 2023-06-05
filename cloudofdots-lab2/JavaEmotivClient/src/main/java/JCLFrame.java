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


public class JCLFrame extends JFrame{

    // JPlayer
    private static JFXPanel VFXPanel;
    private static File video_source;
    private static Media m;
    private static MediaPlayer player;
    private static MediaView viewer;
    private static StackPane root;
    private static Scene scene;

    // JButton
    static JButton play, engagement, excitement, stress, relaxed, interest, focus;

    // JPanel
    static JPanel controlPanel, container;

    // Label to display text
    static JLabel label;

    public JCLFrame() {
        super();
        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        controlPanel = new JPanel(null);
        play = new JButton("Play");
        engagement = new JButton("Engagement");
        excitement = new JButton("Excitement");
        stress = new JButton("Stress");
        relaxed = new JButton("Relaxed");
        interest = new JButton("Interest");
        focus = new JButton("Focus");

        controlPanel.add(play);
        controlPanel.add(engagement);
        controlPanel.add(excitement);
        controlPanel.add(stress);
        controlPanel.add(relaxed);
        controlPanel.add(interest);
        controlPanel.add(focus);
        getVideo();
        //controlPanel.setLocation(100, 100);
        //controlPanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        container.add(Box.createRigidArea(new Dimension(0, 5)));
        container.add(controlPanel);
        this.add(container);
        this.setSize(1000, 1000);
    }

    public void pause() {
        this.player.pause();
    }

    private void getVideo(){
        VFXPanel=new JFXPanel();
        video_source=new File("C:\\Users\\Everett\\IdeaProjects\\CSC 570\\src\\CSC-570-Video-Analysis\\cloudofdots-lab2\\JavaEmotivClient\\src\\main\\java\\test.mp4");
        m=new Media(video_source.toURI().toString());
        player=new MediaPlayer(m);
        viewer=new MediaView(player);
        root=new StackPane();
        scene=new Scene(root);
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
        player.play();
        container.add(VFXPanel);
    }
}