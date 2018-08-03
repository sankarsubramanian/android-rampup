package com.att.firstpriority.base;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import com.att.firstpriority.logger.Logger;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Logger logger = new Logger("error");
//        Logger logger = new Logger("null");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        logger.setLogLevel("verbose");

        logger.verbose(TAG, "***********VERBOSE PRINT*************");
        logger.debug(TAG, "***********DEBUG PRINT*************");
        logger.info(TAG, "***********INFO PRINT*************");
        logger.warn(TAG, "***********WARN PRINT*************");
        logger.error(TAG, "***********ERROR PRINT*************");

        try {
            throw new NullPointerException("demo");
        } catch (Exception e) {
            logger.debug(TAG, "***********DEBUG PRINT With Exception*************", e);
            logger.info(TAG, "***********INFO PRINT With Exception*************", e);
            logger.warn(TAG, "***********WARN PRINT With Exception*************", e);
            logger.error(TAG, "***********ERROR PRINT With Exception*************", e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
