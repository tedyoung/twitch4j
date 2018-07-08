package twitch4j.api.kraken.endpoints;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.client.RestTemplate;
import twitch4j.api.kraken.enums.StreamType;
import twitch4j.api.kraken.exceptions.ChannelCredentialMissingException;
import twitch4j.api.kraken.exceptions.ScopeMissingException;
import twitch4j.api.kraken.model.Channel;
import twitch4j.api.kraken.model.Game;
import twitch4j.api.kraken.model.Recommendation;
import twitch4j.api.kraken.model.RecommendationList;
import twitch4j.api.kraken.model.Stream;
import twitch4j.api.kraken.model.StreamFeatured;
import twitch4j.api.kraken.model.StreamFeaturedList;
import twitch4j.api.kraken.model.StreamList;
import twitch4j.api.kraken.model.StreamSingle;
import twitch4j.api.kraken.model.StreamSummary;
import twitch4j.api.kraken.model.User;
import twitch4j.api.util.rest.HeaderRequestInterceptor;
import twitch4j.api.util.rest.QueryRequestInterceptor;
import twitch4j.common.auth.ICredential;
import twitch4j.common.auth.Scope;
import twitch4j.common.utils.Unofficial;

/**
 * All api methods related to a stream.
 *
 * @author Philipp Heuer [https://github.com/PhilippHeuer]
 * @version %I%, %G%
 * @since 1.0
 */
@Slf4j
public class StreamEndpoint extends AbstractTwitchEndpoint {

	/**
	 * Stream Endpoint
	 *
	 * @param restTemplate The Twitch Client.
	 */
	public StreamEndpoint(RestTemplate restTemplate) {
		super(restTemplate);
	}

	/**
	 * Get Stream by Channel
	 * <p>
	 * Gets stream information (the stream object) for a specified channel.
	 * Requires Scope: none
	 *
	 * @param channel Get stream object of Channel Entity
	 * @return Optional of type Stream is only Present if Stream is Online, returns Optional.empty for Offline streams
	 */
	public Stream getByChannel(Channel channel) {
		// Endpoint
		String requestUrl = String.format("/streams/%s", channel.getId());

		// REST Request
		try {
			return restTemplate.getForObject(requestUrl, StreamSingle.class).getStream();
		} catch (Exception ex) {
			log.error("Request failed: " + ex.getMessage());
			log.trace(ExceptionUtils.getStackTrace(ex));

			return null;
		}
	}

	/**
	 * Get Stream by Channel
	 * <p>
	 * Gets stream information (the stream object) for a specified channel.
	 * Requires Scope: none
	 *
	 * @param user Get stream object of Channel Entity
	 * @return Optional of type Stream is only Present if Stream is Online, returns Optional.empty for Offline streams
	 */
	public Stream getByUser(User user) {
		// Endpoint
		String requestUrl = String.format("/streams/%s", user.getId());

		// REST Request
		try {
			return restTemplate.getForObject(requestUrl, StreamSingle.class).getStream();
		} catch (Exception ex) {
			log.error("Request failed: " + ex.getMessage());
			log.trace(ExceptionUtils.getStackTrace(ex));

			return null;
		}
	}

