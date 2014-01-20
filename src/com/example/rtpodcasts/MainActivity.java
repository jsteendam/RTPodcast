package com.example.rtpodcasts;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		PodcastHandler ph = new PodcastHandler();
		Log.d("tga", "There are " + ph.GetPatchEpisodes().size() + " episodes of The Patch!");
		Log.d("tga", "There are " + ph.GetPodcastEpisodes().size() + " episodes of The RT Podcast!");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
