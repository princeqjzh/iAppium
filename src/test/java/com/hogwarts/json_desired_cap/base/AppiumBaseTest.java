package com.hogwarts.json_desired_cap.base;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hogwarts.json_desired_cap.utils.Logger;
import com.hogwarts.json_desired_cap.utils.Tools;
import io.appium.java_client.AppiumDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Map;
import java.util.Set;

public abstract class AppiumBaseTest extends BaseTest {
    protected AppiumDriver driver;
    protected String appPackage;
    protected String appActivity;
    protected String androidUid;
    JsonObject config;



    protected void InitDriverByDeviceName(String deviceName) throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        String desiredCapsFullPath = System.getenv("desired_cap");
        config = Tools.readJsonFile(desiredCapsFullPath);
        String remoteURL = Tools.deepGetJsonStringVal(config, new String[]{"mobileapp", "appiumServer"}
                , "http://localhost:4723/wd/hub");
        Logger.info("在本地运行自动化测试");
        JsonObject localDesireCap = Tools.deepGetJsonObjVal(config, new String[]{"mobileapp", "local", deviceName});
        Set<Map.Entry<String, JsonElement>> entries = localDesireCap.entrySet();
        for (Map.Entry<String, JsonElement> entry : entries) {
            String key = entry.getKey();
            String value;
            if (entry.getValue().isJsonObject()) {
                value = entry.getValue().getAsJsonObject().getAsString();
            } else {
                value = entry.getValue().getAsString();
            }
            capabilities.setCapability(key, value);
        }
        Logger.info("Remote URL = " + remoteURL);
        Logger.info("The appium desire capabilities: " + capabilities);
        appPackage = capabilities.getCapability("appium:appPackage").toString();
        appActivity = capabilities.getCapability("appium:appActivity").toString();
        androidUid = capabilities.getCapability("appium:udid").toString();
        driver = new AppiumDriver(new URL(remoteURL), capabilities);
        Tools.launchApp(androidUid, appPackage, appActivity);
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        Logger.info("结束运行，关闭 driver");
        if (driver != null) {
            Logger.info("正在关闭 driver");
            driver.quit();
        } else {
            Logger.info("driver 为空");
        }
        Logger.info("driver 已经关闭");
    }

    @Override
    protected void failedAfter() throws IOException {
        // Take screenshot when test failed
        Logger.info(this.testDisplayName + ": 测试失败, 尝试屏幕截图");
        String imgPath = this.projRootPath + File.separator + "testdata" + File.separator + this.testDisplayName + ".png";
        if(driver != null) {
            String base64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            byte[] decodedBytes = Base64.getDecoder().decode(base64.replace("\n", ""));
            Path file = Paths.get(imgPath);
            Files.createDirectories(file.getParent());
            Files.write(file, decodedBytes);
            Logger.info("保存截图：" + imgPath);
        }else{
            Logger.info("driver 为 null, 无法截图！");
        }
    }
}
