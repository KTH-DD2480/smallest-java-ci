package com.group24.CI;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import kotlin.collections.ArrayDeque;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to write build reports into json file.
 * */
public class History {
    private static final Logger logger = Logger.getLogger(History.class);
    Gson gson;
    Path filePath;
    Writer writer;
    Reader reader;
    List<BuildReport> reportList;

    /**
     * Private constructor (Singleton design pattern).
     * */
    private History() {

        String fileName = "history.json";
        String folderName = "history";
        String projectPath = System.getProperty("user.dir");

        this.gson = new Gson();
        this.filePath = Paths.get(projectPath, folderName, fileName);

        // Create history folder if it does not exist
        Path historyPath = Paths.get(projectPath, folderName);
        File historyFolder = new File(String.valueOf(historyPath));
        if (!historyFolder.exists()) historyFolder.mkdirs();

        // If history read from it
        File file = new File(String.valueOf(this.filePath));
        if (file.exists()) {
            try {
                this.reader = Files.newBufferedReader(this.filePath);
                this.reportList = gson.fromJson(reader, new TypeToken<List<BuildReport>>() {}.getType());
                this.reader.close();
                logger.info("Successful read");
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("failed to read",e);
            }
        } else {
            this.reportList = new ArrayList<BuildReport>();
        }
    }

    private static class SingletonHistory {
        private static final History INSTANCE = new History();
    }

    /**
     * Use instead of constructor to get singleton.
     * @return History instance.
     * */
    public static History getHistoryInstance() {
        return SingletonHistory.INSTANCE;
    }

    /**
     * Write build report object into the json history file.
     * @param report object to write into json history
     * */
    public void addReportToHistory(BuildReport report) {
        this.reportList.add(report);
        try {
            this.writer = Files.newBufferedWriter(this.filePath);
            this.gson.toJson(this.reportList, this.writer);
            this.writer.close();
            logger.info("Successful write into json history");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("failed to write into json history");
        }
    }
}
