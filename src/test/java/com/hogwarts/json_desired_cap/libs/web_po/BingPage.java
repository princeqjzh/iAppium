package com.hogwarts.json_desired_cap.libs.web_po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BingPage {

    public static WebElement inputTxt(WebDriver driver){
        return driver.findElement(By.cssSelector(".sb_form_q"));
    }

    public static WebElement searchIcon(WebDriver driver){
        return driver.findElement(By.id("search_icon"));
    }
}
