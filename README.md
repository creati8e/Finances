# Finances - finance control app as technology playground (🛠️)
APKs are hosted on [AppCenter](https://install.appcenter.ms/users/gregamer-gmail.com/apps/finances/distribution_groups/public "AppCenter")
# Tech stack:
- **Kotlin** for all code
- **GitHub Actions** for CI and CD
- **Kotlin serialization** for parsing JSONs
- **Gradle Kotlin DSL** for build scripts configuration
- **Multi module architecture** for faster builds and code separation
- **Clean architecture** for architecture
- **Coroutines** and **Kotlin Flow** for async work
- **Java 8 desugaring** for working with date, time and optional
- **Dagger 2** for DI
- **MVI** for presentation layer
- **Firebase** (Authentication, Firestore) for storing data
- **Jetpack** (Navigation, ViewModel, LiveData, Lifecycle) for
  presentation layer
- **Material Components** for styling UI components and transitions
- **Motion layout** for animating some layouts
- **Coil** for images loading
- **Beagle** for debug menu
- **Strikt** for building assertions in tests
- **Spek** for unit tests
- **MockK** for mocks

# Some other tools:
- [**Gradle Modules Graph visualizer**](https://github.com/vanniktech/gradle-dependency-graph-generator-plugin)
- [**Proguard & R8 Dictionaries Generator**](https://github.com/CleverPumpkin/Proguard-Dictionaries-Generator) - for generating randomized dictionaries for Proguard (and R8)
# Screens
|    Screen     | Remarks                                                                    | How it looks                                             |
|:-------------:|:---------------------------------------------------------------------------|:---------------------------------------------------------|
| Authorization |                                                                            | <img src="screenshots/auth.jpg"  width="250" />          |
|  Onboarding   |                                                                            | <img src="screenshots/onboarding.gif"  width="250" />    |
|   Dashboard   |                                                                            | <img src="screenshots/dashboard.jpg"  width="250" />     |
|    Report     | Filter button is not working,<br> clicking on category shares does nothing | <img src="screenshots/report.jpg"  width="250" />        |
| Money account | Editing money account is not supported yet                                 | <img src="screenshots/money_account.jpg"  width="250" /> |
|     User      |                                                                            | <img src="screenshots/user.jpg"  width="250" />          |
|  Transaction  | Editing balance transaction is not supported yet                           | <img src="screenshots/transaction.jpg"  width="250" />   |
|  Categories   |                                                                            | <img src="screenshots/categories.jpg"  width="250" />    |
And some other screens not listed above