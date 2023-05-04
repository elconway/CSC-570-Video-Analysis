def synthesize_data(filename1, filename2):
    f1 = open(filename1, "r")
    f2 = open(filename2, "r")
    eye_tracking = f1.readlines()[1:]
    emotion_data = f2.readlines()[1:]
    emotion_header = emotion_data[0][:-1] + ",X,Y\n"
    emotion_data = emotion_data[1:]
    f1.close()
    f2.close()

    # Parse eye tracking data, setting first value to comparable float
    for i in range(len(eye_tracking)):
        eye_tracking[i] = eye_tracking[i][:-1].split(",")
        eye_tracking[i][0] = float(eye_tracking[i][0]) / 1000

    # Parse emotiv data, setting first value to comparable float
    emotion_data[0] = emotion_data[0][:-1].split(",")
    emotion_data[0][0] = float(emotion_data[0][0])

    for i in range(1, len(emotion_data)):
        emotion_data[i] = emotion_data[i][:-1].split(",")
        emotion_data[i][0] = float(emotion_data[i][0])

        # If emotiv data is empty, fill in rows with data from rows above
        for j in range(1, len(emotion_data[i])):
            if emotion_data[i][j].strip() == "":
                emotion_data[i][j] = emotion_data[i - 1][j]

    i = 0
    for j in range(len(eye_tracking)):
        while i < len(emotion_data) and emotion_data[i][0] < eye_tracking[j][0]:
            emotion_data[i].append(eye_tracking[j][1])
            emotion_data[i].append(eye_tracking[j][2])
            i += 1
    for i in range(len(emotion_data)):
        emotion_data[i][0] = str(emotion_data[i][0])
        emotion_data[i] = ",".join(emotion_data[i])
    emotion_data = "\n".join(emotion_data)
    f3 = open("merged.csv", "w")
    f3.write(emotion_header)
    f3.write(emotion_data)
    f3.close()
