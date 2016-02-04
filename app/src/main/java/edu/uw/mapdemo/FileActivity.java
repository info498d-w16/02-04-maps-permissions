package edu.uw.mapdemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class FileActivity extends AppCompatActivity {

    private static final String TAG = "** FILE DEMO **";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
    }


    //external button
    public void handleSaveFile(View v){
        Log.v(TAG, "Save button clicked");

        if(isExternalStorageWritable()){

            try {
                File dir; //where to save stuff
                //public external
                dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                //dir = Environment.getExternalStorageDirectory() //private external
                //dir = getFilesDir(); //internal file storage
                //dir = getCacheDir(); //internal cache
                //dir = getExternalCacheDir(); //external cache

                File file = new File(dir, "myFile.txt");

                FileOutputStream outputStream = new FileOutputStream(file);

                //outputStream = openFileOutput("myFile.txt", MODE_PRIVATE); //internal storage


                String message = "Hello file!";

                outputStream.write(message.getBytes());

                outputStream.close();
                Log.v(TAG, "File written");
            }
            catch(IOException ioe){
                ioe.printStackTrace();
            }



        }
    }


    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


    //internal button
    public void handleReadFile(View v){
        Log.v(TAG, "Read button clicked");

        if(isExternalStorageWritable()){

            try {
                File dir; //where to save stuff
                //public external
                dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

                File file = new File(dir, "myFile.txt");

                BufferedReader reader = new BufferedReader(new FileReader(file));

                String text = reader.readLine();

                Log.v(TAG, text);
            }
            catch(IOException ioe){
                ioe.printStackTrace();
            }



        }


    }


    //share button
    public void handleShareFile(View v){
        Log.v(TAG, "Share button clicked");

        Uri fileUri;

        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(dir, "myFile.txt");

        fileUri = Uri.fromFile(file);
        Log.v(TAG, "File is at: "+fileUri);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri);

        Intent chooser = Intent.createChooser(shareIntent, "Share this file");
        //could check the resolver
        startActivity(chooser);

    }


}
