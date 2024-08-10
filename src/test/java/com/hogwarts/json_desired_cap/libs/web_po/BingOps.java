package com.hogwarts.json_desired_cap.libs.web_po;

import com.hogwarts.json_desired_cap.utils.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BingOps {
    public static void inputText(WebDriver driver,  String keyWords){
        WebElement inputTxt = BingPage.inputTxt(driver);
        Logger.info("输入关键词：" + keyWords);
        inputTxt.sendKeys(keyWords);
    }

    public static void clickSearch(WebDriver driver){
        WebElement searchIcon = BingPage.searchIcon(driver);
        Logger.info("单击查找图标");
        searchIcon.click();
    }
}
