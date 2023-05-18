import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.Rect;
import org.opencv.core.MatOfRect;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.CvType;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

import java.util.Date;
import java.io.IOException;

public class EyeTracker {

    private static final String CASCADE_CLASSIFIER_FILE = "./haarcascade_eye.xml";
    private static final int FRAME_WIDTH = 640;
    private static final int FRAME_HEIGHT = 480;

    private static CascadeClassifier eyeCascade;

    public static void trackEyeGaze() throws IOException {
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
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

        long startTime = System.currentTimeMillis();

        while (true) {
            if (capture.read(frame)) {
                Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
                Imgproc.equalizeHist(grayFrame, grayFrame);

                // Detect eyes
                eyeCascade.detectMultiScale(grayFrame, eyes, 1.1, 2, Objdetect.CASCADE_SCALE_IMAGE,
                        new Size(30, 30), new Size());

                Rect[] eyesArray = eyes.toArray();
                for (Rect eye : eyesArray) {
                    // Calculate gaze coordinates
                    int eyeCenterX = eye.x + eye.width / 2;
                    int eyeCenterY = eye.y + eye.height / 2;

                    // Display gaze coordinates
                    System.out.println("Epoch Time: " + (System.currentTimeMillis() - startTime));
                    System.out.println("Gaze X: " + eyeCenterX);
                    System.out.println("Gaze Y: " + eyeCenterY);

                    // Draw rectangle around the eyes
                    Imgproc.rectangle(frame, new Point(eye.x, eye.y), new Point(eye.x + eye.width, eye.y + eye.height),
                            new Scalar(0, 255, 0), 2);
                }

                // Display the resulting frame
                Imgcodecs.imwrite("output.jpg", frame);

                // Exit the loop if the 'Q' key is pressed
                try {
                    if (System.in.available() > 0 && System.in.read() == 'q') {
                        break;
                    }
                } catch (IOException e) {
                    // Handle the exception here, such as logging an error message
                    e.printStackTrace();
                }
            } else {
                System.out.println("Error capturing frame from webcam.");
                break;
            }
        }

        capture.release();
    }
}
