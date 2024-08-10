package com.hogwarts.json_desired_cap.test;

//import com.hs.framework.appium.AppiumBaseTest;
//import com.hs.libs.mobile.AndroidUITasks;
//import com.hs.libs.mobile.IOSUITasks;
//import com.hs.utils.Logger;
//import com.hs.utils.Tools;
import com.hogwarts.json_desired_cap.libs.ContactManagerUITasks;
import com.hogwarts.json_desired_cap.base.AppiumBaseTest;
import com.hogwarts.json_desired_cap.utils.Logger;
import com.hogwarts.json_desired_cap.utils.Tools;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

@Execution(ExecutionMode.SAME_THREAD)
public class MobileTest extends AppiumBaseTest {

    int pausePeriod = 15; //Second

    @ParameterizedTest
    @ValueSource(strings = {"androidEmul1"})
    public void AndroidAutomationTestSample(String deviceName) throws Exception {
        InitDriverByDeviceName(deviceName);
        Logger.info("开始\"" + this.testDisplayName + "\"的测试");

        //workaround 版本问题
        ContactManagerUITasks.clickOKBtnOnConfirmUI(driver);

        ContactManagerUITasks.clickAddContactBtn(driver);

        ContactManagerUITasks.inputContactName(driver);

        ContactManagerUITasks.inputEmail(driver);

        ContactManagerUITasks.clickSaveBtn(driver);

        com.hogwarts.code_desired_cap.base.Tools.wait(2);

        //workaround 权限问题
        ContactManagerUITasks.clickOKBtnOnConfirmUI(driver);

        //workaround close app 问题
        ContactManagerUITasks.clickClosApp(driver);
        com.hogwarts.code_desired_cap.base.Tools.launchApp(androidUid, appPackage, appActivity);

        com.hogwarts.code_desired_cap.base.Tools.wait(2);

        //验证是否回到了首页
        List<WebElement> textViews = driver.findElements(By.className("android.widget.TextView"));
        boolean result = false;
        for (int i = 0; i < textViews.size(); i++) {
            WebElement elem = textViews.get(0);
            String title = elem.getText();
            if (title != null && title.equalsIgnoreCase("Contact Manager")) {
                result = true;
            }
        }
        Assertions.assertTrue(result, "VP: verify back to the main screen");


        Tools.wait(pausePeriod);
    }

    @ParameterizedTest
    @ValueSource(strings = {"android"})
    public void AndroidAutomationTestFailed(String deviceName) throws Exception {

        InitDriverByDeviceName(deviceName);
        Logger.info("开始\"" + this.testDisplayName + "\"的测试");
        Assertions.assertTrue(false, "故意失败演示");
    }
}