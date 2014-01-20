package com.example.rtpodcasts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class PodcastHandler {
	public PodcastHandler() {
		GrabPatchXML();
		GrabPodcastXML();
	}

	private void GrabPatchXML() {
		try {
			URL website = new URL("http://s3.roosterteeth.com/podcasts/gaming-mp3.xml");
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream("gaming.xml");
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		} catch (IOException e) {
		}
	}

	private void GrabPodcastXML() {
		try {
			URL website = new URL("http://s3.roosterteeth.com/podcasts/mp3.xml");
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream("podcast.xml");
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		} catch (IOException e) {
		}
	}

	public List<Podcast> GetPatchEpisodes() {
		return GetEpisodes("gaming.xml");
	}

	public List<Podcast> GetPodcastEpisodes() {
		return GetEpisodes("podcast.xml");
	}

	private List<Podcast> GetEpisodes(String file) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;

		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
		}

		// Load and Parse the XML document
		// document contains the complete XML as a Tree.
		Document document = null;
		try {
			File f = new File(file);
			FileInputStream fis = new FileInputStream(f);
			document = builder.parse(fis);
		} catch (IOException e) {
		} catch (SAXException e) {
		}

		List<Podcast> episodeList = new ArrayList<>();
		// Iterating through the nodes and extracting the data.

		NodeList nodeList = document.getDocumentElement().getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			// We have encountered an <employee> tag.
			Node root = nodeList.item(i);
			NodeList rootlist = root.getChildNodes();

			for (int j = 0; j < rootlist.getLength(); j++) {
				// System.out.println(rootlist.item(j).getNodeName());
				Node item = rootlist.item(j);

				if (item.getNodeName().equals("item")) {
					Podcast podcast = new Podcast();
					NodeList subItemList = item.getChildNodes();
					for (int k = 0; k < subItemList.getLength(); k++) {
						Node subItem = subItemList.item(k);
						switch (subItem.getNodeName()) {
						case "title":
							podcast.setTitle(subItem.getTextContent());
							break;
						case "link":
							podcast.setWeblink(subItem.getTextContent());
							break;
						case "itunes:author":
							podcast.setAuthor(subItem.getTextContent());
							break;
						case "pubDate":
							podcast.setDate(subItem.getTextContent());
							break;
						case "description":
							break;
						case "enclosure":
							String s = subItem.getAttributes().item(1).toString();
							podcast.setDownload_url(s.substring(5, s.length() - 1));
							break;
						case "guid":
							podcast.setItem_nr(subItem.getTextContent());
							break;
						case "itunes:duration":
							podcast.setDuration(subItem.getTextContent());
							break;
						}
					}
					episodeList.add(podcast);
				}
			}
		}
		return episodeList;
	}
}
