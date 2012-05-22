package edu.osu;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TabTrackerActivity extends Activity{
	
	//shared pref
	public static final String PREFS_COUNT = "MyPrefsFile";
	
	//onCreate method
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bartab);
        
        //beer button
        final Button buttonBeer = (Button) findViewById(R.id.button_beer);
        final TextView totalBeer = (TextView) findViewById(R.id.total_beer);
        
        buttonBeer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	int num_beer = Integer.parseInt((String)totalBeer.getText());
            	num_beer++;
            	BarTab.beer=num_beer;
            	totalBeer.setText(Integer.toString(num_beer));
            }
        });
        
        //well button
        final Button buttonWell = (Button) findViewById(R.id.button_well);
        final TextView totalWell = (TextView) findViewById(R.id.total_well);
        
        buttonWell.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	int num_well = Integer.parseInt((String)totalWell.getText());
            	num_well++;
            	BarTab.well = num_well;
            	totalWell.setText(Integer.toString(num_well));
            }
        });
        
        //liquor button
        final Button buttonLiquor = (Button) findViewById(R.id.button_liquor);
        final TextView totalLiquor = (TextView) findViewById(R.id.total_liquor);
        
        buttonLiquor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	int num_liquor = Integer.parseInt((String)totalLiquor.getText());
            	num_liquor++;
            	BarTab.liquor = num_liquor;
            	totalLiquor.setText(Integer.toString(num_liquor));
            }
        });
        
        //bomb button
        final Button buttonBombs = (Button) findViewById(R.id.button_bombs);
        final TextView totalBombs = (TextView) findViewById(R.id.total_bombs);
        
        buttonBombs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	int num_bombs = Integer.parseInt((String)totalBombs.getText());
            	num_bombs++;
            	BarTab.bombs = num_bombs;
            	totalBombs.setText(Integer.toString(num_bombs));
            }
        });
        
        //cocktail button
        final Button buttonCocktail = (Button) findViewById(R.id.button_cocktail);
        final TextView totalCocktail = (TextView) findViewById(R.id.total_cocktail);
        
        buttonCocktail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	int num_cocktail = Integer.parseInt((String)totalCocktail.getText());
            	num_cocktail++;
            	BarTab.cocktail = num_cocktail;
            	totalCocktail.setText(Integer.toString(num_cocktail));
            }
        });
        
        //clear button
        final Button buttonClear = (Button) findViewById(R.id.button_clear);
        
        buttonClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	totalBeer.setText("0");
            	totalWell.setText("0");
            	totalLiquor.setText("0");
            	totalBombs.setText("0");
            	totalCocktail.setText("0");
            	
            	BarTab.beer = 0;
            	BarTab.well = 0;
            	BarTab.liquor = 0;
            	BarTab.bombs = 0;
            	BarTab.cocktail = 0;
            }
        });
        
    }
	//on pause.. on resume
	
	//on pause preserves the current drink count in a shared preference
	@Override
	protected void onPause()
	{
		super.onPause();
		
		SharedPreferences beerPrefs = getSharedPreferences(PREFS_COUNT,0);
		
		SharedPreferences.Editor ed = beerPrefs.edit();
		ed.putInt("beer", BarTab.beer);
		ed.putInt("well", BarTab.well);
		ed.putInt("liquor", BarTab.liquor);
		ed.putInt("bombs", BarTab.bombs);
		ed.putInt("cocktail", BarTab.cocktail);
		
		ed.commit();
	}
	
	//on Resume restores the previous drink count totals
	@Override
	protected void onResume()
	{
		super.onResume();
		
		SharedPreferences beerPrefs = getSharedPreferences(PREFS_COUNT,0);
		
		//find the text views
		final TextView totalBeer = (TextView) findViewById(R.id.total_beer);
		final TextView totalWell = (TextView) findViewById(R.id.total_well);
		final TextView totalLiquor = (TextView) findViewById(R.id.total_liquor);
		final TextView totalBombs = (TextView) findViewById(R.id.total_bombs);
		final TextView totalCocktail = (TextView) findViewById(R.id.total_cocktail);
		
		//get the previous drink counts
		int beerCount = beerPrefs.getInt("beer",0);
		int wellCount = beerPrefs.getInt("well",0);
		int liquorCount = beerPrefs.getInt("liquor",0);
		int bombsCount = beerPrefs.getInt("bombs",0);
		int cocktailCount = beerPrefs.getInt("cocktail",0);
		
		//reset the labels to the previous values
		totalBeer.setText(Integer.toString(beerCount));
    	totalWell.setText(Integer.toString(wellCount));
    	totalLiquor.setText(Integer.toString(liquorCount));
    	totalBombs.setText(Integer.toString(bombsCount));
    	totalCocktail.setText(Integer.toString(cocktailCount));
    	
    	
    	//update the BarTab fields
    	BarTab.beer = beerCount;
    	BarTab.well = wellCount;
    	BarTab.liquor = liquorCount;
    	BarTab.bombs = bombsCount;
    	BarTab.cocktail = cocktailCount;
    	
    	
		
				
	}
	
	
}
