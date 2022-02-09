package com.group24.CI;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CloneRepositoryTest {

    // The repository url
    String repoUrl = "https://github.com/lucianozapata/DD2480VT221.git";
    // The local path of the clone destination.
    String projectPath = System.getProperty("user.dir"); // get the path of the project
    String repoName = "DD2480VT221";

    /**
     * Test if the method can clone a repo to a designated directory
     */
    @Test
    void testCloneRepository() {

        CloneRepository clone = new CloneRepository(repoUrl, projectPath, repoName);
        assertTrue(clone.cloneRepository());
    }
}
