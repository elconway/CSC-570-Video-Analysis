import org.bytedeco.javacv.*;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;

import java.util.Date;

public class EyeTracker {
    public static void main(String[] args) throws FrameGrabber.Exception, InterruptedException {
        // Load the Haar cascade files for eye detection
        CascadeClassifier eyeCascade = new CascadeClassifier("haarcascade_eye.xml");

        // Create a frame grabber to capture video from the camera
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        grabber.start();

        // Create a canvas frame to display the video
        CanvasFrame canvasFrame = new CanvasFrame("Eye Gaze Tracker");
        canvasFrame.setCanvasSize(grabber.getImageWidth(), grabber.getImageHeight());

        // Create a frame to store the captured video frame
        Mat frame = new Mat();

        // Create variables to store the eye coordinates
        int eyeX = 0;
        int eyeY = 0;

        // Start tracking eye gaze
        while (true) {
            if (!canvasFrame.isVisible()) {
                break;
            }

            // Capture the current video frame
            frame = grabber.grab();

            // Detect eyes in the frame
            RectVector eyes = new RectVector();
            eyeCascade.detectMultiScale(frame, eyes);

            // Iterate over the detected eyes
            for (int i = 0; i < eyes.size(); i++) {
                Rect eyeRect = eyes.get(i);

                // Get the coordinates of the eye center
                eyeX = eyeRect.x() + eyeRect.width() / 2;
                eyeY = eyeRect.y() + eyeRect.height() / 2;

                // Draw a circle at the eye center
                CvPoint eyeCenter = new CvPoint(eyeX, eyeY);
                cvCircle(frame, eyeCenter, 5, CvScalar.RED, 2, CV_AA, 0);
            }

            // Display the video frame with eye gaze tracking
            canvasFrame.showImage(frame);

            // Get the current time in epoch seconds
            long epochSeconds = new Date().getTime() / 1000;

            // Print the eye gaze coordinates at the current time
            System.out.println("Time: " + epochSeconds + " | Eye X: " + eyeX + " | Eye Y: " + eyeY);

            Thread.sleep(100);
        }

        // Stop the frame grabber and close the canvas frame
        grabber.stop();
        grabber.close();
        canvasFrame.dispose();
    }
}