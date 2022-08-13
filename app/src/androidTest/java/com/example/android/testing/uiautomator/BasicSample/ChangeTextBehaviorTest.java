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

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import androidx.test.filters.SdkSuppress;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
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
public class ChangeTextBehaviorTest {

    private UiDevice mDevice;

    @Test
    public void checkPreconditions() {
        assertThat(mDevice, notNullValue());
    }

//    @Test
    @Before
    public void startMainActivityFromHomeScreen() throws Exception {

        mDevice = UiDevice.getInstance(getInstrumentation());

        mDevice.findObject(new UiSelector().textContains("Richmonds")).click();
        mDevice.wait(Until.findObject(By.text("Reserve")), 10000);
        mDevice.findObject(new UiSelector().textContains("Reserve")).click();

    }

    @Test
    public void automateRichmonds() throws Exception {

        List<String> dates = List.of("8", "9", "10", "11", "12");
        String bus = "Central Cambridge";
        int gcIdx = 0;
        String source = "Centennial Hotel";
        String dest = "Botanic Garden";
        String arrivalTime = "08:50";
        String departureTime = "17:15";

        for (int i = 0; i < dates.size(); i++) {

            String date = dates.get(i);

            // There...

            mDevice.findObject(new UiSelector().textContains("Reserve a seat")).click();
            mDevice.findObject(new UiSelector().textContains("Route")).click();
            mDevice.findObject(new UiSelector().textContains("Wellcome Genome Campus").instance(gcIdx)).click();

            mDevice.findObject(new UiSelector().textContains("Origin")).click();
            mDevice.findObject(new UiSelector().textContains(source)).click();
            mDevice.findObject(new UiSelector().textContains("Wellcome Genome Campus")).click();

            mDevice.findObject(new UiSelector().textContains("Departing")).click();
            mDevice.findObject(new UiSelector().textContains(date)).click();
            mDevice.findObject(new UiSelector().textContains("Confirm")).click();
            mDevice.findObject(new UiSelector().textContains("Let's go")).click();

            mDevice.findObject(new UiSelector().textContains(arrivalTime)).click();

            mDevice.findObject(new UiSelector().textContains("I understand")).click();
            mDevice.findObject(new UiSelector().textContains("Reserve Now")).click();
            mDevice.wait(Until.findObject(By.text("OK")), 10000);
            mDevice.findObject(new UiSelector().textContains("OK")).click();

            // ...and back again

            mDevice.findObject(new UiSelector().textContains("Reserve a seat")).click();
            mDevice.findObject(new UiSelector().textContains("Route")).click();
            mDevice.findObject(new UiSelector().textContains(bus)).click();

            mDevice.findObject(new UiSelector().textContains("Origin")).click();
            mDevice.findObject(new UiSelector().textContains("Wellcome Genome Campus")).click();
            mDevice.findObject(new UiSelector().textContains(dest)).click();

            mDevice.findObject(new UiSelector().textContains("Departing")).click();
            mDevice.findObject(new UiSelector().textContains(date)).click();
            mDevice.findObject(new UiSelector().textContains("Confirm")).click();
            mDevice.findObject(new UiSelector().textContains("Let's go")).click();

            mDevice.findObject(new UiSelector().textContains(departureTime)).click();

            mDevice.findObject(new UiSelector().textContains("I understand")).click();
            mDevice.findObject(new UiSelector().textContains("Reserve Now")).click();
            mDevice.wait(Until.findObject(By.text("OK")), 10000);
            mDevice.findObject(new UiSelector().textContains("OK")).click();

        }

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