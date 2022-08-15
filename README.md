# UIAutomator for the Richmonds app

This requires a pre-prepared Android image with the Richmonds app (with a logged in user) on the first page of the home screen.

Start the emulator (here the AVD is called 'Richmonds'):

``shell
emulator -avd Richmonds -no-window -no-audio &
```

If required, build and install the test environment using `gradlew`

```shell
./gradlew clean installDebug installDebugAndroidTest
```

(on mac this might require a new JDK version,

```shell
brew tap homebrew/cask-versions
brew install --cask zulu11
```
)


Run the UIAutomator class with command line args corresponding to a bus configuration:

```shell
adb shell am instrument -r -e class com.example.android.testing.uiautomator.BasicSample.ChangeTextBehaviorTest \
-e bus "'Central Cambridge'" \
-e dates "'0,1,2,3'" \
-e gcIdx "'0'" \
-e source "'Centennial Hotel'" \
-e dest "'Botanic Garden'" \
-e arrivalTime "'08:50'" \
-e departureTime "'17:15'" \
-w com.example.android.testing.uiautomator.BasicSample.test/androidx.test.runner.AndroidJUnitRunner
```
