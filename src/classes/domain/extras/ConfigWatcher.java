package classes.domain.extras;

import classes.AirTrafficControl;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import static java.nio.file.StandardWatchEventKinds.*;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

public class ConfigWatcher extends Thread {

    // region Members
    // name of config file
    private String configFileName;
    // if file has changed
    private volatile boolean change;
    // map to store config data
    private HashMap<String, String> options = new HashMap<>();
    // endregion

    public ConfigWatcher(String configFileName) {
        this.configFileName = configFileName;
    }

    // region Getters and Setters
    public String getConfigFileName() {
        return configFileName;
    }

    public void setConfigFileName(String configFileName) {
        this.configFileName = configFileName;
    }

    public boolean isChange() {
        return change;
    }

    public HashMap<String, String> getOptions() {
        return options;
    }
    // endregion

    @Override
    public void run() {

        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            Path dir = Paths.get(System.getProperty("user.dir") + File.separator + "src" + File.separator + "configs");
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

//                    System.out.println(kind.name() + ": " + fileName);        // additional details (which files are changed)

                    // if the file name matches and if the file is modified
                    if (fileName.toString().trim().contains(configFileName) && kind.equals(ENTRY_MODIFY)) {
                        // setting change to true - must set to false after use
                        change = true;
                        List<String> content = Files.readAllLines(dir.resolve(fileName));
                        // populate the map
                        for (String s : content) {
                            options.put(s.split("=")[0], s.split("=")[1]);
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
    // endregion

    public static void main(String[] args) {
        ConfigWatcher cw = new ConfigWatcher("config.properties");
        System.out.println(pathToConfig);
        cw.start();
    }

    private static String pathToConfig = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "configs"
            + File.separator + "config.properties";
}
