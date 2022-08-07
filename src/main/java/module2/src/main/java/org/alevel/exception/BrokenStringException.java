package org.alevel.exception;


import java.io.IOException;

public class BrokenStringException extends IOException {
    public BrokenStringException(String message) {
        super(message);
    }
}
