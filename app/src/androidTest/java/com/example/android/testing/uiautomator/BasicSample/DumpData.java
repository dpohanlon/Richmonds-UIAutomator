/*
 * Copyright 2015, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.testing.uiautomator.BasicSample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import android.os.Bundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.app.Instrumentation;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import androidx.test.filters.SdkSuppress;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.Until;
import androidx.test.uiautomator.UiSelector;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Basic sample for unbundled UiAutomator.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class DumpData {

    private UiDevice mDevice;

    @Test
    public void checkPreconditions() {
        assertThat(mDevice, notNullValue());
    }

    public void resetDateTime() throws Exception {

        mDevice.pressRecentApps();

        mDevice.wait(Until.findObject(By.text("Screenshot")), 10000);

        int startX = mDevice.getDisplayWidth() / 2;
        int startY = (mDevice.getDisplayHeight() / 2) + 400;
        int endX = mDevice.getDisplayWidth() / 2;
        int endY = (mDevice.getDisplayHeight() / 2) - 400;

        mDevice.swipe(startX,startY,endX,endY,8);

        // Start from the home screen
        mDevice.pressHome();

        mDevice.wait(Until.findObject(By.text("Richmonds")), 10000);

        mDevice.swipe(startX,startY,endX,endY, 20);

        mDevice.wait(Until.findObject(By.text("Settings")), 10000);
        mDevice.findObject(new UiSelector().textMatches("^Settings")).click();

        mDevice.wait(Until.findObject(By.text("Battery")), 10000);
        mDevice.swipe(startX,startY + 200,endX,endY - 200, 10);
        mDevice.swipe(startX,startY + 200,endX,endY - 200, 10);
        mDevice.findObject(new UiSelector().textContains("System")).click();

        mDevice.wait(Until.findObject(By.text("Date")), 10000);
        mDevice.findObject(new UiSelector().textContains("Date")).click();

        mDevice.wait(Until.findObject(By.text("Set time")), 10000);
        mDevice.findObject(new UiSelector().textContains("Set time")).click();
        mDevice.findObject(new UiSelector().textContains("Set time")).click();

    }

    @Before
    public void startMainActivityFromHomeScreen() throws Exception {

        // Set device
        mDevice = UiDevice.getInstance(getInstrumentation());

        resetDateTime();

        mDevice.pressHome();

        // Launch the app (and go to the 'Reserve' screen if not there already)
        mDevice.findObject(new UiSelector().textContains("Richmonds")).click();
        mDevice.wait(Until.findObject(By.text("Reserve")), 10000);
        if(new UiSelector().textContains("Reserve a seat") == null) {
            mDevice.findObject(new UiSelector().textContains("Reserve")).click();
        }

    }

    @Test
    public void automateRichmonds() throws Exception {

        String date = "10";
        int gcIdx = 0;
        String source = "Centennial Hotel";

        mDevice.findObject(new UiSelector().textContains("Reserve a seat")).click();
        mDevice.findObject(new UiSelector().textContains("Route")).click();
        mDevice.findObject(new UiSelector().textContains("Wellcome Genome Campus").instance(gcIdx)).click();

        mDevice.findObject(new UiSelector().textContains("Origin")).click();
        mDevice.findObject(new UiSelector().textContains(source)).click();
        mDevice.findObject(new UiSelector().textContains("Wellcome Genome Campus")).click();

        mDevice.findObject(new UiSelector().textContains("Departing")).click();
        mDevice.wait(Until.findObject(By.text(date)), 10000);
        mDevice.findObject(new UiSelector().textMatches("^" + date + "$").instance(0)).click();
        mDevice.findObject(new UiSelector().textContains("Confirm")).click();
        mDevice.findObject(new UiSelector().textContains("Let's go")).click();

        Bundle bundle = new Bundle();

        for (int i = 0; i < 5; i++) {

            UiObject obj = mDevice.findObject(new UiSelector().resourceIdMatches(".*text_capacity.*").instance(i));

            if (!obj.exists()) {
                break;
            }

            String n = obj.getText();
            bundle.putString("N" + (Integer.toString(i)), n);

        }

        Instrumentation ins = getInstrumentation();
        ins.sendStatus(1, bundle);
        ins.addResults(bundle);

    }

    /**
     * Uses package manager to find the package name of the device launcher. Usually this package
     * is "com.android.launcher" but can be different at times. This is a generic solution which
     * works on all platforms.`
     */
    private String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager pm = getApplicationContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }
}