	/**
	 * Get All Streams (ordered by current viewers, desc)
	 * <p>
	 * Gets the list of all live streams.
	 * Requires Scope: none
	 *
	 * @param limit      Maximum number of most-recent objects to return. Default: 25. Maximum: 100.
	 * @param offset     Object offset for pagination of results. Default: 0.
	 * @param language   Restricts the returned streams to the specified language. Permitted values are locale ID strings, e.g. en, fi, es-mx.
	 * @param game       Restricts the returned streams to the specified game.
	 * @param channels   Receives the streams from a list of channels.
	 * @param streamType Restricts the returned streams to a certain stream type. Valid values: live, playlist, all. Playlists are offline streams of VODs (Video on Demand) that appear live. Default: live.
	 * @return Returns all streams that match with the provided filtering.
	 */
	public List<Stream> getLiveStreams(@Nullable List<Channel> channels, @Nullable Game game, @Nullable List<Locale> language, StreamType streamType, Integer limit, Integer offset) {
		// Endpoint
		RestTemplate restTemplate = this.restTemplate;

		// Parameters
		if (limit != null) {
			restTemplate.getInterceptors().add(new QueryRequestInterceptor("limit", Integer.toString((limit > 100) ? 100 : (limit < 1) ? 25 : limit)));
		}
		if (offset != null) {
			restTemplate.getInterceptors().add(new QueryRequestInterceptor("offset", Integer.toString((offset < 0) ? 0 : offset)));
		}
		if (language != null && language.size() > 0) {
			restTemplate.getInterceptors().add(new QueryRequestInterceptor("language", language.stream().map(Locale::getLanguage).collect(Collectors.joining(","))));
		}
		if (game != null) {
			restTemplate.getInterceptors().add(new QueryRequestInterceptor("game", game.getName()));
		}
		if (channels != null && channels.size() > 0) {
			restTemplate.getInterceptors().add(new QueryRequestInterceptor("channel", channels.stream().map(channel -> channel.getId().toString()).collect(Collectors.joining(","))));
		}
		if (streamType != null) {
			restTemplate.getInterceptors().add(new QueryRequestInterceptor("stream_type", streamType.name().toLowerCase()));
		}

		// REST Request
		try {
			return restTemplate.getForObject("/streams", StreamList.class).getStreams();
		} catch (Exception ex) {
			log.error("Request failed: " + ex.getMessage());
			log.trace(ExceptionUtils.getStackTrace(ex));

			return Collections.emptyList();
		}
	}

	/**
	 * Get Followed Streams
	 * <p>
	 * Gets the list of online streams a user follows based on the OAuthTwitch token provided.
	 * Requires Scope: user_read
	 *
	 * @param credential The user.
	 * @return All streams as user follows.
	 */
	public List<Stream> getFollowed(ICredential credential) {

		try {
			checkScopePermission(credential.scopes(), Scope.USER_READ);

			// Endpoint
			RestTemplate restTemplate = this.restTemplate;

			// Parameters
			restTemplate.getInterceptors().add(new HeaderRequestInterceptor("Authorization", String.format("OAuth %s", credential.accessToken())));

			// REST Request
			return restTemplate.getForObject("/streams/followed", StreamList.class).getStreams();
		} catch (ScopeMissingException ex) {
			throw new ChannelCredentialMissingException(credential.userId(), ex);
		} catch (Exception ex) {
			log.error("Request failed: " + ex.getMessage());
			log.trace(ExceptionUtils.getStackTrace(ex));

			return Collections.emptyList();
		}
	}

	/**
	 * Get Featured Streams
	 * <p>
	 * Gets a list of all featured live streams.
	 * Requires Scope: none
	 *
	 * @param limit  Maximum number of most-recent objects to return. Default: 25. Maximum: 100.
	 * @param offset Object offset for pagination of results. Default: 0.
	 * @return The requested range/amount of featured streams.
	 */
	public List<StreamFeatured> getFeatured(@Nullable Integer limit, @Nullable Integer offset) {
		// Endpoint
		RestTemplate restTemplate = this.restTemplate;

		// Parameters
		if (limit != null) {
			restTemplate.getInterceptors().add(new QueryRequestInterceptor("limit", Integer.toString((limit > 100) ? 100 : (limit < 1) ? 25 : limit)));
		}
		if (offset != null) {
			restTemplate.getInterceptors().add(new QueryRequestInterceptor("offset", Integer.toString((offset < 0) ? 0 : offset)));
		}

		// REST Request
		try {
			return restTemplate.getForObject("/streams/featured", StreamFeaturedList.class).getFeatured();
		} catch (Exception ex) {
			log.error("Request failed: " + ex.getMessage());
			log.trace(ExceptionUtils.getStackTrace(ex));

			return Collections.emptyList();
		}
	}

