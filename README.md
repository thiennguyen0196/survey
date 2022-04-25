# Nimble Survey
Nimble Survey is a simple Android application which uses Nimble API that allow user to login and see the surveys list.
- Splash Screen
- Login screen
- Forgot password screen
- Surveys list screen (with pagination and swipe to refresh)
- Survey detail

## **1. Architecture**
Using Clean Architecture, the application provides a clean way to maintain and easy to scale up as well as writing unit test. It contains 3 layers:
- Data
- Domain
- Presentation

The overview architect look like this:

![image](https://user-images.githubusercontent.com/21035435/69536839-9f4c8e80-0fa0-11ea-85ee-d7823e5a46b0.png)

## **2. Third-party libraries**
Below is the list of third-party libraries that I use in the project:

- **RxJava**
- **RxKotlin**
- **RxAndroid**
- **Retrofit**
- **OkHttp**
- **Dagger Hilt**
- **AndroidX**
- **Shimmer layout**
- **Glide**
- **Timber**
- **Junit**
- **Mockito**
- **Robolectric**
- **MockWebServer**
- **Ktlint**

## **3. Build the project on local**
- Contact Nimble team to get your own `CLIEND_ID` and `CLIENT_SECRET` key.
- After cloning the repo, open Android Studio and update your client id and client secret key in `apikeys.properties` file.
- Run build

Thank you and have a nice day!
