# Assignment 3 Gaze + EEG.Affect
Package for CSC 570 at Cal Poly SLO
Group Members: Jordan Chew, Lee Conway, and Chandradeep Chowdhury

## python
The python directory holds the code based off of https://github.com/Emotiv/cortex-v2-example/tree/master/python  
Run it with `python3 records.py`, but you need to update your client and secret

## mergescript.py
Script to merge the emotiv data with the eye tracking data. Currently the datasets are hardcoded

## datasets
Datasets are stored in here, merged\_dataset.csv is the final combined dataset

## GazeAnalysis.html
Open using Chrome browser, then click "allow" in order to activate the WebGazer.js tool. 
In order to display standard YouTube video at full screen below the cells, copy either the full URL or the video id into the box in the upper righthand corner, then click the "Submit" button.
Whether using the video feature or the cell view, click the "Create Download" button in the bottom righthand corner of the table to generate the eye tracking data file.
Then, click the "Download File" link just to the left in order to download the data in CSV file format.
