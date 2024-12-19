# latam_mobile_android

## Getting Started

### Summary of Setup

- Install necessary SDKs and development tools
- Setup Android Studio and create a target device using the emulator within Android Studio
- Launch the application in Android Studio
- Setup a self-managed environment and a cloud tenant using the configuration files provided in `/config-files` as a reference
- Configure the authentication nodes in your journeys to use mobile biometrics
- Update the app code to point to your own tenant URLs

### Install Development Tools

- Install Android Studio and Git, follow the Flutter documentation to install the recommended versions. You should already have Git installed on your laptop, to check the installation version, type `git -v` into the command line.

### Install Flutter

- Download and install Flutter according to the [Flutter documentation](https://docs.flutter.dev/get-started/install/macos/mobile-android#install-the-flutter-sdk).
- Run `flutter doctor` to verify your installation of all necessary components.

### Setup Android Studio and Create Target Device

- Install the necessary components to Android Studio and create a virtual Android device by following the steps in [this document](https://docs.flutter.dev/get-started/install/macos/mobile-android#configure-android-development).

### Launch Application

Once you've cloned this repo, open the project in Android Studio.

Run `flutter pub get` to fetch all necessary packages.

Navigate to Tools -> Device Manager, and start the Android device you've created.

From the top menu bar, click the play button to run main.dart. You can also click Run -> Start Debugging from the top menu. This should launch the application inside of the Device Tools sidebar.

#### Helpful Tips

If you get an error that your gradle version is incompatible, open the project in Android Studio. Upon opening the project, you will be prompted to upgrade gradle and re-sync the project. Click to do so and follow any remaining steps recommended in the UI. Then, you can try running the app again as described above.

If you upgrade gradle and see any errors related to updating the compileSDK version, you can adjust the compileSDK version in the build.gradle file located in the /android/app directory.

### Create and Point to Your Own Environment

You will want to point this application to your own tenant and self-managed environment. In order to do so, you will need to create a self managed environment in encore with IG studio. This application has been tested against version 2024.3.4 of IG Studio. It will need this version or later in order to run properly.

1. In IG, you will need to configure a /transfer route. Please use the demo-app-ig-route-transfer.json file located in /config-files as a reference for for your own route configuration. Keep in mind the route contains a test tenant URL, which will need to be replaced with your own tenant.

2. Then, navigate to your cloud tenant and create an authorization policy. A reference image of the policy is located in /config-files/Authorization-Policy-Transfer-Sample-Flutter-Mobile-App.png.

3. In the cloud tenant, you will want to import the journeys located in /config-files. Please note that these are reference journeys, and you may need to make some adjustments to fit your needs, including adding your [origin domains](https://docs.pingidentity.com/sdks/latest/sdks/use-cases/mobile-biometrics/android/02-node-configurations.html#android-origin-domains) to the WebAuthn journey nodes.

4. Values in the app code will need to be updated to point to your own environment, you'll need to change the URL in /lib/transfer.dart from the test environment (https://alexsm.encore.forgerock.com/ig/transfer?authType=${authnType}) to your own self managed environment URL. Then, you'll need to update the values in /android/app/src/main/res/values/strings.xml from the test tenant (https://openam-bacciambl.forgeblocks.com/am) to your own cloud tenant.

Please Note: In order for the WebAuthn journey nodes to work natively with the app, you will need to ensure the steps in [this document](https://docs.pingidentity.com/sdks/latest/sdks/use-cases/mobile-biometrics/android/index.html) have been followed.

To test the application on an actual Android device, follow the steps outlined in [this document](https://developer.android.com/codelabs/basic-android-kotlin-compose-connect-device#0) to connect via cable or Wi-Fi.

## Additional Resources

A few resources to get you started if this is your first Flutter project:

- [Lab: Write your first Flutter app](https://docs.flutter.dev/get-started/codelab)
- [Cookbook: Useful Flutter samples](https://docs.flutter.dev/cookbook)

For help getting started with Flutter development, view the
[online documentation](https://docs.flutter.dev/), which offers tutorials,
samples, guidance on mobile development, and a full API reference.
