package com.hogwarts.base;

import org.apache.log4j.Logger;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tools {
    private static Logger logger = Logger.getLogger(Tools.class);

    /**
     * 秒等
     * @param sec
     */
    public static void wait(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取安卓的uid, 如果连接多台设备，则随机抽取一个；
     * @return
     */
    public static String getAndroidDeviceId(){
        String androidId = getRandomAndroidDeviceId();
        logger.info("Android device uid : " + androidId);
        return androidId;
    }

    /**
     * 获取对应安卓的release版本
     * @param uid
     * @return
     */
    public static String getDeviceRelease(String uid){
        String deviceRelease = "";
        List<String> al = runExec("adb -s " + uid + " shell getprop ro.build.version.release");
        if(al != null && al.size() > 0){
            deviceRelease = al.get(0);
        }
        logger.info("Android release version " + deviceRelease + "\n");
        return deviceRelease;
    }

    /**
     * 获取对应安卓的release版本
     * @param uid
     * @return
     */
    public static void uninstallPackage(String uid){
        List<String> al = runExec("adb -s " + uid + " uninstall com.example.android.contactmanager");
        logger.info("Clean the contact manager app.");
    }

    /**
     * 获取随机安卓uid
     * @return
     */
    public static String getRandomAndroidDeviceId(){
        String id = "";

        List<String> al = getAndroidDeviceIDs();
        if(null != al && al.size() > 0){
            int i = new Random().nextInt(al.size());
            id = al.get(i);
        }

        return id;
    }

    /**
     * 获取安卓uid列表
     * @return
     */
    public static List<String> getAndroidDeviceIDs(){
        ArrayList<String> deviceIds = new ArrayList<String>();

        List<String> al = runExec("adb devices");
        if(null != al) {
            for (int i = 1; i < al.size(); i++){
                String tmpStr = al.get(i);
                if(null != tmpStr && tmpStr.contains("device")){
                    tmpStr = tmpStr.replace("device","").trim();
                    deviceIds.add(tmpStr);
                }
            }
        }

        return deviceIds;
    }

    /**
     * 运行命令行
     * @param cmd
     * @return
     */
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
