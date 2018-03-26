package com.siwoo.application.domain;

import com.siwoo.application.domain.views.UserSummary;
import org.springframework.util.StringUtils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestUserSummary {


    public static void testUserSummary(UserSummary userSummary) {
        assertNotNull(userSummary);
        assertTrue(StringUtils.hasText(userSummary.getNickname()));
        assertTrue(StringUtils.hasText(userSummary.getEmail()));
        assertNotNull(userSummary.getStatus());
    }

}
