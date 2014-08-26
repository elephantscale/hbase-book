package com.elephantscale.hbase.book.chapter6;

import com.elephantscale.hbase.book.common.Util;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.UUID;

/**
 *
 * @author Mark Kerzner
 */
public class GenerateVideoEvents {

    public static void main(String argv[]) {
        if (argv.length != 2) {
            System.out.println("Arguments: number-videos, average-events-per-video");
            System.exit(0);
        }
        GenerateVideoEvents generator = new GenerateVideoEvents();
        int nVideos = Integer.parseInt(argv[0]);
        int nEventsAverage = Integer.parseInt(argv[1]);

        try {
            generator.generate(nVideos, nEventsAverage);
        } catch (Exception e) {
            System.out.println("Problem writig files: " + e.getMessage());
            e.printStackTrace(System.out);
        }
    }

    private void generate(int nVideos, int nEventsAverage) throws IOException {
        Random ran = new Random();
        new File(Util.ROOT_DIR).mkdirs();
        Charset charset = Charset.forName("US-ASCII");
        if (nEventsAverage < 1) {
            nEventsAverage = 1;
        }
        String videoEventsFile = Util.ROOT_DIR + "/" + Util.VIDEO_EVENTS;
        Util util = new Util();
        new File(videoEventsFile).delete();
        Files.append("use videodb;\n", new File(videoEventsFile), charset);
        for (int u = 1; u <= nVideos; ++u) {
            StringBuilder videoPart = new StringBuilder();
            videoPart.append(
                    "INSERT INTO video_event "
                    + "(videoid, username, event, event_timestamp, video_timestamp)"
                    + "VALUES"
                    + "(");
            String videoid = UUID.randomUUID().toString();
            // TODO use already existing username from 'users' table
            String username = util.generateName();
            String event = util.generateEvent();
            String eventTimestamp = Util.generateTimeUUID().toString();
            videoPart.append(videoid).append(",'").append(username).append("','").append(event).append("',").
                    append(eventTimestamp).append(",");
            // calculate the actual number of events for this video/user
            // here we take an average between 1 and 2 * average, but 
            // TODO use a more realistic distribution, with long tail
            int nEvents = util.generateInt(1, 2 * nEventsAverage);
            for (int e = 0; e < nEvents; ++e) {
                StringBuilder eventPart = new StringBuilder();
                long videoTimestamp = ran.nextLong();
                if (videoTimestamp < 0) videoTimestamp = -videoTimestamp;
                eventPart.append(videoTimestamp).append(");\n");
                Files.append(videoPart.toString() + eventPart.toString(), new File(videoEventsFile), charset);
            }
        }
        System.out.println("Generated file: " + videoEventsFile);
    }
}
