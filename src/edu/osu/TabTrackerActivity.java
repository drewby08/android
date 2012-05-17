package edu.osu;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class TabTrackerActivity extends Activity{
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bartab);
             
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
	
	
}
