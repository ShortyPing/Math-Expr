/*
Copyright (c) 2015 - 2022 Michael Steinmötzger
All rights are reserved for this project, unless otherwise
stated in a license file.
*/

package dev.steinmoetzger.mathlang.exceptions;

public class MLCommandError extends Exception {

    public MLCommandError() {
    }

    public MLCommandError(String message) {
        super(message);
    }

    public MLCommandError(String message, Throwable cause) {
        super(message, cause);
    }

    public MLCommandError(Throwable cause) {
        super(cause);
    }

    public MLCommandError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
