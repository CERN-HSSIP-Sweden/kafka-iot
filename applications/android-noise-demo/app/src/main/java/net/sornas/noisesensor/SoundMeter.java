package net.sornas.noisesensor;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

/**
 * stulen från https://stackoverflow.com/questions/4777060/android-sample-microphone-without-recording-to-get-live-amplitude-level
 */

public class SoundMeter {

    private AudioRecord ar;
    private int minSize;

    public void start() {
        minSize = AudioRecord.getMinBufferSize(44100,
                AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
        ar = new AudioRecord(MediaRecorder.AudioSource.MIC,
                44100,AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,minSize);
        ar.startRecording();
    }

    public void stop() {
        if (ar != null)
            ar.stop();
    }

    public double getAmplitude() {
        short[] buffer = new short[minSize];
        ar.read(buffer, 0, minSize);
        int max = 0;
        for (short s: buffer)
            if (Math.abs(s) > max)
                max = Math.abs(s);
        return max;
    }

}
