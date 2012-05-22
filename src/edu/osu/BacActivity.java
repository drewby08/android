package edu.osu;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

public class BacActivity extends Activity{
	
	private TextView mTimeDisplay;
    private Button mPickTime;

    private int mHour;
    private int mMinute;
    private long milli;
    private long seconds;

    static final int TIME_DIALOG_ID = 0;
    
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bac);
             
        //--------------------Drinking Start Time---------------------
        mTimeDisplay = (TextView) findViewById(R.id.timeDisplay);
        mPickTime = (Button) findViewById(R.id.pickTime);
        
        mPickTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });
        
        // get the current time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        milli = c.getTimeInMillis();
        seconds = TimeUnit.MILLISECONDS.toSeconds(milli);
        
        
        // display the current date
        updateDisplay();
        //----------------------------------------------------------------
        
        //---------------------Current Drink Table------------------------
        
        //Moves current drink values from Tab Tracker to the BAC Tab
        final TextView totalBeer = (TextView) findViewById(R.id.bac_total_beer);
        totalBeer.setText(Integer.toString(BarTab.beer));
        final TextView totalWell = (TextView) findViewById(R.id.bac_total_well);
        totalWell.setText(Integer.toString(BarTab.well));
        final TextView totalLiquor = (TextView) findViewById(R.id.bac_total_liquor);
        totalLiquor.setText(Integer.toString(BarTab.liquor));
        final TextView totalBombs = (TextView) findViewById(R.id.bac_total_bombs);
        totalBombs.setText(Integer.toString(BarTab.bombs));
        final TextView totalCocktails = (TextView) findViewById(R.id.bac_total_cocktails);
        totalCocktails.setText(Integer.toString(BarTab.cocktail));
        
        //------------------------------------------------------------------
        
        //----------------------Calculate BAC--------------------------------
        final Button bacCalc = (Button) findViewById(R.id.bac_calculate);
        final TextView bac = (TextView) findViewById(R.id.bac_bloodAlcoholContent);
        
        
        bacCalc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Gather Variables
            	RadioGroup radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
                int selectedId = radioSexGroup.getCheckedRadioButtonId();
                RadioButton radioSex = (RadioButton) findViewById(selectedId);
                
                final Calendar c = Calendar.getInstance();
                int currentHour = c.get(Calendar.HOUR_OF_DAY);
                int currentMinute = c.get(Calendar.MINUTE);
                int hourDiff = (currentHour - mHour );
                int minDiff = (currentMinute - mMinute);
                int totalDiff = (minDiff + (60 * hourDiff));
                
                EditText temp = (EditText) findViewById(R.id.bacWeight);
                String tempString = temp.getText().toString();
                int weight = Integer.parseInt(tempString);
                
                TextView bacView = (TextView) findViewById(R.id.bac_bloodAlcoholContent);
                
                //Algorithm used for men
                if(radioSex.getText().toString().equalsIgnoreCase("male")){
                	//Calculate BAC based on alcohol and weight, using .02% per drink
                	//Cocktails will count as 2 drinks, wells as .75 of a drink
                	
                	//Find proportionate weight factor based on .02% for a 200lb man
                	double weightFactor = (200/(double)weight);
                	double bac = 0.0;
                	bac = bac + ((double)BarTab.beer * (.02 * weightFactor));
                	bac = bac + ((double)BarTab.well * (.02*.75* weightFactor));
                	bac = bac + ((double)BarTab.liquor * (.02 * weightFactor));
                	bac = bac + ((double)BarTab.bombs * (.02 * weightFactor));
                	bac = bac + ((double)BarTab.cocktail * (.04 * weightFactor));
                	
                	//Finds the amount bac has decreased during drinking
                	//Subtracts .005% per 20 mins
//                	int soberingFactorHour = (diffInSec / (long)1200);
//                	double subtraction = (.02 * soberingFactor);
//                	
//                	bac = bac - subtraction;
//                	updateBac(bacView, (double) diffInSec);
                }
                //Algorithm used for women
                else{
                	
                }
            }
        });
    }
	
	// updates the time we display in the TextView
	private void updateDisplay() {
	    mTimeDisplay.setText(
	        new StringBuilder()
	                .append("Current Start Time -- ").append(pad(mHour)).append(":")
	                .append(pad(mMinute)));
	    
	    final TextView totalBeer = (TextView) findViewById(R.id.bac_total_beer);
        totalBeer.setText(Integer.toString(BarTab.beer));
        final TextView totalWell = (TextView) findViewById(R.id.bac_total_well);
        totalWell.setText(Integer.toString(BarTab.well));
        final TextView totalLiquor = (TextView) findViewById(R.id.bac_total_liquor);
        totalLiquor.setText(Integer.toString(BarTab.liquor));
        final TextView totalBombs = (TextView) findViewById(R.id.bac_total_bombs);
        totalBombs.setText(Integer.toString(BarTab.bombs));
        final TextView totalCocktails = (TextView) findViewById(R.id.bac_total_cocktails);
        totalCocktails.setText(Integer.toString(BarTab.cocktail));
	}
	
	private void updateBac(TextView t, double d){
		t.setText(
				new StringBuilder()
				.append("Current Estimated BAC: ")
				.append(Double.toString(d)));
	}
	
	private static String pad(int c) {
	    if (c >= 10)
	        return String.valueOf(c);
	    else
	        return "0" + String.valueOf(c);
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    case TIME_DIALOG_ID:
	        return new TimePickerDialog(this,
	                mTimeSetListener, mHour, mMinute, false);
	    }
	    return null;
	}
	
	// the callback received when the user "sets" the time in the dialog
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
        new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute; 
                updateDisplay();
            }
        };
	
        
    protected void onResume(){
    	super.onResume();
    	
    	final TextView totalBeer = (TextView) findViewById(R.id.bac_total_beer);
    	totalBeer.setText(Integer.toString(BarTab.beer));
        final TextView totalWell = (TextView) findViewById(R.id.bac_total_well);
        totalWell.setText(Integer.toString(BarTab.well));
        final TextView totalLiquor = (TextView) findViewById(R.id.bac_total_liquor);
        totalLiquor.setText(Integer.toString(BarTab.liquor));
        final TextView totalBombs = (TextView) findViewById(R.id.bac_total_bombs);
        totalBombs.setText(Integer.toString(BarTab.bombs));
        final TextView totalCocktails = (TextView) findViewById(R.id.bac_total_cocktails);
        totalCocktails.setText(Integer.toString(BarTab.cocktail));
    }
}
