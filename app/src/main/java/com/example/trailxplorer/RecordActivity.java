package com.example.trailxplorer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.text.AttributedCharacterIterator;
import java.util.ArrayList;

public class RecordActivity extends AppCompatActivity {

    double maxAlt;
    double minAlt;
    String chronometer;
    String distance;
    String averageSpeed;
    ArrayList<Integer> speed;
    String steps;

    TextView maxAltitudeTxt;
    TextView minAltitudeTxt;
    TextView chronoTxt;
    TextView distanceTxt;
    TextView averageSpeedTxt;
    TextView stepsTxt;
    TextView speedTxt;
    TextView axisText;
    LineChartView lineChart;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_main);

        //Get every TextView in record_main
        maxAltitudeTxt = findViewById(R.id.record_max_altitude);
        minAltitudeTxt = findViewById(R.id.record_min_altitude);
        chronoTxt = findViewById(R.id.record_chronometer);
        distanceTxt = findViewById(R.id.distance);
        averageSpeedTxt = findViewById(R.id.AverageSpeed);
        stepsTxt = findViewById(R.id.stepNumber);
        speedTxt = findViewById(R.id.speed);
        axisText = findViewById(R.id.axis);
        lineChart = findViewById(R.id.linechart);

        //Add a title to the activity and enable the Home Button
        getSupportActionBar().setTitle("Trail Analytics");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get all details about the traveler's journey
        Intent i = getIntent();
        maxAlt = i.getDoubleExtra("Max Altitude", 0);
        minAlt = i.getDoubleExtra("Min Altitude", 0);
        chronometer = i.getStringExtra("Chronometer");
        distance =  i.getStringExtra("Distance");
        averageSpeed = i.getStringExtra("Average Speed");
        steps = i.getStringExtra("Number of Steps");
        speed = i.getIntegerArrayListExtra("Speed Through Time");

        //Put the details on the screen
        maxAltitudeTxt.setText("Max Altitude: " + maxAlt + " in WGS 84 reference ellipsoid");
        minAltitudeTxt.setText("Min Altitude: " + minAlt + " in WGS 84 reference ellipsoid");
        chronoTxt.setText("Time taken: " + chronometer + " min");
        distanceTxt.setText("Distance travelled: " + distance + " m");
        averageSpeedTxt.setText("Average Speed: " + averageSpeed + " k/h");
        stepsTxt.setText("Number of steps done: " + steps);

        // If the user has moved
        if (speed.size() > 0)
        {
            // Display the speeds in the graph
            float[] ok = new float[speed.size()];
            for (int k = 0; k < speed.size(); k++)
            {
                ok[k] = speed.get(k);
            }
            lineChart.setChartData(ok);
            lineChart.setVisibility(View.VISIBLE);
            axisText.setVisibility(View.VISIBLE);
        }

        else
        {
            // The user has not moved, the graph is not shown

            speedTxt.setText("No speed to show");
            float[] ok = new float[]{10};
            lineChart.setChartData(ok);
            lineChart.setVisibility(View.INVISIBLE);
            axisText.setVisibility(View.INVISIBLE);
        }
    }
}