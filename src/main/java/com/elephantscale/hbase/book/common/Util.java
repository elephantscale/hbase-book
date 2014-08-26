package com.elephantscale.hbase.book.common;

import java.text.SimpleDateFormat;
import java.util.Random;

/**
 *
 * @author Mark Kerzner
 */
public class Util {
    // constants

    public static String ROOT_DIR = "generated";
    public static String USERS = "users.txt";
    public static String VIDEOS = "videos.txt";
    public static String VIDEO_EVENTS = "video_events.txt";
    public static String USER_VIDEO_INDEX = "user_video_index.txt";
    public static String TAG_INDEX = "tag_index.txt";
    public static String COMMENTS_BY_USER = "comments_by_user.txt";
    public static String COMMENTS_BY_VIDEO = "comments_by_video.txt";
    // generators
    private Random ran = new Random();
    private static char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
        'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private String[] domains = {"gmail.com", "yahoo.com", "hotmail.com", "msn.com", "comcast.net"};
    private String[] events = {"START", "STOP", "PAUSE", "RESUME"};
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final long DAY_MILS = 1000L * 60 * 60 * 24;

    public Util() {
    }

    public String generateName() {
        StringBuilder userName = new StringBuilder();
        int len = generateInt(5, 10);
        for (int l = 0; l < len; ++l) {
            int pos = generateInt(0, letters.length);
            userName.append(letters[pos]);
        }
        return userName.toString();
    }

    public String generateDomain() {
        int domain = generateInt(0, domains.length);
        return domains[domain];
    }

    public String generateEvent() {
        int event = generateInt(0, events.length);
        return events[event];
    }

    public int generateInt(int min, int range) {
        int i = ran.nextInt(range) + min;
        return i;
    }

    /**
     * Gets a new time uuid.
     *
     * @return the time uuid
     */
    public static java.util.UUID generateTimeUUID() {
        return java.util.UUID.fromString(new com.eaio.uuid.UUID().toString());
    }
}
