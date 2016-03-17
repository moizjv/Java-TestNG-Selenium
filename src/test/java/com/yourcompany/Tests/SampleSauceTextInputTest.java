package com.yourcompany.Tests;


import com.yourcompany.Pages.*;
import com.yourcompany.Tests.SampleSauceTestBase;
import com.yourcompany.Utils.Statistics;
import org.junit.Test;
import org.omg.CORBA.SystemException;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.UnexpectedException;
import java.util.*;

import static org.junit.Assert.*;


/**
 * Created by mehmetgerceker on 12/7/15.
 */

public class SampleSauceTextInputTest extends SampleSauceTestBase {

    HashMap<String, ArrayList<Double>> execTimes;

    /**
     * Runs a simple test verifying if the email input is functional.
     * @throws InvalidElementStateException
     */
    //@org.testng.annotations.Test(dataProvider = "hardCodedBrowsers")
    public void verifyEmailInputTest(String browser, String version, String os, Method method)
            throws MalformedURLException, InvalidElementStateException, UnexpectedException {
        String emailInputText = "abc@gmail.com";
        WebDriver driver = createDriver(browser, version, os, method.getName());
        // Navigate to the page
        driver.get("https://saucelabs.com/test/guinea-pig");

        // get page object
        GuineaPigPage page = GuineaPigPage.getPage(driver);

        /*
         enterEmailText page is an exposed "service",
             which interacts with the email input field element by sending text to it.
        */
        page.enterEmailText(emailInputText);

        /*
         Assertions should be part of test and not part of Page object.
         Each test should be verifying one piece of functionality (atomic testing)
        */
        assertEquals(page.getEmailText(), emailInputText);

    }

    /**
     * Runs a simple test verifying if the comment input is functional.
     * @throws InvalidElementStateException
     */
    @org.testng.annotations.Test(dataProvider = "hardCodedBrowsers", invocationCount = 100)
    public void verifyCommentInputTest(String browser, String version, String os, Method method)
            throws MalformedURLException, InvalidElementStateException, UnexpectedException {
        String commentInputText = UUID.randomUUID().toString();

        WebDriver driver = createDriver(browser, version, os, method.getName());

        // Navigate to the page
        Long startTime = System.nanoTime();
        driver.get("https://saucelabs.com/test/guinea-pig");
        double time = (System.nanoTime() - startTime)/1000000;
        if (this.execTimes.containsKey("page_load")) {
            this.execTimes.get("page_load").add(time);
        } else {

            this.execTimes.put("page_load", new ArrayList<>(Arrays.asList(time)));
        }

        /*
         enterCommentText page is an exposed "service",
             which interacts with the email input field element by sending text to it.
        */
        startTime = System.nanoTime();
        WebElement comments = driver.findElement(By.id("comments"));
        time = (System.nanoTime() - startTime)/1000000;
        if (this.execTimes.containsKey("comments_find_by_id")) {
            this.execTimes.get("comments_find_by_id").add(time);
        } else {
            this.execTimes.put("comments_find_by_id", new ArrayList<>(Arrays.asList(time)));
        }

        startTime = System.nanoTime();
        comments.sendKeys(commentInputText);
        time = (System.nanoTime() - startTime)/1000000;
        if (this.execTimes.containsKey("comments_send_text")) {
            this.execTimes.get("comments_send_text").add(time);
        } else {
            this.execTimes.put("comments_send_text", new ArrayList<>(Arrays.asList(time)));
        }

        startTime = System.nanoTime();
        WebElement button = driver.findElement(By.id("submit"));
        time = (System.nanoTime() - startTime)/1000000;
        if (this.execTimes.containsKey("button_find_by_id")) {
            this.execTimes.get("button_find_by_id").add(time);
        } else {
            this.execTimes.put("button_find_by_id", new ArrayList<>(Arrays.asList(time)));
        }

        startTime = System.nanoTime();
        button.click();
        time = (System.nanoTime() - startTime)/1000000;
        if (this.execTimes.containsKey("button_click")) {
            this.execTimes.get("button_click").add(time);
        } else {
            this.execTimes.put("button_click", new ArrayList<>(Arrays.asList(time)));
        }

        startTime = System.nanoTime();
        WebElement submittedComments = driver.findElement(By.id("your_comments"));
        time = (System.nanoTime() - startTime)/1000000;
        if (this.execTimes.containsKey("submitted_comments_find_by_id")) {
            this.execTimes.get("submitted_comments_find_by_id").add(time);
        } else {
            this.execTimes.put("submitted_comments_find_by_id", new ArrayList<>(Arrays.asList(time)));
        }

        startTime = System.nanoTime();
        String submittedCommentsText = submittedComments.getText();
        time = (System.nanoTime() - startTime)/1000000;
        if (this.execTimes.containsKey("submitted_comments_get_text")) {
            this.execTimes.get("submitted_comments_get_text").add(time);
        } else {
            this.execTimes.put("submitted_comments_get_text", new ArrayList<>(Arrays.asList(time)));
        }

        assertTrue(submittedCommentsText.endsWith(commentInputText));
    }

    @BeforeClass
    public void logSetup(){
        this.execTimes = new HashMap<>();
    }

    @AfterClass
    public void logTearDown(){
        for (Map.Entry<String, ArrayList<Double>> me: this.execTimes.entrySet()){
            String key = me.getKey();
            ArrayList<Double> data = me.getValue();
            Statistics stats = new Statistics(data);
            System.out.println("start " + key + " data and statistics");
            System.out.println(key + "-> Data: "  + data.toString());
            System.out.println(key + "-> Mean: "  + Double.toString(stats.getMean()));
            System.out.println(key + "-> StdDev: "  + Double.toString(stats.getStdDev()));
            System.out.println(key + "-> Variance: "  + Double.toString(stats.getVariance()));
            System.out.println(key + "-> Median: "  + Double.toString(stats.getMedian()));
            System.out.println("end " + key + " data and statistics");

        }
    }
}
