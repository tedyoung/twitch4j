package twitch4j.test.util;

import twitch4j.Configuration;
import twitch4j.api.TwitchHelix;

public class HelixTestUtil {

	public TwitchHelix getHelixClient() {
		Configuration config = new Configuration("kimne78kx3ncx6brgo4mv6wki5h1ko", "clientSecret", "test", "", null, false, null);
		TwitchHelix twitchHelix = new TwitchHelix(config);

		return twitchHelix;
	}

}
