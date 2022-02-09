package com.group24.CI;

import org.gradle.internal.impldep.org.apache.commons.io.FileUtils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class CloneRepositoryTest {

    // The repository url
    String repoUrl = "https://github.com/lucianozapata/DD2480VT221.git";
    // The local path of the clone destination.
    String projectPath = System.getProperty("user.dir"); // get the path of the project
    String directoryPath = String.valueOf(Paths.get(projectPath, "clone_test"));

    /**
     * Deletes the repository under the directory path before and after the test.
     * @throws IOException
     */
    @BeforeEach
    @AfterEach
    void clearRepository() throws IOException {
        FileUtils.deleteDirectory(new File(directoryPath));
    }

    /**
     * Test if the method can clone a repo to a designated directory
     */
    @Test
    void testCloneRepository() {

        CloneRepository clone = new CloneRepository(repoUrl, directoryPath);
        assertTrue(clone.cloneRepository());
    }
}
