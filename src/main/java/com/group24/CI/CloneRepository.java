package com.group24.CI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.gradle.internal.impldep.org.apache.commons.io.FileUtils;

/**
 * Class to clone a repo to a designated path
 */
public class CloneRepository {
    private static final Logger logger = Logger.getLogger(CloneRepository.class);
    // The repository url
    String repoUrl;
    // The local path of the clone destination
    String directoryPath;
    // The name of the repository, used as the folder name of the local repo
    String repoName;
    /**
     * Constructor
     *
     * @param repoUrl       url of the repository
     * @param directoryPath path of the destination
     */
    public CloneRepository (String repoUrl, String directoryPath, String repoName) {
        this.repoUrl = repoUrl;
        this.directoryPath = directoryPath;
        this.repoName = repoName;
    }

    /**
     * Clone the repository.
     * @return true if clone succeed, false otherwise
     */
    public boolean cloneRepository() {
        try {
            String repoPath = String.valueOf(Paths.get(directoryPath, repoName));
            FileUtils.deleteDirectory(new File(repoPath));
            System.out.println(repoPath);
            System.out.println("Cloning "+repoUrl+" into "+repoUrl);
            Git.cloneRepository()
                    .setURI(repoUrl)
                    .setDirectory(Paths.get(repoPath).toFile())
                    .call();
           // System.out.println("Completed Cloning");
            logger.info("Completed Cloning");
            return true;
        } catch (GitAPIException e) {
            //System.out.println("Exception occurred while cloning repo");
            logger.error("Exception occurred while cloning repo",e);
            e.printStackTrace();
            return false;
        } catch (IOException e) {
           // System.out.println("Exception occurred while deleting the repository");
            logger.error("Exception occurred while deleting the repository");
            e.printStackTrace();
            return false;
        }
    }


}
