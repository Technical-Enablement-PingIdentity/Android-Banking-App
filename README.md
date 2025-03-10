# latam_mobile_android

## Getting Started

### Summary of Setup

- Install necessary SDKs and development tools
- Setup Android Studio and create a target device using the emulator within Android Studio
- Launch the application in Android Studio
- Create a Baseline Demo from Encore and configure the tenant and IG using the configuration files provided in `/config-files` as a reference
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

1. In IG, you will need to configure a /transfer route. Please use the demo-app-ig-route-transfer.json file located in /config-files as a reference for your own route configuration. The following values will need to be updated:
   - `baseURI`: Set to `http://baseline-demo:8082`.
   - `tenantCookieName`
   - `amInstanceUrl`
   - `agent.password`: Create an IG Agent in AIC, take the password and base64 encode it.
   - `AmService` values: Set `agent.username` to the IG Agent created. Update the `ssoTokenHeader` and `url` values too.

2. Then, navigate to your cloud tenant and create an authorization policy. A reference image of the policy is located in `/config-files/Authorization-Policy-Transfer-Sample-Flutter-Mobile-App.png`. Make sure that the id of the policy set matches the `PolicyEnforcementFilter.application` value in the IG route in the previous step (in the sample the id is `data` and name is `Baseline-Demo`).

3. In AIC create a Native / SPA Application overriding the following:
   - Take a note of your client ID as it'll be needed later.
   - Sign-in URL `org.forgerock.demo://oauth2redirect`.
   - Token Endpoint Authentication Method to None.

4. In the cloud tenant, you will want to import the journeys located in `/config-files`. Please note that these are reference journeys. For the WebAuthN nodes to work, you must run the app on a physical device. If you can only use an emulator, then you will need to rewire the journeys to avoid the WebAuthn nodes. See later steps for more info on the WebAuthn nodes.

5. Values in the app code will need to be updated to point to your own environment, you'll need to change the following:
    - `lib/transfer.dart`: One reference to the `/transfer` route needs to be updated to match your environment.
    - `android/app/src/main/res/values/strings.xml`: Ensure OAuth2 Client details match what you created in AIC. Update the AM Instance details, including cookie name. Finally, update the asset statement.

6. To get the WebAuthN nodes working you must have a physical Android device to work with. First, follow the first section of this guide until you are required to host an `assetlinks.json` file: [Android Mobile Biometrics](https://docs.pingidentity.com/sdks/latest/sdks/use-cases/mobile-biometrics/android/index.html)

7. Connect to your IG K8s pod ([instructions here](https://pingidentity.atlassian.net/wiki/spaces/SE1/pages/688062515/HOW+TO+ForgeRock+-+SE+Devops+setup+-+Quick+Starting+guide)), and create an `assetlinks.json` file in `/var/ig/config/public` with the required content from the previous guide.

8. Import the IG route `ig-route-01-asset-links.json` that will serve the file at `https://<baseline-url>/.well-known/assetlinks.json`. Verify it works by going directly to that URL and you should see the content of your file.

9. Follow the steps in the second section of the previous guide to configure the WebAuthn nodes, then run the app from your physical device following [these steps](https://developer.android.com/codelabs/basic-android-kotlin-compose-connect-device#0).

#### Helpful Tips

Keep an eye on the console logs for both the mobile app and the IG pod. To detect if the transfer journey worked, there will be a console log from the Android app starting with `Final Reponse { "result": "Transfer complete" ... }`.

If running into issues, try running the journeys without the WebAuthn nodes to narrow down the type of failure. Remember that the WebAuthn nodes will not work on an emulated device as biometrics are required.

## Additional Resources

A few resources to get you started if this is your first Flutter project:

- [Lab: Write your first Flutter app](https://docs.flutter.dev/get-started/codelab)
- [Cookbook: Useful Flutter samples](https://docs.flutter.dev/cookbook)

For help getting started with Flutter development, view the
[online documentation](https://docs.flutter.dev/), which offers tutorials,
samples, guidance on mobile development, and a full API reference.
