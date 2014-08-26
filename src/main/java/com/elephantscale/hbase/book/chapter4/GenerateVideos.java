package com.elephantscale.hbase.book.chapter4;

import com.elephantscale.hbase.book.common.Util;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author Mark Kerzner
 */
public class GenerateVideos {

    public static void main(String argv[]) {
        if (argv.length != 2) {
            System.out.println("Arguments: number-videos, number-locations-per-video");
            System.exit(0);
        }
        GenerateVideos generator = new GenerateVideos();
        int nVideos = Integer.parseInt(argv[0]);
        int nLocations = Integer.parseInt(argv[1]);

        try {
            generator.generate(nVideos, nLocations);
        } catch (Exception e) {
            System.out.println("Problem writig files: " + e.getMessage());
            e.printStackTrace(System.out);
        }
    }

    private void generate(int nVideos, int nLocations) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        new File(Util.ROOT_DIR).mkdirs();
        Charset charset = Charset.forName("US-ASCII");
        if (nLocations < 1) {
            nLocations = 1;
        }
        String videoFile = Util.ROOT_DIR + "/" + Util.VIDEOS;
        Util util = new Util();
        new File(videoFile).delete();
        Files.append("use videodb;\n", new File(videoFile), charset);
        for (int u = 1; u <= nVideos; ++u) {
            StringBuilder insert = new StringBuilder();
            insert.append(
                    "INSERT INTO videos \n"
                    + "(videoid, videoname, username, description, location, tags, upload_date)\n"
                    + "VALUES\n"
                    + "(");
            String videoid = UUID.randomUUID().toString();
            String videoname = util.generateName();
            // TODO use already existing username from 'users' table
            String username = util.generateName();
            String description = util.generateName();

            insert.append(videoid).append(",'").append(videoname).append("',").
                    append("'").append(username).append("','").append(description).append("',");
            insert.append("{");
            for (int e = 0; e < nLocations; ++e) {
                String domain = util.generateDomain();
                String fullDomain = e + domain;
                insert.append("'").append(fullDomain).append("'").append(" : ").append("'http://").append(domain).append("/").
                        append(videoname).append("'");
                if (e < nLocations - 1) {
                    insert.append(",");
                }
            }
            insert.append("},");
            insert.append("{'cats', 'pets'},");
            insert.append("'").append(dateFormat.format(new Date())).append("');").append("\n");
            Files.append(insert.toString(), new File(videoFile), charset);
        }
        System.out.println("Generated file: " + videoFile);
    }
}
