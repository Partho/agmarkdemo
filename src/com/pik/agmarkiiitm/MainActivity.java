package com.pik.agmarkiiitm;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener, OnTouchListener, RecognitionListener {
	static {
		System.loadLibrary("pocketsphinx_jni");
	}
	/**
	 * Recognizer task, which runs in a worker thread.
	 */
	RecognizerTask rec1;
	RecognizerTask rec2;
	RecognizerTask rec3;
	
	/**
	 * Thread in which the recognizer task runs.
	 */
	Thread rec_thread_1;
	Thread rec_thread_2;
	Thread rec_thread_3;
	/**
	 * Time at which current recognition started.
	 */
	Date start_date;
	/**
	 * Number of seconds of speech.
	 */
	float speech_dur;
	/**
	 * Are we listening?
	 */
	boolean listening;
	/**
	 * Progress dialog for final recognition.
	 */
	ProgressDialog rec_dialog;
	/**
	 * Performance counter view.
	 */
	//TextView performance_text;
	/**
	 * Editable text view.
	 */
	
	EditText edit_commodity;
	EditText edit_variety;
	EditText edit_market;
	EditText fromDateEtxt;
	
    DatePickerDialog fromDatePickerDialog;
    SimpleDateFormat dateFormatter;
    
    Button submit;

    File f;
	
	
	
	/**
	 * Respond to touch events on the Speak button.
	 * 
	 * This allows the Speak button to function as a "push and hold" button, by
	 * triggering the start of recognition when it is first pushed, and the end
	 * of recognition when it is released.
	 * 
	 * @param v
	 *            View on which this event is called
	 * @param event
	 *            Event that was triggered.
	 */
	

	public boolean onTouch(View v, MotionEvent event) {
		switch(v.getId()){
        
        case R.id.ButtonCommodity:
            //do stuff for button 1
        	if(event.getAction() == MotionEvent.ACTION_DOWN )
        	{
        		start_date = new Date();
    			this.listening = true;
    			this.rec1.start();
        	}
        	else if(event.getAction() == MotionEvent.ACTION_UP)
        	{
        		Date end_date = new Date();
    			long nmsec = end_date.getTime() - start_date.getTime();
    			this.speech_dur = (float)nmsec / 1000;
    			if (this.listening) {
    				Log.d(getClass().getName(), "Showing Dialog");
    				this.rec_dialog = ProgressDialog.show(MainActivity.this, "", "Recognizing speech...", true);
    				this.rec_dialog.setCancelable(false);
    				this.listening = false;
    			}
    			this.rec1.stop();
        	}
            break;
            
            
        case R.id.ButtonVariety:
            //do stuff for button 2
        	if(event.getAction() == MotionEvent.ACTION_DOWN )
        	{
        		start_date = new Date();
    			this.listening = true;
    			this.rec2.start();
        	}
        	else if(event.getAction() == MotionEvent.ACTION_UP)
        	{
        		Date end_date = new Date();
    			long nmsec = end_date.getTime() - start_date.getTime();
    			this.speech_dur = (float)nmsec / 1000;
    			if (this.listening) {
    				Log.d(getClass().getName(), "Showing Dialog");
    				this.rec_dialog = ProgressDialog.show(MainActivity.this, "", "Recognizing speech...", true);
    				this.rec_dialog.setCancelable(false);
    				this.listening = false;
    			}
    			this.rec2.stop();
        	}
            break;
            
            
        case R.id.ButtonMarket:
            //do stuff for button 3
        	if(event.getAction() == MotionEvent.ACTION_DOWN )
        	{
        		start_date = new Date();
    			this.listening = true;
    			this.rec3.start();
        	}
        	else if(event.getAction() == MotionEvent.ACTION_UP)
        	{
        		Date end_date = new Date();
    			long nmsec = end_date.getTime() - start_date.getTime();
    			this.speech_dur = (float)nmsec / 1000;
    			if (this.listening) {
    				Log.d(getClass().getName(), "Showing Dialog");
    				this.rec_dialog = ProgressDialog.show(MainActivity.this, "", "Recognizing speech...", true);
    				this.rec_dialog.setCancelable(false);
    				this.listening = false;
    			}
    			this.rec3.stop();
        	}
            break;    
		default:
			;
		}
		// Let the button handle its own state 
		return false;
	}
	

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Drawable rightArrow = getResources().getDrawable(R.drawable.cornwall);

		// setting the opacity (alpha)
		//rightArrow.setAlpha(10);

		// setting the images on the ImageViews
		//rightImage.setImageDrawable(rightArrow);
		
		
		
		
		this.rec1 = new RecognizerTask(new File(getExternalFilesDir(null), "pocketsphinxmodels"));
		this.rec2 = new RecognizerTask(new File(getExternalFilesDir(null), "pocketsphinxmodels"));
		this.rec3 = new RecognizerTask(new File(getExternalFilesDir(null), "pocketsphinxmodels"));
		

		this.rec_thread_1 = new Thread(this.rec1,"f");
		this.rec_thread_2 = new Thread(this.rec2,"s");
		this.rec_thread_3 = new Thread(this.rec3,"t");
		
		this.listening = false;
		
		// For Commodity Thread
		ImageButton c = (ImageButton) findViewById(R.id.ButtonCommodity);
		c.setOnTouchListener(this);
		//this.performance_text = (TextView) findViewById(R.id.PerformanceText);
		
		
		this.edit_commodity = (EditText) findViewById(R.id.EditTextCommodity);
		this.rec1.setRecognitionListener(this);
		this.rec_thread_1.start();
		
		// For Variety Thread
		ImageButton v = (ImageButton) findViewById(R.id.ButtonVariety);
		v.setOnTouchListener(this);
		//this.performance_text = (TextView) findViewById(R.id.PerformanceText);
		
		
		this.edit_variety = (EditText) findViewById(R.id.EditTextVariety);
		this.rec2.setRecognitionListener(this);
		this.rec_thread_2.start();
		
		// For Market Thread
		ImageButton m = (ImageButton) findViewById(R.id.ButtonMarket);
		m.setOnTouchListener(this);
		//this.performance_text = (TextView) findViewById(R.id.PerformanceText);
		
		
		this.edit_market = (EditText) findViewById(R.id.EditTextMarket);
		this.rec3.setRecognitionListener(this);
		this.rec_thread_3.start();
		
		// For date
		dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        findViewsById();
        setDateTimeField();
        
        
        submit = (Button) findViewById(R.id.ButtonSubmit);
        
        submit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
            	
            	String Commodity = edit_commodity.getText().toString();
    			String Variety = edit_variety.getText().toString();
    			String Market = edit_market.getText().toString();
    			String Date = fromDateEtxt.getText().toString();
    			String DateDatabase= Date.replace("-", "");
    			
            	if (!isNetworkAvailable(getApplicationContext())) 
            		Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
            
            	else if(Commodity.matches("")||Variety.matches("")||Market.matches("")||Date.matches(""))
            		Toast.makeText(getApplicationContext(), "Missing required field(s)", Toast.LENGTH_LONG).show();
            	
            	else
            	{
	                Intent resultsintent = new Intent(MainActivity.this, Results.class);
	    			resultsintent.putExtra("commo",Commodity);
	    			resultsintent.putExtra("varie",Variety);
	    			resultsintent.putExtra("markt",Market);
	    			resultsintent.putExtra("date",Date);
	    			resultsintent.putExtra("datedb",DateDatabase);
	                MainActivity.this.startActivity(resultsintent);
	            }
            }
        });
        	
	}
	
    public void findViewsById() {
        fromDateEtxt = (EditText) findViewById(R.id.EditTextDate);    
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();
     }
    
    public void setDateTimeField() {
        fromDateEtxt.setOnClickListener(this);
             
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {
 
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }
 
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
   
    }
    
    public static boolean isNetworkAvailable(Context context) {
    	ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    	NetworkInfo networkInfo = cm.getActiveNetworkInfo();
    	// if no network is available networkInfo will be null
    	// otherwise check if we are connected
    	if (networkInfo != null && networkInfo.isConnected()) {
    	    return true;
    	}
    	return false; 
    	}
    
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
 */
    
    // On click for date
    @Override
    public void onClick(View view) {
        if(view == fromDateEtxt)
            fromDatePickerDialog.show();
    }        
	

	/** Called when partial results are generated. */
	public void onPartialResults(Bundle c, int x) {
		final MainActivity that = this;
		final String hyp = c.getString("hyp");
		if(x==1)
			that.edit_commodity.post(new Runnable() {
				public void run() {that.edit_commodity.setText(hyp);}});
		
		if(x==2)
			that.edit_variety.post(new Runnable() {
				public void run() {that.edit_variety.setText(hyp);}});
		
		if(x==3)
			that.edit_market.post(new Runnable() {
				public void run() {that.edit_market.setText(hyp);}});
	}

	/** Called with full results are generated. */
	public void onResults(Bundle c, int x) {
		final String hyp = c.getString("hyp");
		final MainActivity that = this;
		
		if(x==1)
			this.edit_commodity.post(new Runnable() {
				public void run() {that.edit_commodity.setText(hyp); that.rec_dialog.dismiss();}});
		
		if(x==2)
			this.edit_variety.post(new Runnable() {
				public void run() {that.edit_variety.setText(hyp); that.rec_dialog.dismiss();}});
		
		if(x==3)
			this.edit_market.post(new Runnable() {
				public void run() {that.edit_market.setText(hyp);that.rec_dialog.dismiss();}});
	}
	
	
	public void onError(int err,  int x) {
		final MainActivity that = this;
		if(x==1)
		that.edit_commodity.post(new Runnable() {
			public void run() {that.rec_dialog.dismiss();}});
		
		if(x==2)
		that.edit_variety.post(new Runnable() {
			public void run() {that.rec_dialog.dismiss();}});
		
		if(x==3)
		that.edit_market.post(new Runnable() {
			public void run() {that.rec_dialog.dismiss();}});
	}
	
	
	
}