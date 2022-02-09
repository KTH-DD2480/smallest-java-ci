package com.group24.CI;

import java.nio.file.Paths;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

/**
 * Class to clone a repo to a designated path
 */
public class CloneRepository {

    // The repository url
    String repoUrl;
    // The local path of the clone destination
    String directoryPath;

    /**
     * Constructor
     *
     * @param repoUrl       url of the repository
     * @param directoryPath path of the destination
     */
    public CloneRepository (String repoUrl, String directoryPath) {
        this.repoUrl = repoUrl;
        this.directoryPath = directoryPath;
    }

    /**
     * Clone the repository.
     * @return true if clone succeed, false otherwise
     */
    public boolean cloneRepository() {
        try {
            System.out.println("Cloning "+repoUrl+" into "+repoUrl);
            Git.cloneRepository()
                    .setURI(repoUrl)
                    .setDirectory(Paths.get(directoryPath).toFile())
                    .call();
            System.out.println("Completed Cloning");
            return true;
        } catch (GitAPIException e) {
            System.out.println("Exception occurred while cloning repo");
            e.printStackTrace();
            return false;
        }
    }


}
