package com.hogwarts.json_desired_cap.base;

import com.hogwarts.json_desired_cap.utils.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.io.IOException;
import java.util.Optional;

/**
 * 测试基础类
 */

public abstract class BaseTest {

    protected String testDisplayName;
    protected String projRootPath;

    @BeforeEach
    public void setUp(TestInfo testInfo) {
        String testMethod = testInfo.getTestMethod().get().getName();
        testDisplayName = testInfo.getDisplayName();
        if (testDisplayName.contains(testMethod)) {
            testDisplayName = testMethod;
        } else {
            testDisplayName = testMethod + "-" + testDisplayName;
        }
        projRootPath = System.getProperty("user.dir");
        Logger.info(testDisplayName + " - Test setUp !");
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        Logger.info(testInfo.getDisplayName() + " - Test tearDown !");
    }

    protected void failedAfter() throws IOException {
    }

    protected void succeededAfter() {
    }

    @RegisterExtension
    AfterTestExecutionCallback afterTestExecutionCallback = new AfterTestExecutionCallback() {
        @Override
        public void afterTestExecution(ExtensionContext context) throws Exception {
            Optional<Throwable> exception = context.getExecutionException();
            if (exception.isPresent()) {
                failedAfter();
            } else {
                succeededAfter();
            }
        }
    };
}
