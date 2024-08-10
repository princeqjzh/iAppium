package com.hogwarts.code_desired_cap.androidui;

import com.hogwarts.code_desired_cap.base.ContactManagerBase;
import com.hogwarts.code_desired_cap.base.ContactManagerUITasks;
import com.hogwarts.code_desired_cap.base.Tools;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ContactManagerTest extends ContactManagerBase {
    private Logger logger = Logger.getLogger(ContactManagerTest.class);

    @Test
    public void runTest() throws Exception {
        testcaseName = "ContactManager android automation test";

        //workaround 版本问题
        ContactManagerUITasks.clickOKBtnOnConfirmUI(driver);

        ContactManagerUITasks.clickAddContactBtn(driver);

        ContactManagerUITasks.inputContactName(driver);

        ContactManagerUITasks.inputEmail(driver);

        ContactManagerUITasks.clickSaveBtn(driver);

        Tools.wait(2);

        //workaround 权限问题
        ContactManagerUITasks.clickOKBtnOnConfirmUI(driver);

        //workaround close app 问题
        ContactManagerUITasks.clickClosApp(driver);
        Tools.launchApp(androidUid, appPackage, appActivity);

        Tools.wait(2);

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

    }
}
