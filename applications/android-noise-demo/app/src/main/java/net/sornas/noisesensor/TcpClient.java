package net.sornas.noisesensor;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class TcpClient {
    public static final String TAG = TcpClient.class.getSimpleName();
    public static final String SERVER_IP = "127.0.0.1"; //server IP address
    public static final int SERVER_PORT = 4444;
    // while this is true, the server will continue running
    private volatile boolean mRun = false;
    // used to send messages
    private PrintWriter mBufferOut;

    public TcpClient() {}

    void sendMessage(final String msg) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (mBufferOut != null) {
                    Log.d(TAG, "sending msg");
                    mBufferOut.write(msg + "\n");
                    mBufferOut.flush();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void stop() {
        mRun = false;

        if (mBufferOut != null) {
            mBufferOut.flush();
            mBufferOut.close();
        }

        mBufferOut = null;
    }

    public void run() {
        mRun = true;

        try {
            //here you must put your computer's IP address.
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

            Log.d("TCP Client", "C: Connecting...");

            //create a socket to make the connection with the server

            try (Socket socket = new Socket(serverAddr, SERVER_PORT)) {

                //sends the message to the server
                mBufferOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                //in this while the client listens for the messages sent by the server
                while (true) {
                    if (!mRun) break;
                }

            } catch (Exception e) {
                Log.e("TCP", "S: Error", e);
            }
            //the socket must be closed. It is not possible to reconnect to this socket
            // after it is closed, which means a new socket instance has to be created.

        } catch (Exception e) {
            Log.e("TCP", "C: Error: " + e.getMessage());
            for (StackTraceElement stackTraceElement: e.getStackTrace())
                Log.e("TCP", stackTraceElement.toString());
        }
    }
}
