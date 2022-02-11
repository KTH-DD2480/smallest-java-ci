package com.group24.CI;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CloneRepositoryTest {

    // The repository url
    String repoUrl = "https://github.com/lucianozapata/DD2480VT221.git";
    // The local path of the clone destination.
    String projectPath = System.getProperty("user.dir");
    // The project's name
    String repoName = "DD2480VT221";

    /**
     * Test all the methods in CloneRepository class
     */
    @Test
    void testCloneRepository() {
        CloneRepository clone = new CloneRepository(repoUrl, projectPath, repoName);

        // Test if the method can clone a repo to a designated directory
        assertTrue(clone.cloneRepository());

        // Test if the method can check out a remote branch
        assertTrue(clone.checkoutBranch("gh-pages"));
    }

}
