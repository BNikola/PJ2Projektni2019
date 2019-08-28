package classes.domain.extras;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class BackupCreator extends Thread {
    // TODO: 28.8.2019. Add this to main, maybe remove class
    @Override
    public void run() {
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            sleep(60_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BackupCreator backupCreator = new BackupCreator();
        backupCreator.run();
    }
}