	/**
	 * Get Streams Summary
	 * <p>
	 * Gets a summary of all live streams.
	 * Requires Scope: none
	 *
	 * @param game Restricts the summary stats to the specified game.
	 * @return A <code>StreamSummary</code> object, that contains the total number of live streams and viewers.
	 */
	public StreamSummary getSummary(@Nullable Game game) {
		// Endpoint
		RestTemplate restTemplate = this.restTemplate;

		// Parameters
		if (game != null) {
			restTemplate.getInterceptors().add(new QueryRequestInterceptor("game", game.getName()));
		}

		// REST Request
		try {
			return restTemplate.getForObject("/streams/summary", StreamSummary.class);
		} catch (Exception ex) {
			log.error("Request failed: " + ex.getMessage());
			log.trace(ExceptionUtils.getStackTrace(ex));

			return new StreamSummary();
		}
	}

	/**
	 * Get recommended streams for User (Unofficial)
	 * Gets a list of recommended streams for a user.
	 * Requires Scope: none
	 *
	 * @param credential OAuthCredential of the user, you want to request recommendations for.
	 * @return StreamList of random Streams.
	 */
	@Unofficial("https://discuss.dev.twitch.tv/t/is-the-recommended-endpoint-available-documented/9994")
	public List<Recommendation> getRecommendations(ICredential credential) {
		// Endpoint
		RestTemplate restTemplate = this.restTemplate;

		// Parameters
		restTemplate.getInterceptors().add(new HeaderRequestInterceptor("Authorization", String.format("OAuth %s", credential.accessToken())));

		// REST Request
		try {
			return restTemplate.getForObject("/streams/recommended", RecommendationList.class).getRecommendedStreams();
		} catch (Exception ex) {
			log.error("Request failed: " + ex.getMessage());
			log.trace(ExceptionUtils.getStackTrace(ex));

			return Collections.emptyList();
		}
	}

	/**
	 * Get the streams on the frontpage for a specific region (UnofficialEndpoint)
	 *
	 * @param locale 	The Language
	 * @param limit 	Limit
	 * @return The 6 streams of the frontpage for the specified region.
	 */
	@Unofficial("https://github.com/justintv/Twitch-API/blob/9991b5673734916ad8f06a5b6843e0da4b68ed9f/v3_resources/streams.md#get-streamsfeatured")
	public List<StreamFeatured> getStreamsOnFrontpage(@Nullable Locale locale, @Nullable Integer limit) {
		// Endpoint
		String requestUrl = String.format("https://api.twitch.tv/kraken/streams/featured");
		RestTemplate restTemplate = this.restTemplate;

		// Parameters
		if (limit != null) {
			restTemplate.getInterceptors().add(new QueryRequestInterceptor("limit", Integer.toString((limit > 100) ? 100 : (limit < 1) ? 25 : limit)));
		}
		if (locale != null) {
			restTemplate.getInterceptors().add(new QueryRequestInterceptor("lang", locale.getLanguage()));
			if (locale.getCountry() != null) {
				restTemplate.getInterceptors().add(new QueryRequestInterceptor("geo", locale.getCountry()));
			}
		}

		// REST Request
		try {
			return restTemplate.getForObject(requestUrl, StreamFeaturedList.class).getFeatured();
		} catch (Exception ex) {
			log.error("Request failed: " + ex.getMessage());
			log.trace(ExceptionUtils.getStackTrace(ex));

			return Collections.emptyList();
		}
	}

	/**
	 * Checks if a stream is live (includes replays)
	 * Requires Scope: none
	 *
	 * @param channel Get stream object of Channel Entity
	 * @return Channel is live
	 */
	public boolean isLive(Channel channel) {
		return this.getByChannel(channel) != null;
	}

	/**
	 * Checks if a stream is currently live and running a replay
	 * Requires Scope: none
	 *
	 * @param channel Get stream object of Channel Entity.
	 * @return Channel is live and playing replay.
	 */
	public boolean isReplaying(Channel channel) {
		Stream stream = this.getByChannel(channel);
		return stream != null && stream.isPlaylist();
	}

	/**
	 * Whether a stream is on the frontpage.
	 * <p>
	 * This method check's if the stream is on the frontpage for a certain region.
	 *
	 * @param stream Stream object
	 * @deprecated Unsupported. Will return <code>false</code>
	 * @return Whether a stream is on the frontpage.
	 */
	@Deprecated
	public Boolean isStreamOnFrontpage(Stream stream) {
		return false;
	}
}
