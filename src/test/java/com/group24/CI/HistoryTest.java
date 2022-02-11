package com.group24.CI;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryTest {

    History history;

    @BeforeEach
    void setupHistoryObject() {
        this.history = History.getHistoryInstance();
    }

    @Test
    void getHistoryInstance() {
        History secondHistory = History.getHistoryInstance();
        // make sure only single object is created
        assertEquals(history, secondHistory);
    }

    @Test
    void addReportToHistory() throws IOException {
        // append to dummy reports to the json file
        BuildReport firstReport = new BuildReport("Unit-Tests", "master", "47dea2eefc6d2816cddb3f30689070285491733c", true);
        this.history.addReportToHistory(firstReport);
        BuildReport secondReport = new BuildReport("Unit-Tests", "develop", "16ab09a55b7666f6ea5462e6460581a0989831c4", false);
        this.history.addReportToHistory(secondReport);

        // reload the reports back from the json
        Reader reader = Files.newBufferedReader(this.history.filePath);
        List<BuildReport> reports = new Gson().fromJson(reader, new TypeToken<List<BuildReport>>() {
        }.getType());

        // check if they are the same (custom equals)
        assertEquals(firstReport, reports.get(reports.size() - 2));
        assertEquals(secondReport, reports.get(reports.size() - 1));

        // remove the dummy json
        File jsonFile = new File(String.valueOf(this.history.filePath));
    }
}