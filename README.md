# Endpointclosing_homework
# Candidate Name: Shen-Yu Sun 
## Wikipedia - Android client automation - iOS compatible 
This is a comprehensive Automation Framework that I built it from the gorund up.
It is very scalable, followed DRY principle, and ready to expand! It can supports one phone, or multiple phones at the same time. 
Android and iOS. It's very simple to control the UI objects by Page Object Model.
## The tools that I used:
- Development Environemnt: macOS. IDE: IntelliJ
- Phone Tested: Samsung G9+
- Android Version: 10.0
- The language that I used: JAVA
- Appium - It supports Android, and iOS
- TestNG - For test case purpose - Organized the test cases into different classes
- Report - Very basic TestNG report. This can be easily replace by other report lib.
- Maven - Libs management
## Framework Layout
Packages/Classes: 
- Listeners: Test NG TestListener. This class listen to the test actions such as Start, Finish, Pass, Fail, etc
- Screens: Hold all the page objects class/packages.
- TestHelper: TestHelper class included all the constant variables, methods, tools that'll help the tests run
- WikiCore: core of this automation framework. This class included all driver commands, methods, etc
- Configuration.properties: Handle static configuration information
- WikiAppTests: Test cases based on the homework requirements
## Insutrction for running
- Download appium from http://appium.io/ , and make sure server is running on 127.0.0.1:4723. Check configuration file for more info.
- You should run appium-doctor to make sure your environment is Appium ready!  https://github.com/appium/appium-doctor
- Make sure device is connected to usb, and USB debug mode is turn on. Run adb command "adb devices" from your android home to make sure.
- If environemnt setup correctly, then you should be able to kick off test by right-click "endpoint_testing.xml" and Run it within your IDE.
