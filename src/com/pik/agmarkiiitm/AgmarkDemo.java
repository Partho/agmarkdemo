package com.pik.agmarkiiitm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;



public class AgmarkDemo extends Activity {
	
	File f;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);
		Thread AgmarkDemoThread=new Thread(){
			public void run(){
				try{
					copyAsset("pocketsphinxmodels");
					sleep(2000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}finally{
					 Intent mainIntent = new Intent(AgmarkDemo.this,MainActivity.class);
			         AgmarkDemo.this.startActivity(mainIntent);
				}
			}
		};
		AgmarkDemoThread.start();
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
		
	}
	
	public void copyAsset(String path) {
		  AssetManager manager = getAssets();
		  
		  f = new File(getExternalFilesDir(null), path);
		  if(!f.isDirectory()){

			  // If we have a directory, we make it and recurse. If a file, we copy its
			  // contents.
			  try {
			    String[] contents = manager.list(path);
	
			    // The documentation suggests that list throws an IOException, but doesn't
			    // say under what conditions. It'd be nice if it did so when the path was
			    // to a file. That doesn't appear to be the case. If the returned array is
			    // null or has 0 length, we assume the path is to a file. This means empty
			    // directories will get turned into files.
			    if (contents == null || contents.length == 0)
			      throw new IOException();
	
			    // Make the directory.
			    File dir = new File(getExternalFilesDir(null), path);
			    dir.mkdirs();
	
			    // Recurse on the contents.
			    for (String entry : contents) {
			      copyAsset(path + "/" + entry);
			    }
			  } catch (IOException e) {
			    copyFileAsset(path);
			  }
		  }
		}

		/**
		 * Copy the asset file specified by path to app's data directory. Assumes
		 * parent directories have already been created.
		 * 
		 * @param path
		 * Path to asset, relative to app's assets directory.
		 **/
		 
		public void copyFileAsset(String path) {
		  File file = new File(getExternalFilesDir(null), path);
		  try {
		    InputStream in = getAssets().open(path);
		    OutputStream out = new FileOutputStream(file);
		    byte[] buffer = new byte[1024];
		    int read = in.read(buffer);
		    while (read != -1) {
		      out.write(buffer, 0, read);
		      read = in.read(buffer);
		    }
		    out.close();
		    in.close();
		  } catch (IOException e) {
		    //Log.d(path, path, e);
		  }
		}
	
}