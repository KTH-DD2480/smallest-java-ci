package com.group24.CI;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

/**
 * A class for setting the Github status using the
 * Github REST API
 *
 */
public class GitStatus {

    String owner, repo, sha, state, description, target_url;


    /**
     *
     * @param owner         Owner of the Github repository.
     * @param repo          The name of the Github repository.
     * @param sha           The SHA of the commit.
     * @param state         The status of the commit.
     * @param description   Description of the status.
     * @param target_url    Link to the build on the server.
     */
    GitStatus(String owner, String repo, String sha, String state, String description, String target_url){
        this.owner = owner;
        this.repo = repo;
        this.sha = sha;
        this.state = state;
        this.description = "Build was a: " + state;
        this.target_url = "https://api.github.com/repos/" + owner + "/" + repo + "/commits/" + sha;
    }

    /**
     * A method for sending a post message using Github REST API.
     *
     */
    public HttpResponse sendPost() throws IOException {
        String payload = "{\"owner\": \"" + owner + "\",\"repo\": \"" + repo + "\",\"sha\": \"" + sha +
                "\",\"state\": \"" + state +  "\",\"description\": \"" + description + "\",\"target_url\": \"" + target_url + "\"  }";
        StringEntity entity = new StringEntity(payload,
                ContentType.APPLICATION_FORM_URLENCODED);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("https://api.github.com/repos/" + owner + "/" + repo + "/statuses/" + sha);

        /*
            Needs to set an enviromental variable for the token as "token" in the terminal.
            In linux:
                export token=your_token
            Anyone with write access to the repo can generate their own token.
            https://github.com/settings/tokens
        */
        String finalToken = System.getenv("token");

        request.setHeader(HttpHeaders.CONTENT_TYPE,"application/vnd.github.v3+json");
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + finalToken);

        request.setEntity(entity);

        HttpResponse response = httpClient.execute(request);
        System.out.println(response.getStatusLine().getStatusCode());

        // Return response for test purposes.
        return response;
    }


}
