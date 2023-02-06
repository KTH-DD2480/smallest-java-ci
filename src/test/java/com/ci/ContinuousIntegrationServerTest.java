package com.ci;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;


public class ContinuousIntegrationServerTest {

        ContinuousIntegrationServer DEFAULT = new ContinuousIntegrationServer();
        //Example: Typical test syntax
        @Test
        public void verifyNoExceptionsThrown(){
            DEFAULT.gradleTest();
        }

        @Test
        /*
         * Requirement: cloneRepo successfully runs the "git clone repoName dirPath" command.
         * Precondition (may need update): 
         *      Directory path is valid
         *      Repository URL is valid
         *      The CI server has access to the repository (may clone, has internet access etc.)
         *       
         * Postcondition: 
         *      The exit code is 0.
         */
        public void cloneRepo_Correct_Return() throws IOException, InterruptedException{
            int exitValue = DEFAULT.publicCloneRepo();
            assertEquals(0, exitValue);
        }





}

