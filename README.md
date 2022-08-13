# UIAutomator for the Richmonds app

```shell
adb install -r -g app/build/outputs/apk/debug/app-debug.apk
```

```shell
adb shell am instrument -w  com.example.android.testing.uiautomator.BasicSample.test/androidx.test.runner.AndroidJUnitRunner
```
