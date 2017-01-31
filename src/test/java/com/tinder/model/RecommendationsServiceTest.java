package com.tinder.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinder.model.webservice.data.RecommendationsDTO;
import com.tinder.model.webservice.data.Results;
import com.vaadin.ui.VerticalLayout;

public class RecommendationsServiceTest {
    private final static Logger LOGGER = Logger.getLogger(RecommendationsServiceTest.class.getSimpleName());
    private RecommendationsDTO recsDTO;

    @Mock
    TinderAPI tinderAPI;

    @Before
    public void setUp() {
        String fakeData = "{\"status\":200,\"results\":[{\"distance_mi\":4,\"connection_count\":1337,\"common_like_count\":0,\"common_friend_count\":0,\"common_likes\":[],\"common_interests\":[],\"uncommon_interests\":[],\"common_friends\":[],\"content_hash\":\"8QxU2OU0ri87fVeFmRs24sOQc1ququkQUZ6sEUbAuwAcVw\",\"_id\":\"53dd70858855ecd536bc14a0\",\"badges\":[],\"bio\":\"\",\"birth_date\":\"1989-01-28T16:18:38.543Z\",\"name\":\"Monica\",\"ping_time\":\"2017-01-24T21:10:04.590Z\",\"photos\":[{\"id\":\"33b0f890-79c8-4851-9826-9a5625e835d8\",\"url\":\"http://images.gotinder.com/53dd70858855ecd536bc14a0/33b0f890-79c8-4851-9826-9a5625e835d8.jpg\",\"processedFiles\":[{\"url\":\"http://images.gotinder.com/53dd70858855ecd536bc14a0/640x640_33b0f890-79c8-4851-9826-9a5625e835d8.jpg\",\"height\":640,\"width\":640},{\"url\":\"http://images.gotinder.com/53dd70858855ecd536bc14a0/320x320_33b0f890-79c8-4851-9826-9a5625e835d8.jpg\",\"height\":320,\"width\":320},{\"url\":\"http://images.gotinder.com/53dd70858855ecd536bc14a0/172x172_33b0f890-79c8-4851-9826-9a5625e835d8.jpg\",\"height\":172,\"width\":172},{\"url\":\"http://images.gotinder.com/53dd70858855ecd536bc14a0/84x84_33b0f890-79c8-4851-9826-9a5625e835d8.jpg\",\"height\":84,\"width\":84}]},{\"id\":\"c6275728-257b-4ab9-a5f3-1b00099da769\",\"url\":\"http://images.gotinder.com/53dd70858855ecd536bc14a0/c6275728-257b-4ab9-a5f3-1b00099da769.jpg\",\"processedFiles\":[{\"url\":\"http://images.gotinder.com/53dd70858855ecd536bc14a0/640x640_c6275728-257b-4ab9-a5f3-1b00099da769.jpg\",\"height\":640,\"width\":640},{\"url\":\"http://images.gotinder.com/53dd70858855ecd536bc14a0/320x320_c6275728-257b-4ab9-a5f3-1b00099da769.jpg\",\"height\":320,\"width\":320},{\"url\":\"http://images.gotinder.com/53dd70858855ecd536bc14a0/172x172_c6275728-257b-4ab9-a5f3-1b00099da769.jpg\",\"height\":172,\"width\":172},{\"url\":\"http://images.gotinder.com/53dd70858855ecd536bc14a0/84x84_c6275728-257b-4ab9-a5f3-1b00099da769.jpg\",\"height\":84,\"width\":84}]}],\"jobs\":[{\"company\":{\"name\":\"Lluis Tejero Arquitectos y Asociados\"},\"title\":{\"name\":\"Architect\"}}],\"schools\":[{\"name\":\"Pontificia Universidad Católica Madre y Maestra\",\"id\":\"108562865840867\"}],\"teaser\":{\"string\":\"Architect at Lluis Tejero Arquitectos y Asociados\",\"type\":\"jobPosition\"},\"teasers\":[{\"string\":\"Architect bei Lluis Tejero Arquitectos y Asociados\",\"type\":\"jobPosition\"},{\"string\":\"Pontificia Universidad Católica Madre y Maestra\",\"type\":\"school\"}],\"s_number\":18650514,\"gender\":1,\"birth_date_info\":\"fuzzy birthdate active, not displaying real birth_date\"},{\"distance_mi\":2,\"connection_count\":660,\"common_like_count\":0,\"common_friend_count\":0,\"common_likes\":[],\"common_friends\":[],\"content_hash\":\"j9GhJzf8xHDfblivhkgfvtDjhgvCRDh5CVH5VFQsqQ\",\"_id\":\"5884df4bd76b44bb0acf4401\",\"badges\":[],\"bio\":\"\",\"birth_date\":\"1981-01-28T16:18:38.545Z\",\"name\":\"Macarena\",\"ping_time\":\"2017-01-24T21:50:16.516Z\",\"photos\":[{\"id\":\"9c655e0c-301c-4429-843c-f01208b11030\",\"url\":\"http://images.gotinder.com/5884df4bd76b44bb0acf4401/9c655e0c-301c-4429-843c-f01208b11030.jpg\",\"processedFiles\":[{\"width\":640,\"height\":640,\"url\":\"http://images.gotinder.com/5884df4bd76b44bb0acf4401/640x640_9c655e0c-301c-4429-843c-f01208b11030.jpg\"},{\"width\":320,\"height\":320,\"url\":\"http://images.gotinder.com/5884df4bd76b44bb0acf4401/320x320_9c655e0c-301c-4429-843c-f01208b11030.jpg\"},{\"width\":172,\"height\":172,\"url\":\"http://images.gotinder.com/5884df4bd76b44bb0acf4401/172x172_9c655e0c-301c-4429-843c-f01208b11030.jpg\"},{\"width\":84,\"height\":84,\"url\":\"http://images.gotinder.com/5884df4bd76b44bb0acf4401/84x84_9c655e0c-301c-4429-843c-f01208b11030.jpg\"}]},{\"id\":\"364bbbd5-7e57-4941-b3b0-3e55093499ba\",\"url\":\"http://images.gotinder.com/5884df4bd76b44bb0acf4401/364bbbd5-7e57-4941-b3b0-3e55093499ba.jpg\",\"processedFiles\":[{\"width\":640,\"height\":640,\"url\":\"http://images.gotinder.com/5884df4bd76b44bb0acf4401/640x640_364bbbd5-7e57-4941-b3b0-3e55093499ba.jpg\"},{\"width\":320,\"height\":320,\"url\":\"http://images.gotinder.com/5884df4bd76b44bb0acf4401/320x320_364bbbd5-7e57-4941-b3b0-3e55093499ba.jpg\"},{\"width\":172,\"height\":172,\"url\":\"http://images.gotinder.com/5884df4bd76b44bb0acf4401/172x172_364bbbd5-7e57-4941-b3b0-3e55093499ba.jpg\"},{\"width\":84,\"height\":84,\"url\":\"http://images.gotinder.com/5884df4bd76b44bb0acf4401/84x84_364bbbd5-7e57-4941-b3b0-3e55093499ba.jpg\"}]}],\"jobs\":[],\"schools\":[],\"teaser\":{\"string\":\"\"},\"teasers\":[],\"s_number\":94104425,\"gender\":1,\"birth_date_info\":\"fuzzy birthdate active, not displaying real birth_date\"}]}";

        ObjectMapper mapper = new ObjectMapper();
        try {
            recsDTO = mapper.readValue(fakeData, RecommendationsDTO.class);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Test
    public void testLikePassButtonsAreAddedToTheLayout() {
        RecommendationsService recsFacade = new RecommendationsService(anyString(), anyObject(), tinderAPI);
        VerticalLayout recsLayout = new VerticalLayout();

        for (Results result : recsDTO.getResults()) {
            recsFacade.addLikePassButtons(result, recsLayout, anyObject(), anyInt());
        }
        assertThat(recsDTO.getResults().size(), equalTo(2));
        // There are two components, like and pass layout for each user
        assertThat(recsLayout.getComponentCount(), equalTo(2));
    }

    @Test
    public void testUserInfoIsAddedToTheLayout() {
        RecommendationsService recsFacade = new RecommendationsService(anyString(), anyObject(), tinderAPI);
        VerticalLayout recsLayout = new VerticalLayout();

        for (Results result : recsDTO.getResults()) {
            recsFacade.addUserInfo(result, recsLayout);
        }

        assertThat(recsDTO.getResults().size(), equalTo(2));
        // There are 9 components: name, distance, birthday and last connection
        // two times each and the job description of the first user
        assertThat(recsLayout.getComponentCount(), equalTo(9));
    }

    @Test
    public void testUserPicturesAreAddedToTheLayout() {
        RecommendationsService recsFacade = new RecommendationsService(anyString(), anyObject(), tinderAPI);
        VerticalLayout recsLayout = new VerticalLayout();

        for (Results result : recsDTO.getResults()) {
            recsFacade.addUserPictures(result, recsLayout);
        }

        assertThat(recsDTO.getResults().size(), equalTo(2));
        // There are 2 components: a picture layout for each user
        assertThat(recsLayout.getComponentCount(), equalTo(2));
    }
}