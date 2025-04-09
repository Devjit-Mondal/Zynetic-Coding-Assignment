Project Title
# Zynetic Assignment
A Jetpack Compose-based Android application that fetches and displays products from the DummyJSON API with features like error handling, retry logic, loading indicators, and tests.


1. Setup Instructions

1. Clone the repository:

2. Open the project in Android Studio.

3. Sync Gradle:
- Open `build.gradle` files and click on **Sync Now** in the top-right corner.

4. Run the app:
- Select a device/emulator from the **Device Manager**.
- Click on the **Run** button (green triangle) in Android Studio.

5. Test the app:
- Right-click on the `test` directory and select **Run Tests** to execute unit tests.

## Assumptions or Notes

1. The app fetches product data from [DummyJSON API](https://dummyjson.com/docs/products).
2. Internet connectivity is required for API calls to work.
3. The app uses Coil for dynamic image loading.
4. Error handling includes retry logic for transient network issues (up to 3 retries).
5. Loading indicators are displayed while data is being fetched.
6. The app has been tested on Android API levels 24+.

### Known Issues
- Rendering issues may occur on certain emulators due to OpenGL configurations.
- Placeholder images are used if no product images are available.

## Bonus Implementations

1. **Unit Tests**:
   - Tests for ViewModel methods (`fetchProducts`, `fetchProductDetails`) using mocked API responses.
   - Retry logic is verified in tests.

2. **UI Tests**:
   - Basic UI tests for `ProductListScreen` using Compose Test Rules.

3. **Error Handling**:
   - Handles network errors gracefully with user-friendly messages.
   - Includes retry logic for transient failures.

4. **Loading Indicators**:
   - CircularProgressIndicator is displayed while data is being fetched.

5. **Animations** (Optional):
   - Added simple animations for screen transitions using Compose Navigation.




