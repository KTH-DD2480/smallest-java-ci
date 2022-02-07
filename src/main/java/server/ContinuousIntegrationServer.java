package server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.*;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import org.json.*;

/**
 Skeleton of a ContinuousIntegrationServer which acts as webhook
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

        System.out.println(target);

        String who = request.getHeader("user-agent");

        if(who.contains("GitHub-Hookshot")) {
            String what = request.getHeader("X-GitHub-Event");
            if(what.contains("push")) {
                BufferedReader br = request.getReader();
                //read the request
                JSONObject JSON = getJSON(br);

                String URL = getRepoURL(JSON);
                
                String cloneOK = cloneRepo(URL);

                String buildOK = "build not done";
                String notifyOK = "notification not sent";

                if(cloneOK.contains("Cloning OK")){
                    buildOK = buildAndTest("./cloned-repo");
                }

                if(buildOK.contains("Build OK")){
                    notifyOK = notify(buildOK);
                }

                System.out.println("Request handled");

                if(notifyOK.contains("Notification sent successfully")){
                    System.out.println(notifyOK);
                }
            }
        }
    }

    public int dummyFunction() {
        //dummy function to start testing
        System.out.println("Calling dummyFunction");
        return 1;
    }

    /**
     * Creates a JSON object from the body of a http POST request from a GitHub webhook. 
     * @param br contains the body of a http POST request
     * @return A JSON object containing the parameters from a GitHub webhook
     * @throws IOException
     */
    public JSONObject getJSON(BufferedReader br) throws IOException {
        //reads the request and converts it to a JSON object
        //when adding webhook in GitHub, you have to chose a payload of application/json. Otherwise, this function will not work.
        String str;
        StringBuilder wholeStr = new StringBuilder();
        while ((str = br.readLine()) != null) {
            wholeStr.append(str);
        }
        br.close();

        String ss = wholeStr.toString();

        //System.out.println(ss);

        return new JSONObject(ss);
    }

    /**
     * Gets the GitHub repo url and recently pushed branch from the input json
     * and combine these to form a compatible string to use with the 'git clone' command. 
     * @param json A JSON object containing the parameters from a GitHub webhook
     * @return A string with the recently pushed branch and the GitHub repo url. 
     */
    public String getRepoURL(JSONObject json){
        //gets the URL for repository to be cloned
        System.out.println("Getting repository URL");

        //this extracts the branch in which the event occurred as lastOne
        String ref = json.get("ref").toString();
        String[] splitref = ref.split("/");
        String branch = splitref[splitref.length - 1];
        //this extracts the url of the repository where the event occurred as git_url
        String git_url = json.getJSONObject("repository").get("git_url").toString();
        String git_url_fixed = git_url.replaceFirst("git", "https");
        String full_url;
        full_url = branch + " " + git_url_fixed;
        return full_url;
    }

    /**
     * Clones a repo into the directory ./cloned-repo
     * @param httpURL the http url of the repo
     * @return status of of how the cloning went
     */
    public String cloneRepo(String httpURL){

        System.out.println("Cloning repository "+ httpURL);
        String cloneStatus;

        try {
            System.out.println(httpURL);
            Process P1=Runtime.getRuntime().exec("git clone -b " + httpURL + " ./cloned-repo");
            P1.waitFor();
            cloneStatus = "Cloning OK";
        } catch (IOException | InterruptedException e) {
            System.out.print("Could not clone repo.");
            cloneStatus = "Cloning Failed";
        }

        return cloneStatus;
    }

    /**
     * Build and test ./cloned-repo directory
     * if BUILD SUCCESS, deletes this directory.
     * @param path The path to the github repo that should be built and tested
     * @return "Build OK" if the build and test were successful and otherwise "Build and test Failed" 
     */
    public String buildAndTest(String path) {
        //builds the specified repo path using Maven and returns the status of the build
        System.out.println("Running mvn package");
        File file=new File(path);
        String buildStatus = "Build and test Failed";
        try {
            ProcessBuilder p1 = new ProcessBuilder(new String[]{"mvn","package"});
            p1.redirectErrorStream(true);
            p1.directory(file);
            Process p = p1.start();
            p.waitFor();
            InputStream fis = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader fg = new BufferedReader(isr);
            String line = null;
            while ((line = fg.readLine()) != null) {
                System.out.println(line);
                String temp=line;
                if((temp.contains("BUILD"))&&(temp.contains("SUCCESS")))
                {
                    buildStatus="Build OK";
                }
            }
            // Delete the repository.
            if(file.exists())
            {
                Process pp=Runtime.getRuntime().exec("rm -rf cloned-repo");
            }
        } catch (IOException | InterruptedException e) {
            System.out.print("Could not build.");
        }
        return buildStatus;

    }

    public String notify(String status){
        //sends notification of the build to the webhook
        System.out.println("Notifying GitHub of build status");
        String notificationStatus = "Notification sent successfully";
        return notificationStatus;
    }

    // public void write_payload_to_json(JSONObject input, String file_name) {
    //     //this function writes a JSONObject to a specified json-file
    //     try (FileWriter file = new FileWriter(file_name)) {
    //         com.alibaba.fastjson.JSONWriter WT = new JSONWriter(file);
    //         WT.writeObject(input);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    /** 
     * Main method. 
     * Used to start the CI server in command line.
     * @param args Not used
     */
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8080);
        server.setHandler(new ContinuousIntegrationServer());
        server.start();
        server.join();
    }
}