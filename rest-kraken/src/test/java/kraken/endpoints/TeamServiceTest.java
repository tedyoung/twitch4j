package kraken.endpoints;

import com.github.twitch4j.kraken.TwitchKraken;
import com.github.twitch4j.kraken.TwitchKrakenBuilder;
import com.github.twitch4j.kraken.domain.KrakenTeam;
import com.github.twitch4j.kraken.domain.KrakenTeamList;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@Tag("integration")
public class TeamServiceTest extends AbstractKrakenServiceTest {

    @Test
    @DisplayName("getAllTeams")
    @Disabled
    public void getAllTeams() {
        KrakenTeamList resultList = getTwitchKrakenClient().getAllTeams(null, null).execute();

        assertTrue(resultList.getTeams().size() > 0, "Didn't find any teams!");
    }

    @Test
    @DisplayName("getTeamByName")
    @Disabled
    public void getTeamByName() {
        KrakenTeam result = getTwitchKrakenClient().getTeamByName("staff").execute();

        assertNotNull(result, "Didn't find the specified team!");

        assertTrue(result.getUsers().size() > 1, "Should be at least one team member");
    }

}
