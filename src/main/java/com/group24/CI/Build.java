package com.group24.CI;

import org.gradle.tooling.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.Objects;

/**
 * Class to build gradle project.
 * This classes uses the gradle tooling api.
 */
public class Build {

    private final String projectDir;
    private final String projectName;
    private final String logPath;
    private final String commitHash;
    private final String buildDir;
    private final boolean buildStarted;

    /**
     * Constructor.
     *
     * @param projectDir  path of the gradle project.
     * @param projectName name of the gradle project.
     * @param commitHash  hash of the commit (determines folder name).
     * @param buildDir    path where the build artifacts are saved.
     */
    public Build(String projectDir, String projectName, String commitHash, String buildDir) {
        this.projectDir = projectDir;
        this.projectName = projectName;
        this.commitHash = commitHash;
        this.buildDir = buildDir;
        this.logPath = String.valueOf(Paths.get(projectDir,"/build.log"));
        this.buildStarted = false;
    }

    /**
     * Create output stream to write into file
     */
    private FileOutputStream createLogStream() throws FileNotFoundException {
        File buildOutputLog = new File(this.logPath);
        return new FileOutputStream(buildOutputLog);
    }

    /**
     * Create connection to gradle project
     */
    private ProjectConnection getGradleProjectConnection() throws FileNotFoundException {
        // check if the path is a valid directory
        if (!Files.isDirectory(Paths.get(this.projectDir))) {
            throw new FileNotFoundException("Not a valid directory path");
        }
        File repoDir = new File(this.projectDir);
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
}
