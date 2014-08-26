package com.elephantscale.hbase.book.chapter6;

import com.elephantscale.hbase.book.common.Util;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author Mark Kerzner
 */
public class GenerateTagIndex {

    public static void main(String argv[]) {
        if (argv.length != 2) {
            System.out.println("Arguments: tags, average-videos-per-tag");
            System.exit(0);
        }
        GenerateTagIndex generator = new GenerateTagIndex();
        int nTags = Integer.parseInt(argv[0]);
        int nAverageVideos = Integer.parseInt(argv[1]);

        try {
            generator.generate(nTags, nAverageVideos);
        } catch (Exception e) {
            System.out.println("Problem writig files: " + e.getMessage());
            e.printStackTrace(System.out);
        }
    }

    private void generate(int nTags, int nAverageVideos) throws IOException {
        new File(Util.ROOT_DIR).mkdirs();
        Charset charset = Charset.forName("US-ASCII");
        if (nAverageVideos < 1) {
            nAverageVideos = 1;
        }
        // users
        String userFile = Util.ROOT_DIR + "/" + Util.TAG_INDEX;
        Util util = new Util();
        new File(userFile).delete();
        Files.append("use videodb;\n", new File(userFile), charset);
        for (int u = 1; u <= nTags; ++u) {
            StringBuilder tagPart = new StringBuilder();
            tagPart.append("INSERT INTO tag_index (tag, videoid, timestamp) VALUES (");
            String tag = util.generateName();
            tagPart.append("'").append(tag).append("',");
            // calculate the actual number of videos for this user
            // here we take an average between 1 and 2 * average, but 
            // TODO use a more realistic distribution, with long tail
            int nVideos = util.generateInt(1, 2 * nAverageVideos);          
            for (int e = 0; e < nVideos; ++e) {
                StringBuilder videoPart = new StringBuilder();
                String videoid = UUID.randomUUID().toString();
                // date the video back up to a year
                String uploadDate = Util.dateFormat.format(new Date().getTime() - util.generateInt(0, 365) * Util.DAY_MILS);
                videoPart.append(videoid).append(",'").append(uploadDate).append("');").append("\n");
                Files.append(tagPart.toString() + videoPart.toString(), new File(userFile), charset);
            }
        }
        System.out.println("Generated file: " + userFile);
    }
}
