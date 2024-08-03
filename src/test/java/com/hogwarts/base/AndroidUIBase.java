package com.hogwarts.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class AndroidUIBase {
    private Logger logger = Logger.getLogger(AndroidUIBase.class);
    private String appiumURL = "http://127.0.0.1:4723/wd/hub"; //appium 服务URL地址

    private String platformName = "Android"; //平台名称
    private String deviceName = "Android Emulator"; //设备名称(可以是假的)
    protected String appPackage = "com.example.android.contactmanager";//安卓应用包名
    protected String appActivity = appPackage + ".ContactManager"; //安卓activity类
    private String androidUid = Tools.getAndroidDeviceId(); //安卓设备Uid(不能是假的)

    protected AppiumDriver driver;
    protected String testcaseName = "";

    @BeforeEach
    public void begin() throws MalformedURLException {
        //设置Desired Capabilities相关参数
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("platformName", platformName);
        capabilities.setCapability("appium:deviceName", deviceName);

        //设置安卓系统uid
        capabilities.setCapability("appium:udid", androidUid);

        //设置app的主包名和主类名
        capabilities.setCapability("appium:appPackage", appPackage);
        capabilities.setCapability("appium:appActivity", appActivity);
        capabilities.setCapability("appium:resetKeyboard", false);
        capabilities.setCapability("appium:noReset", true);
        capabilities.setCapability("appium:autoAcceptAlerts", true);
        capabilities.setCapability("appium:autoGrantPermissions", true);
        capabilities.setCapability("appium:automationName", "UiAutomator2");

        driver = new AppiumDriver(new URL(appiumURL), capabilities);
        Tools.launchApp(appPackage, appActivity);
        logger.info("Implement drvier instance ...");
    }

    @AfterEach
    public void tearDown(){
        logger.info("Automation test " + testcaseName + " finished.");

        if (driver == null) {
            return;
        }
        driver.quit();
//        Tools.uninstallPackage(androidUid);
    }


}
