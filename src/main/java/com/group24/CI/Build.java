package com.group24.CI;

import org.gradle.tooling.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.*;

/**
 * Class to build gradle project.
 * This classes uses the gradle tooling api.
 */
public class Build {

    private final String repoDir;
    private final String repoName;
    private final String branch;
    private final String commitHash;
    private final String logFilePath;
    private final Boolean success;

    /**
     * Constructor.
     */
    public Build(String repoDir, String repoName, String commitHash, String branch) {
        this.repoDir = repoDir;
        this.repoName = repoName;
        this.commitHash = commitHash;
        this.branch = branch;
        this.success = false;

        // create path and folder to save logs in
        String projectPath = System.getProperty("user.dir");
        String logFolderPath = String.valueOf(Paths.get(projectPath, "history", "logs", this.commitHash));
        File logFolder = new File(logFolderPath);
        if (!logFolder.exists()) logFolder.mkdirs();
        this.logFilePath = String.valueOf(Paths.get(logFolderPath, "build.logs"));
    }

    /**
     * Create output stream to write into file
     */
    private FileOutputStream createLogStream() throws FileNotFoundException {
        File buildOutputLog = new File(this.logFilePath);
        return new FileOutputStream(buildOutputLog);
    }

    /**
     * Create connection to gradle project
     */
    private ProjectConnection getGradleProjectConnection() throws FileNotFoundException {
        // check if the path is a valid directory
        if (!Files.isDirectory(Paths.get(this.repoDir))) {
            throw new FileNotFoundException("Not a valid directory path");
        }
        File repoDir = new File(this.repoDir);
        return GradleConnector.newConnector()
                .forProjectDirectory(repoDir)
                .connect();
    }

    /**
     * Function to build Gradle project
     *
     * @return true if build was successful, otherwise false
     */
    public boolean buildProject() {
        try {
            ProjectConnection connection = getGradleProjectConnection();
            BuildLauncher build = connection.newBuild();
            FileOutputStream logStream = createLogStream();
            build.setStandardOutput(logStream);

            //select tasks to run
            build.forTasks("clean", "build", "test");

            CustomHandler handler = new CustomHandler();

            // run the build for the given task
            build.run(handler);
            connection.close();

            return handler.isSuccess();

        } catch (GradleConnectionException | FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public BuildReport generateBuildReport() {
        return new BuildReport(this.repoName,this.branch, this.commitHash, this.success);
    }
}
