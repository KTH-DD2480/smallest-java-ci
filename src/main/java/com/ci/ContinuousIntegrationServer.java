package com.ci;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;

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

    String repOwner;
    String repName;
    String sha;

    enum CommitStatus {
        error,
        failure,
        pending,
        success
    }
    
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
 
    
    private void cloneRepo() {

    }

    private void build() {

    }
    
    private void postStatus(CommitStatus status, String description) throws Exception {
        // API enpoint for setting the commit status  
        URL url = new URL(String.format("https://api.github.com/repos/%s/%s/statuses/%s", repOwner, repName, sha));
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("accept", "application/vnd.github+json"); // Recommended header
        con.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(con.getOutputStream());

        // Add status and description to body:
        out.writeBytes(String.format("\"status\":\"%s\", \"description\":\"%d\"", status, description));
        out.flush();
        out.close();

        // Send request
        con.connect();

        // Check response code
        int code = con.getResponseCode();
        if (code != 200) {
            System.out.println(String.format("Error when setting commit status! (%d)", code));
            throw new Exception(con.getResponseMessage());
        }
        con.disconnect();
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
