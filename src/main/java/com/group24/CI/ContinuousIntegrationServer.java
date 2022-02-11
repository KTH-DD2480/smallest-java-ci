package com.group24.CI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.IO;
import org.eclipse.jetty.util.ajax.JSON;
import org.eclipse.jetty.util.ajax.JSONEnumConvertor;
import org.eclipse.jetty.util.ajax.JSONObjectConvertor;
import org.json.JSONObject;


/** 
 Skeleton of a com.group24.CI.ContinuousIntegrationServer which acts as webhook
 See the Jetty documentation for API documentation of those classes.
*/
public class ContinuousIntegrationServer extends AbstractHandler
{
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) 
        throws IOException, ServletException
    {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        String stringObject = IO.toString(request.getReader());
        JSONObject body = new JSONObject(stringObject);

        CloneRepository cloner;
        Build builder;
        History history = History.getHistoryInstance();

        DiscordBot discordBot = new DiscordBot();

        if(request.getMethod() == "POST"){

            // Parse the repository URL
            String repository_url = body.getJSONObject("repository").getString("clone_url");
            // Parse the commit SHA (secure hashing algorithm)
           String commit_hash = body.getString("after");
            //Parse the project name
           String repository_name = body.getJSONObject("repository").getString("full_name");

            // Parse the date & time the push happened.
           String updated_at = body.getJSONObject("repository").getString("updated_at");

           // TODO parse branch
           String repository_branch = "master";

            // create folder to save cloned repos
           String projectPath = System.getProperty("user.dir");
           String repoFolderPath = String.valueOf(Paths.get(projectPath, "repos"));
           String buildProjectPath = String.valueOf(Paths.get(projectPath, "repos", repository_name));

           cloner = new CloneRepository(repository_url, repoFolderPath, repository_name);
           builder = new Build(buildProjectPath, repository_name, commit_hash, repository_branch);

           boolean buildSuccessful=false;
           boolean cloneSuccessful = cloner.cloneRepository();
           // TODO checkout branch

           if (cloneSuccessful) {
               buildSuccessful = builder.buildProject();
               BuildReport report = builder.generateBuildReport();
               history.addReportToHistory(report);
           }

            System.out.println("CLONE: " + cloneSuccessful);
            System.out.println("BUILD: " + buildSuccessful);

            // TODO add github and mail notification
            discordBot.sendMsg("Repository: " + repository_name +
                             " || Commit: " + commit_hash.substring(0,12)
                           + " || Build: " + buildSuccessful);


            // Notify the user if the build whether the build was successful
            GitStatus status = new GitStatus("lucianozapata", repository_name, commit_hash, String.valueOf(buildSuccessful), "", "");
            status.sendPost();

        }



        // Parse the commit message
        //String branch_name = body.getJSONArray("commit").getString("")

        // here you do all the continuous integration task
        // for example
        // 1st clone your repository
        // 2nd compile the code
        response.getWriter().println("CI job done");

    }
 
    // used to start the CI server in command line
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8080);
        server.setHandler(new ContinuousIntegrationServer());

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("./history");
        server.setHandler(resourceHandler);

        server.start();
        server.join();




    }
}
