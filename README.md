# Lab 2 #

## Setup Instructions ##
Install gradle: https://gradle.org/install/  
Once you've installed gradle, set up the clientID and clientSecret in DanceDelegate  
Run the project by doing
```shell
./gradlew run
```
gradlew is the wrapper script  
build.gradle.kts is the build file which is written in Kotlin, but for our purposes it's basically just a dependency manager  
Gradle expects all java files to be in src/java  
Initial builds of gradle will be slow, but they become faster  
<<<<<<< HEAD
=======

This project uses version 2.2.0 of [jzy3d](https://github.com/jzy3d/jzy3d-api)

## Comments

We have a working eye tracker and websocket for accessing the eye gaze data and the emotiv data in real time. 
We also have a working library for plotting the 3d vectors, however we were unable to complete this part of the lab
as we spent a lot of time finding a suitable library and managing the dependency.

