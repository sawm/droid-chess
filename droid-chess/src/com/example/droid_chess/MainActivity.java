package com.example.droid_chess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void localGame(View view) {
    	//Open up the window for the game activity
    	Intent intent = new Intent(this,GameActivity.class);
    	startActivity(intent);
    }
        
    public void networkGame(View view) {
    	//Open up the window for the network game activity
    	Intent intent = new Intent(this,NetGameSetup.class);
    	startActivity(intent);    	
    }
    
    public void instruction(View view) {
    	//Open up the window for the instructions
    	Intent intent = new Intent(this,Instructions.class);
    	startActivity(intent);
    }
}
