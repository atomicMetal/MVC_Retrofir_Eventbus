package com.metalsack.retrobus.utils.imagehandling;

import java.util.List;

/**
 * Created by abc on 7/16/2016.
 */
public class CreateTempImagesFinishedEvent {
    public List<String> bitmapPaths;
    public List<String> originalFilePaths;
    public Exception exception;
}
