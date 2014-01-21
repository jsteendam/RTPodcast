package com.example.rtpodcasts;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy);

		new Thread(new PodcastHandler()).start();
//		new Runnable() {
//			@Override
//			public void run() {
//				try {
//					EditText input = (EditText) findViewById(R.id.inputField);
//					TextView output = (TextView) findViewById(R.id.outputField);
//					String escaped = JSONObject.quote(input.getText().toString());
//					Log.d("app", escaped);
//					String result = new RequestTask().execute("http://coliru.stacked-crooked.com/compile", escaped).get();
//					output.setText(result);
//				} catch (InterruptedException e) {
//				} catch (ExecutionException e) {
//				}
//			}
//		}.run();
//		Log.d("tga", "There are " + ph.GetPatchEpisodes().size() + " episodes of The Patch!");
//		Log.d("tga", "There are " + ph.GetPodcastEpisodes().size() + " episodes of The RT Podcast!");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
