package com.example.rtpodcasts;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.os.AsyncTask;
import android.util.Log;

public class PodcastHandler implements Runnable {

	public PodcastHandler() {
	}

	private class PatchGrabber extends AsyncTask<String, String, String> {
		private String GrabPatchXML() {
			try {
				URL url = new URL("http://s3.roosterteeth.com/podcasts/gaming-mp3.xml");
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
				String str;
				String xml = "";
				while ((str = in.readLine()) != null) {
					xml += str;
				}
				in.close();
				return xml;
			} catch (IOException e) {
				Log.e("tga", e.toString());
			}
			return null;
		}

		@Override
		protected String doInBackground(String... params) {
			return GrabPatchXML();
		}
	}

	private class PodcastGrabber extends AsyncTask<String, String, String> {
		private String GrabPodcastXML() {
			try {
				URL url = new URL("http://s3.roosterteeth.com/podcasts/mp3.xml");
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
				String str;
				String xml = "";
				while ((str = in.readLine()) != null) {
					xml += str;
				}
				in.close();
				return xml;
			} catch (IOException e) {
				Log.e("tga", e.toString());
			}
			return null;
		}

		@Override
		protected String doInBackground(String... params) {
			return GrabPodcastXML();
		}
	}

	// private List<Podcast> GetPatchEpisodes() {
	// return GetEpisodes(GrabPatchXML());
	// }
	//
	// private List<Podcast> GetPodcastEpisodes() {
	// return GetEpisodes(GrabPodcastXML());
	// }

	private List<Podcast> GetEpisodes(String file) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
		}
		Document document = null;
		try {
			document = builder.parse(new ByteArrayInputStream(file.getBytes()));
		} catch (Exception e) {
			Log.e("tga", e.toString());
		}

		ArrayList<Podcast> episodeList = new ArrayList<Podcast>();
		if (document != null) {
			NodeList nodeList = document.getDocumentElement().getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node root = nodeList.item(i);
				NodeList rootlist = root.getChildNodes();

				for (int j = 0; j < rootlist.getLength(); j++) {
					Node item = rootlist.item(j);

					if (item.getNodeName().equals("item")) {
						Podcast podcast = new Podcast();
						NodeList subItemList = item.getChildNodes();
						for (int k = 0; k < subItemList.getLength(); k++) {
							Node subItem = subItemList.item(k);
							String name = subItem.getNodeName();
							if (name.equals("title")) {
								podcast.setTitle(subItem.getTextContent());
							} else if (name.equals("link")) {
								podcast.setWeblink(subItem.getTextContent());
							} else if (name.equals("itunes:author")) {
								podcast.setAuthor(subItem.getTextContent());
							} else if (name.equals("pubDate")) {
								podcast.setDate(subItem.getTextContent());
							} else if (name.equals("description")) {
							} else if (name.equals("enclosure")) {
								String s = subItem.getAttributes().item(1).toString();
								podcast.setDownload_url(s.substring(5, s.length() - 1));
							} else if (name.equals("guid")) {
								podcast.setItem_nr(subItem.getTextContent());
							} else if (name.equals("itunes:duration")) {
								podcast.setDuration(subItem.getTextContent());
							}
						}
						episodeList.add(podcast);
					}
				}
			}
		}
		return episodeList;
	}

	@Override
	public void run() {
		for (;;) {
			String podcasts = null;
			String patch = null;
			try {
				podcasts = new PodcastGrabber().execute("").get();
				patch = new PatchGrabber().execute("").get();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			} catch (ExecutionException e1) {
				e1.printStackTrace();
			}
			if(patch != null)
				Log.d("tga", "There are " + GetEpisodes(patch).size() + " episodes of The Patch!");
			if(podcasts != null)
				Log.d("tga", "There are " + GetEpisodes(podcasts).size() + " episodes of The RT Podcast!");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}
}
