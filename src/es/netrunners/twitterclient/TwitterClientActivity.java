package es.netrunners.twitterclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class TwitterClientActivity extends ListActivity {

	String[] from = new String[] { "Name", "Text" };
	int[] to = new int[] { R.id.username, R.id.message };
	EditText user;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		user = (EditText)findViewById(R.id.name);
	}
	
	public void viewUser(View v){
		GetTimelineTask getTweets = new GetTimelineTask(user.getText().toString());
		getTweets.execute();
	}

	public class GetTimelineTask extends AsyncTask<Void, String, List<Tweet>> {

		String user;
		String TWITTER_API_URL = "http://api.twitter.com/1/statuses/user_timeline.xml?screen_name=";
		String url;

		public GetTimelineTask(String u) {
			this.user = u;
			url = TWITTER_API_URL + user;
		}

		@Override
		protected List<Tweet> doInBackground(Void... params) {
			AndroidSaxTwiterParser parser = new AndroidSaxTwiterParser(url);
			List<Tweet> result = parser.parse();
			return result;
		}

		@Override
		protected void onPostExecute(List<Tweet> result) {
			super.onPostExecute(result);
			ArrayList<HashMap<String, String>> Tweets = new ArrayList<HashMap<String, String>>();

			for (Tweet tweet : result) {
				HashMap<String, String> tweetData = new HashMap<String, String>();
				tweetData.put("Image", tweet.getImageSrc());
				tweetData.put("Name", "@"+ tweet.getUsername());
				tweetData.put("Text", tweet.getMessage());
				Tweets.add(tweetData);
			}

			ListAdapter TweetList = new ListAdapter(
					getApplicationContext(), Tweets, R.layout.row, from, to);

			setListAdapter(TweetList);
		}

	}
}