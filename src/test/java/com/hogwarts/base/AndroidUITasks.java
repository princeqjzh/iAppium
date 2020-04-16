package com.hogwarts.base;

import io.appium.java_client.AppiumDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AndroidUITasks {

    private static Logger logger = Logger.getLogger(AndroidUITasks.class);

    /**
     * 单击Save 按钮
     *
     * @param driver
     * @throws Exception
     */
    public static void clickSaveBtn(AppiumDriver driver) throws Exception {
        List<WebElement> btnEles =
                findObjectsByXPath("//android.widget.Button[contains(@resource-id,'contactSaveButton')]", driver);
        btnEles.get(0).click();
        logger.info("Click 'Save' button！");
    }

    /**
     * 单击Add Contact 按钮
     *
     * @param driver
     * @throws Exception
     */
    public static void clickAddContactBtn(AppiumDriver driver) throws Exception {
        List<WebElement> btnEles =
                findObjectsByXPath("//android.widget.Button[contains(@resource-id,'addContactButton')]", driver);
        btnEles.get(0).click();
        logger.info("Click 'Add Contact' button！");
    }

    /**
     * 输入联系姓名
     *
     * @param driver
     * @throws Exception
     */
    public static void inputContactName(AppiumDriver driver) throws Exception {
        List<WebElement> btnEles =
                findObjectsByXPath("//android.widget.EditText[contains(@resource-id,'contactNameEditText')]", driver);
        btnEles.get(0).sendKeys("A San");
        logger.info("Input 'A San' into the Contact Name!");
    }

    /**
     * 输入email
     *
     * @param driver
     * @throws Exception
     */
    public static void inputEmail(AppiumDriver driver) throws Exception {
        List<WebElement> btnEles =
                findObjectsByXPath("//android.widget.EditText[contains(@resource-id,'contactEmailEditText')]", driver);
        btnEles.get(0).sendKeys("asan@example.com");
        logger.info("Input Email!");
    }

    /**
     * 单击问题提示窗口中的OK按钮
     *
     * @param driver
     * @throws Exception
     */
    public static void clickOKBtnOnConfirmUI(AppiumDriver driver) {
        try {
            List<WebElement> btnEles = findObjectsByXPath("//android.widget.Button[contains(@resource-id,'android:id/button1')]", driver, 2);
            btnEles.get(0).click();
            logger.info("Click the OK button！");
        }catch (UINotFoundException uiex){
            logger.info("No confirm dialog.");
        }
    }

    /**
     * 找元素，固定最长等待15秒
     *
     * @param xpath
     * @param driver
     * @return
     * @throws UINotFoundException
     */
    private static List<WebElement> findObjectsByXPath(String xpath, AppiumDriver driver) throws UINotFoundException {
        return findObjectsByXPath(xpath, driver, 5);
    }

    /**
     * 找元素
     *
     * @param xpath   元素的xpath定位
     * @param driver  appium driver
     * @param waitMax 最长等待秒数
     * @return
     * @throws UINotFoundException
     */
    private static List<WebElement> findObjectsByXPath(String xpath, AppiumDriver driver, int waitMax) throws UINotFoundException {
        int size = 0;
        List<WebElement> objs = null;
        long start = System.currentTimeMillis();
        long now = System.currentTimeMillis();
        while (((now - start) < waitMax * 1000) && (size == 0)) {
            Tools.wait(1);
            objs = driver.findElementsByXPath(xpath);
            if (objs != null) {
                size = objs.size();
            }
            now = System.currentTimeMillis();
        }

        if (size == 0) {
            throw new UINotFoundException();
        } else {
            return objs;
        }
    }
}
