package classes.domain.extras;

import classes.AirTrafficControl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class BackupCreator extends Thread {
    @Override
    public void run() {
        while (true) {
            File[] events = new File("events").listFiles();
            File[] alerts = new File("alert").listFiles();
            String path = "backup"
                    + File.separator
                    + "backup_"
                    + new SimpleDateFormat("yyyy_MM_dd'_'HH_mm").format(new Date())
                    + ".zip";
            try (ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(path))) {
                if (events != null) {
                    for (File ev : events) {
                        zip.putNextEntry(new ZipEntry(String.valueOf(ev)));
                        zip.write(Files.readAllBytes(ev.toPath()));
                    }
                }
                if (alerts != null) {
                    for (File al : alerts) {
                        zip.putNextEntry(new ZipEntry(String.valueOf(al)));
                        zip.write(Files.readAllBytes(al.toPath()));
                    }
                }
            } catch (IOException e) {
                AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
            }

            try {
                sleep(60_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
