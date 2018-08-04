# Twitch Client Builder

## `TwitchClient`
The TwitchClient is the core component of the library, which will be accessible in almost every class.

To use the API you need to get a APPLICATION_ID and a APPLICATION_SECRET.

You can create a new App [here](https://www.twitch.tv/kraken/oauth2/clients/new)!
Here you need to provide the following information:

| Name | Value |
| ---- | --------------- |
| Name | Your application Name |
| Redirect URI (Required for the oauth integration) | http://127.0.0.1:7090/oauth_authorize_twitch |
| Application Category | Application Integration |

**Plase write your secret down immediately.** It will not be shown again, you can only generate new ones on your [`connections page`](https://www.twitch.tv/settings/connections). This is also where you can find all of your apps, or change the configuration/generate a new secret key.

### Example
```java
TwitchClient twitchClient = new TwitchClientBuilder()
				.clientId("*clientId*")
				.clientSecret("*clientSecret*")
				.build();
```
