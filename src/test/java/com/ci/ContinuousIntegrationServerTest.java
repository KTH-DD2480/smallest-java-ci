package com.ci;
import org.junit.Test;
import static org.junit.Assert.*;


public class ContinuousIntegrationServerTest {

        //Example: Typical test syntax
        @Test
        public void exampleMainTest(){
            //Just calls the main method with an epty argument
            try{
                ContinuousIntegrationServer.main(new String[] {});
            }catch(Exception e){
                System.out.println("This will always pass");
            }
            
        }







}

