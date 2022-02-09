package com.group24.CI;

import org.gradle.internal.impldep.org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class BuildTest {

    String projectName = "smallest-java-ci";
    String commitHash = "fbbed10d4f67139ce99f5f2165ffa0dda2f271d9";
    File tmpFolder;

    /**
     * This fixture copies the current project into temporary folder
     * next to the project folder. Afterwards the test directory is
     * deleted to prevent recursive builds.
     */
    @BeforeEach
    void setUp() throws IOException {
        // get the path of the current project
        String projectPath = System.getProperty("user.dir");
        File projectDir = new File(projectPath);

        // create tmp folder inside project folder
        String tmpPath = String.valueOf(Paths.get(projectPath, "tmpCopy"));
        tmpFolder = new File(tmpPath);
        if (!tmpFolder.exists()) tmpFolder.mkdirs();

        // copy current project into temporary folder
        FileUtils.copyDirectory(projectDir, tmpFolder);

        // remove test folder of the copied project inside tmpProjectCopy to prevent recursive builds
        String testDirPath = String.valueOf(Paths.get(tmpFolder.toString(), "src", "test"));
        File testDirectory = new File(testDirPath);
        FileUtils.deleteDirectory(testDirectory);
    }

    /**
     * This fixture deletes the project copy after running the tests.
     */
    @AfterEach
    void tearDown() throws IOException {
        FileUtils.deleteDirectory(tmpFolder);
    }

    /**
     * Build the copy of the project and check if the build is successful.
     */
    @Test
    void buildProject() {
        Build builder = new Build(tmpFolder.toString(), projectName, commitHash, tmpFolder.toString());
        boolean success = builder.buildProject();
        assertTrue(success);
    }
}