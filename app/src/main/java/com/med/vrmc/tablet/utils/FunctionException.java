package com.med.vrmc.tablet.utils;

/**
 * Created by raytine on 2018/1/8.
 */

public class FunctionException extends Exception {
    public String s;
    public FunctionException(String s) {
        this.s = s;
    }
}
