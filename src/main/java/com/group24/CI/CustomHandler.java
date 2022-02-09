package com.group24.CI;

import org.gradle.tooling.GradleConnectionException;
import org.gradle.tooling.ResultHandler;

/**
 * Async custom handler with callbacks on the build process0
 * */
public class CustomHandler implements ResultHandler<Object> {

    private boolean success;

    /**
     * Custom handler with callbacks on the build process
     * @return true if build was successful, false otherwise
     * */
    public boolean isSuccess() {
        return success;
    }

    @Override
    public void onComplete(Object o) {
        // TODO replace with logger
        System.out.println("***** Success *****");
        this.success = true;
    }

    @Override
    public void onFailure(GradleConnectionException e) {
        // TODO replace with logger
        System.out.println("***** Success *****");
        System.out.println("***** Failure *****");
        System.out.println(e.getMessage());
        this.success = false;
    }
}
