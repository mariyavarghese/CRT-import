package com.example.crt;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ConnectedThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private final Handler mHandler;

    public ConnectedThread(BluetoothSocket socket, Handler handler) {
        mmSocket = socket;
        mHandler = handler;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) { }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1024];  // buffer store for the stream
        int bytes; // bytes returned from read()
        // Keep listening to the InputStream until an exception occurs
        Log.d("read","readtest");
        while (true) {
      //      Log.d("read","loop");
            try {
                // Read from the InputStream
                bytes = mmInStream.available();

                if(bytes != 0) {
                    Log.d("read","write");
                    buffer = new byte[1024];
                    SystemClock.sleep(100); //pause and wait for rest of data. Adjust this depending on your sending speed.
                    bytes = mmInStream.available(); // how many bytes are ready to be read?
                    bytes = mmInStream.read(buffer, 0, bytes); // record how many bytes we actually read

                    String respdata= TextUtil.toHexString(buffer);
                    Log.d("resp",respdata);

                    mHandler.obtainMessage(BluetoothActivity.MESSAGE_READ, bytes, -1, buffer)
                            .sendToTarget(); // Send the obtained bytes to the UI activity


                }
            } catch (IOException e) {
                e.printStackTrace();

                break;
            }
        }
    }

    /* Call this from the main activity to send data to the remote device */
    public void write(String input) {
       // Log.d("read",input);
        byte[] bytes ={70,77,66,88,-86,-86,-86,-86,0,46,0,2,0,1,14,-88,13,10};

        //{70,77,66,88,-86,-86,-86,-86,0,30,0,2,0,0,-54,41,13,10};
               // {70,77,66,88,-86,-86,-86,-86,0,46,0,2,0,1,14,-88,13,10};
                //input.getBytes();           //converts entered String into bytes
        try {
            mmOutStream.write(bytes);
        } catch (IOException e) { }
    }

    /* Call this from the main activity to shutdown the connection */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }
}
