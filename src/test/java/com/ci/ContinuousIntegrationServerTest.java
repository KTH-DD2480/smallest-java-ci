package com.ci;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.io.File;

public class ContinuousIntegrationServerTest {

        ContinuousIntegrationServer DEFAULT = new ContinuousIntegrationServer("");
        //Example: Typical test syntax
        @Test
        public void verifyNoExceptionsThrown(){
            DEFAULT.gradleTest();
        }

        @Test
        /**
         * Requirements: See `cleanTarget` documentation
         * Contract:
         *      Precondition:    
         *      Postcondition:   
         */
        public void testEmptyTargetDirCleanup() throws NotDirectoryException{
            new File("./target").mkdirs();

            Path targetPath = Path.of("./target");
            assertTrue(Files.isDirectory(targetPath));
            DEFAULT.cleanTarget();
            assertFalse(Files.isDirectory(targetPath));       
        }

        @Test
        /**
         * Requirements: See `cleanTarget` documentation
         * Contract:
         *      Precondition:    
         *      Postcondition:   
         */
        public void testNoTargetDirCleanup() throws NotDirectoryException{
            new File("./target").mkdirs();
            Path targetPath = Path.of("./target");
            assertTrue(Files.isDirectory(targetPath));
            DEFAULT.cleanBuild();
            assertTrue(Files.isDirectory(targetPath));
            DEFAULT.cleanTarget();
            assertFalse(Files.isDirectory(targetPath));
        }


        @Test
        /**
         * Requirements: See `cleanBuild` documentation
         * Contract:
         *      Precondition:    
         *      Postcondition:   
         */
        public void testBuildDirCleanup() throws NotDirectoryException{
            //Create the directory
            new File("./target/build").mkdirs();

            //Retrieve the path to the directories
            Path buildPath = Path.of("./target/build");
            Path targetPath = Path.of("./target");

            //Check that `build` and `target` is a directory
            assertTrue(Files.isDirectory(buildPath));
            assertTrue(Files.isDirectory(targetPath));

            //Remove the `build` directory
            DEFAULT.cleanBuild();

            //Check that `build` is no longer a valid directory
            //but `target` is.
            assertFalse(Files.isDirectory(buildPath));
            assertTrue(Files.isDirectory(targetPath));

            //Removal of the entire `target` directory after tests
            DEFAULT.cleanTarget();

            //Double check `target` deletion
            assertFalse(Files.isDirectory(targetPath));
        }


        @Test
        /**
         * Requirements: See `cleanTarget` documentation
         * Contract:
         *      Precondition:    
         *      Postcondition:   
         */
        public void testTargetDirSubCleanup() throws IOException, NotDirectoryException {
            //Creates the directories and files
            new File("./target").mkdirs();
            new File("./target/testSubDir").mkdirs();
            new File("./target/test.txt").createNewFile();
            new File("./target/testSubDir/test2.txt").createNewFile();

            //Retrieves the paths of the created directories and files
            Path targetPath = Path.of("./target");
            Path targetSubPath = Path.of("./target/testSubDir");
            Path targetTxt = Path.of("./target/test.txt");
            Path targetSubTxt = Path.of("./target/testSubDir/test2.txt");

            //Checks that the directories work and that the files are writable
            assertTrue(Files.isDirectory(targetPath));
            assertTrue(Files.isDirectory(targetSubPath));
            assertTrue(Files.isWritable(targetTxt));
            assertTrue(Files.isWritable(targetSubTxt));

            //Cleanup the `target` folder
            DEFAULT.cleanTarget();

            //Check that the directories and files are deleted
            assertFalse(Files.isDirectory(targetPath));
            assertFalse(Files.isDirectory(targetSubPath));
            assertFalse(Files.isWritable(targetTxt));
            assertFalse(Files.isWritable(targetSubTxt));
        }


        @Test
        /**
         * Requirements: See `cleanBuild` documentation
         * Contract:
         *      Precondition:    
         *      Postcondition:   
         */
        public void testBuildDirSubCleanup() throws IOException, NotDirectoryException{
            //Creates the directories and files
            new File("./target/build").mkdirs();
            new File("./target/build/testSubDir").mkdirs();
            new File("./target/build/test.txt").createNewFile();
            new File("./target/build/testSubDir/test2.txt").createNewFile();

            //Retrieves the paths of the created directories and files
            Path targetPath = Path.of("./target");
            Path buildPath = Path.of("./target/build");
            Path buildSubPath = Path.of("./target/build/testSubDir");
            Path buildTxt = Path.of("./target/build/test.txt");
            Path buildSubTxt = Path.of("./target/build/testSubDir/test2.txt");

            //Checks that the directories work and that the files are writable
            assertTrue(Files.isDirectory(targetPath));
            assertTrue(Files.isDirectory(buildPath));
            assertTrue(Files.isDirectory(buildSubPath));
            assertTrue(Files.isWritable(buildTxt));
            assertTrue(Files.isWritable(buildSubTxt));
            
            //Cleanup the `Build` folder, but not the `target`
            DEFAULT.cleanBuild();

            //Check that the directories and files are deleted but
            //the target directory remains.
            assertTrue(Files.isDirectory(targetPath));
            assertFalse(Files.isDirectory(buildPath));
            assertFalse(Files.isDirectory(buildSubPath));
            assertFalse(Files.isWritable(buildTxt));
            assertFalse(Files.isWritable(buildSubTxt));

            //Finally clean the target directory
            DEFAULT.cleanTarget();
            assertFalse(Files.isDirectory(targetPath));
        }







}
