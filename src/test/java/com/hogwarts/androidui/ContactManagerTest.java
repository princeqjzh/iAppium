package com.hogwarts.androidui;

import com.hogwarts.base.AndroidUIBase;
import com.hogwarts.base.AndroidUITasks;
import com.hogwarts.base.Tools;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

/**
 * Created by JiZhi.Qian on 2019/3/11.
 */
public class ContactManagerTest extends AndroidUIBase {
    private Logger logger = Logger.getLogger(ContactManagerTest.class);

    @Test
    public void runTest() throws Exception {
        testcaseName = "ContactManager安卓自动化测试";

        AndroidUITasks.clickAddContactBtn(driver);

        AndroidUITasks.inputContactName(driver);

        AndroidUITasks.inputEmail(driver);

        AndroidUITasks.clickSaveBtn(driver);

        Tools.wait(2);

        //workaround 权限问题
        AndroidUITasks.clickOKBtnOnConfirmUI(driver);

        Tools.wait(2);

        //验证是否回到了首页
        List<WebElement> textViews = driver.findElementsByClassName("android.widget.TextView");
        boolean result = false;
        for(int i = 0; i < textViews.size(); i++){
            WebElement elem = textViews.get(0);
            String title = elem.getText();
            if(title != null && title.equalsIgnoreCase("Contact Manager")){
                result = true;
            }
        }
        Assert.assertTrue("校验测试结果：验证返回了首页。",result);

    }
}
