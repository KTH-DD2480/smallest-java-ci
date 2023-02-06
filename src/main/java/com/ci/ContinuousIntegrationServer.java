package com.ci;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
 
import java.io.IOException;
 
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

/** 
 Skeleton of a ContinuousIntegrationServer which acts as webhook
 See the Jetty documentation for API documentation of those classes.
*/
public class ContinuousIntegrationServer extends AbstractHandler
{  
    final static int GROUP_NUMBER = 31;
    final static int PORT = 8000 + GROUP_NUMBER;

    private String repoURL, branch, dirPath;

    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) 
        throws IOException, ServletException
    {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        System.out.println(target);

        // here you do all the continuous integration tasks
        // for example
        // 1st clone your repository
        // 2nd compile the code
        response.getWriter().println("CI job done");
    }

    //Method for JUnit to initially try
    //with gradle, remove later.
    public void gradleTest(){
        System.out.println("Gradle/JUnit works");
    }
 
    /**
     * Clones the git repository specified by repoURL into the directory specified by dirPath.
     * @param repoUrl The URL of the git repository to clone.
     * @param branch The specific branch of the git repository to be clone.
     * @param dirPath The path to where the repository should be cloned.
     * @return The exit value of the "git clone repoName dirPath" command.
     * @throws IOException
     * @throws InterruptedException
     * 
     */
    private int cloneRepo() throws IOException, InterruptedException{
        /*
         * TODO: Get repoURL, branch from HTTP request and decide and assign specific repository path.
         */
        repoURL = "https://github.com/DD2480-Group31/Continuous-Integration.git";
        dirPath = "./target_repo";
        branch = "Issue#6";
        String[] cmdarr = {"git", "clone", "-b", branch, repoURL, dirPath};
        Process p = Runtime.getRuntime().exec(cmdarr);

        p.waitFor();
        int exitValue = p.exitValue();
        p.destroy();

        return exitValue;
    }

    /**
     * Public method for test visibility. 
     * Clones the git repository specified by repoURL into the directory specified by dirPath.
     * @return Result of cloneRepo.
     * @throws IOException
     * @throws InterruptedException
     */
    public int publicCloneRepo() throws IOException, InterruptedException{
        return cloneRepo();
    }

    private void build() {

    }

    private void report() {

    }

    private void cleanup() {
        
    }

    // used to start the CI server in command line
    public static void main(String[] args) throws Exception
    {    
        Server server = new Server(PORT);
        server.setHandler(new ContinuousIntegrationServer()); 
        server.start();
        server.join();
    }
}
