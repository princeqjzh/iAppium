package com.hogwarts.base;

import org.apache.log4j.Logger;

/**
 * Created by jizhi.qian on 2019/4/8.
 */
public class UINotFoundException extends Exception {

    private static Logger logger = Logger.getLogger(UINotFoundException.class);

    public UINotFoundException(){

    }

    public UINotFoundException(String errorMsg){
        logger.info(errorMsg);
    }
}
