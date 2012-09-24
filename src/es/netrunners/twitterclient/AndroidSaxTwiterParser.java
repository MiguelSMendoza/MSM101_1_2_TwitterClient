package es.netrunners.twitterclient;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.util.Xml;

public class AndroidSaxTwiterParser {

	// XML Tags
	static final String PUB_DATE = "created_at";
	static final String TEXT = "text";
	static final String USER = "user";
	static final String USERNAME = "screen_name";
	static final String IMAGE = "profile_image_url";
	static final String ITEM = "status";

	final URL twitterURL;

	protected AndroidSaxTwiterParser(String twitterURL) {
		try {
			this.twitterURL = new URL(twitterURL);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Tweet> parse() {
		final Tweet currentTweet = new Tweet();
		RootElement root = new RootElement("statuses");
		final List<Tweet> tweets = new ArrayList<Tweet>();
		Element item = root.getChild(ITEM);
		item.setEndElementListener(new EndElementListener() {
			public void end() {
				tweets.add(currentTweet.copy());
			}
		});
		Element user = item.getChild(USER);
		user.getChild(USERNAME).setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						currentTweet.setUsername(body);
					}
				});
		user.getChild(IMAGE).setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						currentTweet.setImageSrc(body);
					}
				});
		item.getChild(TEXT).setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						currentTweet.setMessage(body);
					}
				});
		item.getChild(PUB_DATE).setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						currentTweet.setDate(body);
					}
				});
		try {
			Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8,
					root.getContentHandler());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return tweets;
	}

	protected InputStream getInputStream() {
		try {
			return twitterURL.openConnection().getInputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
