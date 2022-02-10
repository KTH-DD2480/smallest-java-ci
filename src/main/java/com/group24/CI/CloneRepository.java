package com.group24.CI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.gradle.internal.impldep.org.apache.commons.io.FileUtils;

/**
 * Class to clone a repo to a designated path
 */
public class CloneRepository {

    // The repository url
    String repoUrl;
    // The local path of the clone destination
    String repoPath;
    /**
     * Constructor
     *
     * @param repoUrl       url of the repository
     * @param directoryPath path of the destination
     */
    public CloneRepository (String repoUrl, String directoryPath, String repoName) {
        this.repoUrl = repoUrl;
        repoPath = String.valueOf(Paths.get(directoryPath, repoName));
    }

    public boolean checkoutBranch(String branch) {
        try {
            Repository repo = new FileRepositoryBuilder()
                    .setGitDir(new File(repoPath + "/.git"))
                    .readEnvironment()
                    .findGitDir()
                    .build();
            Git git = new Git(repo);
            git.checkout().setName("origin/" + branch).call();
            System.out.println("Successfully checkout branch " + branch);
            return true;
        } catch (GitAPIException e) {
            System.out.println("Exception occurred while checking remote branch");
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Clone the repository.
     * @return true if clone succeed, false otherwise
     */
    public boolean cloneRepository() {
        try {
            FileUtils.deleteDirectory(new File(repoPath));
            System.out.println(repoPath);
            System.out.println("Cloning "+repoUrl+" into "+repoUrl);
            Git.cloneRepository()
                    .setURI(repoUrl)
                    .setDirectory(Paths.get(repoPath).toFile())
                    .call();
            System.out.println("Completed Cloning");
            return true;
        } catch (GitAPIException e) {
            System.out.println("Exception occurred while cloning repo");
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            System.out.println("Exception occurred while deleting the repository");
            e.printStackTrace();
            return false;
        }
    }


}
