package com.example.trailxplorer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import java.io.File;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

// This class exists for the sole purpose of creating a .gpx file and writing in it
public class GPXHelper extends AppCompatActivity{

    Context context;

    public GPXHelper(Context context)
    {
       this.context = context;
    }

    // Create a new directoy called "GPStracks" in the external directory
    // If it does not exists to store .gpx files in it
    // The file is accessible to everyone

    public File newDirectory(String directoryName)
    {
        String gpsFolder = directoryName;
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), gpsFolder);
        if(!file.exists())
        {
            file.mkdirs();
        }
        return file;
    }

    // Create a new .gpx file with the appropriate name, header, and other specifications
    public File newFile(File f, String fileName)
    {
        try {
            String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?><gpx xmlns=\"http://www.topografix.com/GPX/1/1\" creator=\"MapSource 6.15.5\" " +
                    "version=\"1.1\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"  xsi:schemaLocation=\"http://www.topografix.com/GPX/1/1 " +
                    "http://www.topografix.com/GPX/1/1/gpx.xsd\"><trk>\n";
            String name = "<name>" + "TrackPoints" + "</name><trkseg>\n";
            File gpxFile = new File(f, fileName + ".gpx");
            FileWriter writer = new FileWriter(gpxFile, true);
            writer.append(header);
            writer.append(name);
            writer.flush();
            writer.close();
            Toast.makeText(context, ".gpx file saved at " + f.getPath(), Toast.LENGTH_SHORT).show();
            return gpxFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Add a trackpoint to the .gpx file with its latitude, longitude, altitude and time
    public void writeInFile(File f, Location l)
    {
        try {
            File p = f;
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            FileWriter writer = new FileWriter(p, true);
            writer.append("<trkpt lat=\"" + l.getLatitude() + "\" lon=\"" + l.getLongitude() + "\" alt=\"" + l.getAltitude() + "\" ><time>" + df.format(new Date(l.getTime())) + "</time></trkpt>\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Close the file by writing the necessary elements at the end
    public void closeFile(File f)
    {
        try {
            File p = f;
            String close = "</trkseg></trk></gpx>";
            FileWriter writer = new FileWriter(p, true);
            writer.append(close);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
