package com.hogwarts.base;

import org.apache.log4j.Logger;

public class UINotFoundException extends Exception {

    private static Logger logger = Logger.getLogger(UINotFoundException.class);

    public UINotFoundException(){

    }

    public UINotFoundException(String errorMsg){
        logger.info(errorMsg);
    }
}
