package com.group24.CI;

import org.apache.http.HttpResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GitStatusTest {

    @Test
    void sendPost() throws IOException {
        GitStatus s = new GitStatus("persman96", "testtest", "12e2fb7d5232f69535677bbb3487c0ed6078b36a", "success", "", "");
        HttpResponse response = s.sendPost();

        // Assert that the http post request successfuly delivered and created a resource (status)
        //assertEquals(201 , response.getStatusLine().getStatusCode());

    }
}