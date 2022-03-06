package instagram4j;


import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.actions.media.MediaAction;
import com.github.instagram4j.instagram4j.models.media.Media;
import com.github.instagram4j.instagram4j.models.media.timeline.Comment;
import com.github.instagram4j.instagram4j.models.media.timeline.TimelineVideoMedia;
import com.github.instagram4j.instagram4j.models.user.User;
import com.github.instagram4j.instagram4j.requests.feed.FeedClipsRequest;
import com.github.instagram4j.instagram4j.requests.feed.FeedUserRequest;
import com.github.instagram4j.instagram4j.requests.media.MediaGetCommentsRequest.SortOrder;
import com.github.instagram4j.instagram4j.responses.accounts.AccountsAccessToolHtmlResponse;
import com.github.instagram4j.instagram4j.responses.feed.FeedClipsResponse;
import com.github.instagram4j.instagram4j.responses.feed.FeedUserResponse;
import com.github.instagram4j.instagram4j.responses.media.MediaGetCommentsResponse;
import com.github.instagram4j.instagram4j.utils.IGUtils;
import okhttp3.OkHttpClient;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static instagram4j.TestUtils.*;

public class MediaActionsTest {
    private static final Logger log = LoggerFactory.getLogger(MediaActionsTest.class);

    private static IGClient client;

    @BeforeClass
    public static void setUp() throws IOException, ClassNotFoundException {
        OkHttpClient.Builder clientBuilder = IGUtils.defaultHttpClientBuilder();

        setUpProxy(clientBuilder);

        client = IGClient.deserialize(new File(CLIENT_FILE), new File(COOKIE_FILE), clientBuilder);

    }

    @AfterClass
    public static void tearDown() throws IOException {
        client.serialize(new File(CLIENT_FILE), new File(COOKIE_FILE));
        log.info("Client has been serialized successfully");
    }

    @Test
    public void testClipUpload() {
        client.actions().clips()
                .uploadClip(new File(PATH + "test.mp4"),
                        new File(PATH + "test.jpg"),
                        "my caption")
                .join();
    }

    @Test
    public void testTimelineVideoUpload() {
        client.actions().timeline()
                .uploadVideo(new File(PATH + "test.mp4"),
                        new File(PATH + "test.jpg"),
                        "my caption")
                .join();
    }

    @Test
    public void testFeedUserTimeline() {
        User user = client.actions().users().findByUsername("cristiano").join().getUser();
        FeedUserResponse response = new FeedUserRequest(user.getPk()).execute(client).join();
        response.getItems()
                .forEach(media -> log.info(media.getMedia_type() + " " + media.getLike_count() + " " + media.getCaption().getText()));
    }

    @Test
    public void testFeedDiscoverClips() {
        CompletableFuture<FeedClipsResponse> responseCompletableFuture = client.actions().clips().feedClips(FeedClipsRequest.FeedType.DISCOVER);
        FeedClipsResponse response = responseCompletableFuture.join();
        log.warn(String.valueOf(response.getItems().size()));
        response.getItems()
                .forEach(media -> log.info(media.getId() + " " + media.getUser().getUsername() + " " + media.getVideo_versions().get(0).getUrl()));
    }

    @Test
    public void testFeedDiscoverClipsWithAmount() {
        int amount = 21;
        List<TimelineVideoMedia> clips = client.actions().clips().feedClipsList(FeedClipsRequest.FeedType.DISCOVER, amount);
        clips.forEach(media -> log.info(media.getId() + " " + media.getUser().getUsername()));
        Assert.assertTrue(clips.size() >= amount);
        float uniqueAmount = clips.stream().map(Media::getId).collect(Collectors.toSet()).size();
        log.info("Unique amount = " + uniqueAmount + "; uniqueness = " + uniqueAmount / clips.size());
    }

    @Test
    public void testFeedDiscoverClipsWithAmountAsync() {
        int amount = 21;
        List<TimelineVideoMedia> clips = client.actions().clips().feedClipsAsync(FeedClipsRequest.FeedType.DISCOVER, amount).join();
        clips.forEach(media -> log.info(media.getId() + " " + media.getUser().getUsername()));
        Assert.assertTrue(clips.size() >= amount);
        float uniqueAmount = clips.stream().map(Media::getId).collect(Collectors.toSet()).size();
        log.info("Unique amount = " + uniqueAmount + "; uniqueness = " + uniqueAmount / clips.size());
    }

    @Test
    public void testChangeBio() {
        client.actions().account().setBio("test").join();
    }

    @Test
    public void testGetAccountRegDate() {
        AccountsAccessToolHtmlResponse response = client.actions().account().accessTool().join();
        System.out.println(response.getJoinedDate());
    }


    @Test
    public void getPopularCommentsTest() {
        List<TimelineVideoMedia> clips = client.actions().clips().feedClips(FeedClipsRequest.FeedType.DISCOVER).join().getItems();
        clips.forEach(clip -> {
            log.warn("comments:{}, views:{}, likes:{}, url:{}",
                    clip.getComment_count(),
                    clip.getView_count(),
                    clip.getLike_count(),
                    clip.getVideo_versions().get(0).getUrl());
            MediaGetCommentsResponse response = MediaAction.of(client, clip.getId()).commentsPage(SortOrder.POPULAR).join();
            response.getComments().stream()
                    .sorted(Comparator.comparingInt(Comment::getComment_like_count).reversed())
                    .forEach(comment -> log.info("{} : {}", comment.getComment_like_count(), comment.getText()));
        });
    }
}
