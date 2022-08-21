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

    // List of dates to book
    private List<String> dates = new ArrayList<>();

    // Bus name
    private String bus = "Central Cambridge";

    // To genome campus bus index - CC : 0, EC : 1, NC : 2, SC : 3
    private int gcIdx = 0;

    // Source bus stop (morning)
    private String source = "Centennial Hotel";

    // Destination bus stop (evening)
    private String dest = "Botanic Garden";

    // Source (morning) bus stop departure time
    private String arrivalTime = "08:50";

    // Destination (evening) bus stop departure time
    private String departureTime = "17:15";

    @Test
    public void checkPreconditions() {
        assertThat(mDevice, notNullValue());
    }

//    @Test
    @Before
    public void startMainActivityFromHomeScreen() throws Exception {

        // Get command line args
        Bundle testBundle = InstrumentationRegistry.getArguments();

        // Split string of dates and insert into a list
        String[] datesArg = testBundle.getString("dates").split(",");

        Collections.addAll(dates, datesArg);

        // Fill other args
        if (testBundle.getString("bus") != null){
            bus = testBundle.getString("bus");
        }
        if (testBundle.getString("gcIdx") != null){
            gcIdx = Integer.parseInt(testBundle.getString("gcIdx"));
        }
        if (testBundle.getString("source") != null){
            source = testBundle.getString("source");
        }
        if (testBundle.getString("dest") != null){
            dest = testBundle.getString("dest");
        }
        if (testBundle.getString("arrivalTime") != null){
            arrivalTime = testBundle.getString("arrivalTime");
        }
        if (testBundle.getString("departureTime") != null){
            departureTime = testBundle.getString("departureTime");
        }

        // Debugging

        Bundle bundle = new Bundle();
        bundle.putString("BUS", testBundle.getString("bus"));
        bundle.putString("dates", dates.get(1));
        bundle.putString("gcIdx", String.valueOf(gcIdx));
        bundle.putString("ARRIVAL", testBundle.getString("arrivalTime"));

        Instrumentation ins = getInstrumentation();
        ins.sendStatus(1, bundle);
        ins.addResults(bundle);

        // Set device
        mDevice = UiDevice.getInstance(getInstrumentation());

        // Start from the home screen
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
            mDevice.wait(Until.findObject(By.text(date)), 10000);
            mDevice.findObject(new UiSelector().textContains(date).instance(0)).click();
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
            mDevice.wait(Until.findObject(By.text(date)), 10000);
            mDevice.findObject(new UiSelector().textContains(date).instance(0)).click();
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
