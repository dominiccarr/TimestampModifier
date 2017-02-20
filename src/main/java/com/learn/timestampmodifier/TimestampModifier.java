package com.learn.timestampmodifier;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dcarr
 */
public class TimestampModifier {

    public static void main(String[] args) {

        String baseDir = "C:\\Users\\dcarr\\Downloads\\UserService";
        Calendar calendar = new GregorianCalendar(2016, 1, 28, 13, 24, 56);
        traverse(baseDir, calendar);

    }

    public static void modify(Path path, Calendar calendar) {
        try {
            calendar.set(Calendar.SECOND, (int) (Math.random() * 60));
            calendar.set(Calendar.MINUTE, (int) (24 + (Math.random() * 20)));
            Date date = calendar.getTime();
            Files.setLastModifiedTime(path, FileTime.fromMillis(date.getTime()));
        } catch (IOException ex) {
            Logger.getLogger(TimestampModifier.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void traverse(String directory, Calendar calendar) {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(directory))) {
            for (Path path : directoryStream) {
                modify(path, calendar);
                if (Files.isDirectory(path)) {
                    TimestampModifier.traverse(path.toString(), calendar);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
