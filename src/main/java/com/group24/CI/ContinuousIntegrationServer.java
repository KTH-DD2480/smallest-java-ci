package com.group24.CI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.IOException;
 
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.IO;
<<<<<<< HEAD
=======
import org.eclipse.jetty.util.ajax.JSON;
import org.eclipse.jetty.util.ajax.JSONEnumConvertor;
import org.eclipse.jetty.util.ajax.JSONObjectConvertor;
import org.json.JSONObject;

>>>>>>> 1ab44024330b6989de3d7d336ec68106209a4cd3

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
<<<<<<< HEAD
        String body = IO.toString(request.getReader());

        System.out.println(body);



=======
        String stringObject = IO.toString(request.getReader());
        JSONObject body = new JSONObject(stringObject);

        if(request.getMethod() == "POST"){

            // Parse the repository URL
            String repository_url = body.getJSONObject("repository").getString("html_url");
            // Parse the commit SHA (secure hashing algorithm)
            String commit_hash = body.getString("after");
            //Parse the project name
            String repository_name = body.getJSONObject("repository").getString("full_name");

            // Parse the date & time the push happened.
            String updated_at = body.getJSONObject("repository").getString("updated_at");

        }







        // Parse the commit message
        //String branch_name = body.getJSONArray("commit").getString("")


>>>>>>> 1ab44024330b6989de3d7d336ec68106209a4cd3
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
        server.start();
        server.join();
    }
}
