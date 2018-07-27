package twitch4j.api.helix.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import reactor.core.publisher.Flux;
import twitch4j.api.TwitchHelix;
import twitch4j.api.helix.model.Game;
import twitch4j.test.util.HelixTestUtil;

@Slf4j
public class GamesServiceTest {

	/**
	 * Test Util
	 */
	private HelixTestUtil helixTestUtil = new HelixTestUtil();

	/**
	 * TwitchHelix Client
	 */
	private TwitchHelix twitchHelix = helixTestUtil.getHelixClient();

	/**
	 * Get Games by Name
	 */
	@Test
	public void testGetGamesByName() {
		// TestCase
		String testCaseName = new Object() {}.getClass().getEnclosingMethod().getName();
		log.info("{}: Running TestCase", testCaseName);

		// Request
		String gameName = "Overwatch";
		Flux<Game> gameList = twitchHelix.getGamesService().getByNames(gameName);
		gameList
				.map(entity -> {
					log.info("{}: Result Entry - {}!", testCaseName, entity.toString());
					Assert.assertEquals("Got unexpected Gamename!", gameName, entity.getName());
					return entity;
				})
				.blockLast();

		log.info("{}: Success!", testCaseName);
	}

	/**
	 * Get Top Games
	 */
	@Test
	public void testGetTopGames() {
		// TestCase
		String testCaseName = new Object() {}.getClass().getEnclosingMethod().getName();
		log.info("{}: Running TestCase", testCaseName);

		// Request
		Flux<Game> gameList = twitchHelix.getGamesService().getTopGames();
		gameList
				.map(entity -> {
					log.info("{}: Result Entry - {}!", testCaseName, entity.toString());
					return entity;
				})
				.blockLast();
		log.info("{}: Success!", testCaseName);
	}

}
