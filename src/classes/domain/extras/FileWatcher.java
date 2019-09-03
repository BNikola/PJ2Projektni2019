package classes.domain.extras;

import classes.AirTrafficControl;
import classes.controllers.Controller;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

public class FileWatcher extends Thread {
    // region Members
    private String fileToWatch;
    private String pathToDirectory;
    private volatile boolean change;
    private List<String> data;
    // endregion Members

    public FileWatcher(String fileToWatch, String pathToDirectory) {
        this.fileToWatch = fileToWatch;
        this.pathToDirectory = pathToDirectory;
        data = new ArrayList<>();
    }

    // region Getters and Setters

    public String getFileToWatch() {
        return fileToWatch;
    }

    public void setFileToWatch(String fileToWatch) {
        this.fileToWatch = fileToWatch;
    }

    public String getPathToDirectory() {
        return pathToDirectory;
    }

    public void setPathToDirectory(String pathToDirectory) {
        this.pathToDirectory = pathToDirectory;
    }

    public boolean isChange() {
        return change;
    }

    public void setChange(boolean change) {
        this.change = change;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    // endregion Getters and Setters


    @Override
    public synchronized void run() {
        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            Path dir = Paths.get(pathToDirectory);
            dir.register(watcher, ENTRY_MODIFY);

            System.out.println("Watch service registered for dir: " + dir.getFileName());

            while (true) {
                WatchKey key;
                try {
                    key = watcher.take();
                } catch (InterruptedException e) {
                    return;
                }
                for (WatchEvent<?> event : key.pollEvents()) {
                    change = false;
                    WatchEvent.Kind<?> kind = event.kind();
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path fileName = ev.context();

                    // if the fileName contains fileToWatch
                    if (fileName.toString().trim().contains(fileToWatch) && kind.equals(ENTRY_MODIFY)) {
                        // setting change to true
                        change = true;
                        data = Files.readAllLines(dir.resolve(fileName));
                        if (Controller.app != null) {
                            Controller.app.refreshTextArea(data);
                        }
                    }
                }

                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }
        } catch (IOException e) {
            AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }
}
