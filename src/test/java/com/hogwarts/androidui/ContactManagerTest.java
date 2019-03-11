package com.hogwarts.androidui;

import com.hogwarts.base.AndroidUIBase;
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
    public void runTest(){
        testcaseName = "ContactManager安卓自动化测试";
        WebElement el = driver.findElement(By.className("android.widget.Button"));
        el.click();
        logger.info("单击 Add Contact 按钮");

        List<WebElement> textFieldsList = driver.findElementsByClassName("android.widget.EditText");
        textFieldsList.get(0).sendKeys("阿三");
        logger.info("在 Contact Name 中输入 阿三 ...");

        textFieldsList.get(2).sendKeys("asan@example.com");
        logger.info("在 Contact Email中输入 email地址");

        //向下滑动
//        driver.swipe(100, 500, 100, 100, 2);
        driver.findElementByClassName("android.widget.Button").click();
        logger.info("单击 Save 按钮 ...");

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
