# TestRedditApp


TestRedditApp is an Android application that allows you to fetch posts from Reddit's /top section.
It retrieves the posts along with their associated images and displays relevant information such as
the username of the post's author, the number of comments, and the time elapsed since the post was created.

# Installation

To use TestRedditApp, follow these steps:

- Clone the repository or download the project ZIP file.
- Open the project in Android Studio.
- Make sure the Gradle files are synchronized.
- Build and run the project on an Android emulator or a physical device running Android 5.0 (API level 21) or higher.

## Features

TestRedditApp offers the following features:

- Fetching posts from Reddit's /top section.
- Displaying post images along with their associated details, such as the author's username, the number of comments, and the post's age.
- Integration of Dagger Hilt for dependency injection.
- Utilization of Jetpack LiveData for observing data changes.
- Implementation of the ViewModel architectural pattern (MVVM).
- Utilization of Glide library for efficient image loading and caching.
- Integration of Navigation Components for easy navigation between fragments.
- Utilization of Retrofit library for fetching data from the Reddit API.
- Integration of Paging3 library for smooth pagination functionality.
