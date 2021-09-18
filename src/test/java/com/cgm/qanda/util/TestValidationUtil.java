package com.cgm.qanda.util;

import com.cgm.qanda.QnAApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.cgm.qanda.TestUtils.generateString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = QnAApplication.class,
        initializers = ConfigFileApplicationContextInitializer.class)
public class TestValidationUtil {
    @Test
    public void testValidateAverageLengthSuccess() {
        String input = "test String";
        boolean validate = ValidationUtil.validateLength(input);

        assertTrue(validate);
    }

    @Test
    public void testValidatateZeroLenthSuccess() {
        String input = "";
        boolean validate = ValidationUtil.validateLength(input);

        assertTrue(validate);
    }

    @Test
    public void testValidatate256LengthLenthSuccess() {
        String input = generateString(256);
        boolean validate = ValidationUtil.validateLength(input);

        assertTrue(validate);
    }

    @Test
    public void testValidatate257LengthLenthFail() {
        String input = generateString(257);
        boolean validate = ValidationUtil.validateLength(input);

        assertFalse(validate);
    }

    @Test
    public void testValidateNullFailed() {
        String input = null;
        boolean validate = ValidationUtil.validateLength(input);

        assertFalse(validate);
    }

    @Test
    public void testValidateAnswerFormat() {
        String input = "this is input " + "\"" + "test";
        boolean validate = ValidationUtil.validateAnswerFormat(input);

        assertTrue(validate);
    }

    @Test
    public void testValidateAnswerFormatFailure() {
        String input = "this is wrong input";
        boolean validate = ValidationUtil.validateAnswerFormat(input);

        assertFalse(validate);
    }
}
