import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.core.Rect;
import org.opencv.core.MatOfRect;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.application.Platform;

import java.io.IOException;

//This class is used to track the eye gaze and print a red dot at the target location

public class EyeTracker {

    private static final String CASCADE_CLASSIFIER_FILE = "src/main/java/haarcascade_eye.xml";
    private static final int FRAME_WIDTH = 1920;
    private static final int FRAME_HEIGHT = 1080;
    private static int first = 0;

    private static CascadeClassifier eyeCascade;

    public static void trackEyeGaze(StackPane overlayPane) throws IOException {
        nu.pattern.OpenCV.loadShared();

        eyeCascade = new CascadeClassifier();
        if (!eyeCascade.load(CASCADE_CLASSIFIER_FILE)) {
            System.out.println("Error loading eye cascade classifier.");
            return;
        }

        VideoCapture capture = new VideoCapture(0);
        if (!capture.isOpened()) {
            System.out.println("Error opening webcam.");
            return;
        }

        capture.set(3, FRAME_WIDTH);
        capture.set(4, FRAME_HEIGHT);

        Mat frame = new Mat();
        Mat grayFrame = new Mat();
        MatOfRect eyes = new MatOfRect();


        while (true) {
            try {
            if (capture.read(frame)) {
                Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
                Imgproc.equalizeHist(grayFrame, grayFrame);

                // Detect eyes
                eyeCascade.detectMultiScale(grayFrame, eyes, 1.01, 3, Objdetect.CASCADE_SCALE_IMAGE,
                        new Size(10, 10), new Size());

                Rect[] eyesArray = eyes.toArray();
                for (Rect eye : eyesArray) {
                    // Calculate gaze coordinates
                    int eyeCenterX = eye.x + eye.width / 2;
                    int eyeCenterY = eye.y + eye.height / 2;

                    double gazeX = eyeCenterX / (double) FRAME_WIDTH;
                    double gazeY = eyeCenterY / (double) FRAME_HEIGHT;

                    // Display gaze coordinates
                    System.out.println("Gaze X: " + gazeX);
                    System.out.println("Gaze Y: " + gazeY);

                    Circle gazeDot = new Circle(5, Color.RED);

                    // Draw circle at gaze coordinates
                    Platform.runLater(() -> {
                        gazeDot.setTranslateX(gazeX * overlayPane.getWidth());
                        gazeDot.setTranslateY(gazeY * overlayPane.getHeight());
//                        overlayPane.getChildren().(gazeDot);
                        overlayPane.getChildren().add(gazeDot);
                    });
                }

                // Display the resulting frame
//                Imgcodecs.imwrite("output.jpg", frame);

                // Exit the loop if the 'Q' key is pressed
                try {
                    if (System.in.available() > 0 && System.in.read() == 'q') {
                        break;
                    }
                } catch (IOException e) {
                    // Handle the exception here, such as logging an error message
                    e.printStackTrace();
                }
            } } catch (Exception e) {
                System.out.println("Exception occurred while capturing frame: " + e.getMessage());
                e.printStackTrace();
                break;
            }
        }

        capture.release();
    }
}
