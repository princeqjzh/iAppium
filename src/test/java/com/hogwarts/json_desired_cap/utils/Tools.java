package com.hogwarts.json_desired_cap.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.log4j.Logger;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Tools {

    private static Logger logger = Logger.getLogger(com.hogwarts.code_desired_cap.base.Tools.class);
    public static JsonObject readJsonFile(String jsonFullPath) {
        JsonParser parser = new JsonParser();
        JsonObject jsonObj = null;
        FileReader reader;
        try {
            reader = new FileReader(jsonFullPath);
            jsonObj = parser.parse(reader).getAsJsonObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return jsonObj;
    }

    public static String deepGetJsonStringVal(JsonObject jsonObj, String[] keyList, String defaultRet) {
        int length = keyList.length;
        int i = 1;
        String ret = null;
        try {
            for (String key : keyList) {
                if (i != length) {
                    jsonObj = jsonObj.getAsJsonObject(key);
                } else {
                    ret = jsonObj.get(key).getAsString();
                }
                i++;
            }
        } catch (Exception e) {
            ret = defaultRet;
        }
        return ret;
    }

    public static JsonObject deepGetJsonObjVal(JsonObject jsonObj, String[] keyList) {
        try {
            for (String key : keyList) {
                jsonObj = jsonObj.getAsJsonObject(key);
            }
        } catch (Exception e) {
            logger.error("No value at the position: " + keyList);
        }
        return jsonObj;
    }

    public static JsonArray deepGetJsonArrayVal(JsonObject jsonObj, String[] keyList) {
        int length = keyList.length;
        int i = 1;
        JsonArray ret = null;
        try {
            for (String key : keyList) {
                if (i != length) {
                    jsonObj = jsonObj.getAsJsonObject(key);
                } else {
                    ret = jsonObj.getAsJsonArray(key);
                }
                i++;
            }
        } catch (Exception e) {
            ret = null;
        }
        return ret;
    }

    public static void wait(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void launchApp(String udid, String packageName, String activityName){
        String cmd = "adb -s " + udid + " shell am start -n " + packageName + "/" + activityName;
        List<String> lines = runExec(cmd);
        for(String line: lines){
            logger.info(line);
        }
    }

    private static List<String> runExec(String cmd) {
        ArrayList<String> output = new ArrayList<String>();
        try {
            logger.debug("cmd = '" + cmd + "'");
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(cmd);
            InputStream stdin = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(stdin);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            logger.debug("This is java runExec output:");
            logger.debug("<OUTPUT>");
            while ((line = br.readLine()) != null) {
                output.add(line);
                logger.debug(line);
            }
            logger.debug("</OUTPUT>");
            int exitVal = proc.waitFor();
            logger.debug("Process exitValue: " + exitVal);
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return output;
    }
}
