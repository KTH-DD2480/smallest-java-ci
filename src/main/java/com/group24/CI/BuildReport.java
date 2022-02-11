package com.group24.CI;

import java.util.Date;
import java.util.Objects;

/**
 * Class representing a build report
 */
public class BuildReport {

    String commit;
    String branch;
    String repoName;
    String buildDate;
    Boolean buildSuccess;

    /**
     * Constructor.
     *
     * @param repoName name of the project.
     * @param branch current branch of the repo.
     * @param commit    hash of the commit.
     */
    public BuildReport(String repoName, String branch, String commit, Boolean success) {
        this.repoName = repoName;
        this.branch = branch;
        this.commit = commit;
        this.buildDate = String.valueOf(new Date());
        this.buildSuccess = success;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof BuildReport)) {
            return false;
        }
        BuildReport otherReport = (BuildReport) other;
        return Objects.equals(this.commit, otherReport.commit)
                && Objects.equals(this.buildDate, otherReport.buildDate)
                && Objects.equals(this.repoName, otherReport.repoName)
                && Objects.equals(this.branch, otherReport.branch);
    }
}
