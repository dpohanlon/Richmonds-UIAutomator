# UIAutomator for the Richmonds app

```shell
adb install -r -g app/build/outputs/apk/debug/app-debug.apk
```

Or build and install using `gradlew`

```shell
./gradlew clean installDebug installDebugAndroidTest
```

(on mac this might require a new JDK version)

```shell
brew tap homebrew/cask-versions
brew install --cask zulu11
```

```shell
adb shell am instrument -w  com.example.android.testing.uiautomator.BasicSample.test/androidx.test.runner.AndroidJUnitRunner
```

With command line args:

```shell
~/Downloads/platform-tools/adb shell am instrument -r -e class com.example.android.testing.uiautomator.BasicSample.ChangeTextBehaviorTest \
-e bus "'Central Cambridge'" \
-e dates "'0,1,2,3'" \
-e gcIdx "'0'" \
-e source "'Centennial Hotel'" \
-e dest "'Botanic Garden'" \
-e arrivalTime "'08:50'" \
-e departureTime "'17:15'" \
-w  com.example.android.testing.uiautomator.BasicSample.test/androidx.test.runner.AndroidJUnitRunner
```
