
# Recipe Fetcher Application

A Recipe Fetcher Application that allows users to fetch recipes built by following MVVM Architecture and Jetpack Components.

## Major Highlights

- MVVM Architecture
- Kotlin
- Retrofit
- Coroutines
- LiveData
- Navigation
- Viewbinding
- Room

> [!IMPORTANT]
> Meals Data is been fetched from the MealDB API: https://www.themealdb.com/

## Features Implemented

- Fetching Meals
- Meals Based on Categories
- Save Meals Locally
- Search For Meals

## Dependency Used:

- Recycler View for listing
```
implementation "androidx.recyclerview:recyclerview:1.3.1"
```
- Glide for image loading
```
implementation 'com.github.bumptech.glide:glide:4.15.1'
annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0'
```
- Retrofit for networking
```
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
```
- Android Lifecycle aware component
```
implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
```
- Navigation
```
implementation "androidx.navigation:navigation-fragment-ktx:$nav_version"
implementation "androidx.navigation:navigation-ui-ktx:$nav_version"
```
- ROOM DB
```
implementation "androidx.room:room-runtime:$room_version"
implementation "androidx.room:room-ktx:$room_version"
kapt "androidx.room:room-compiler:$room_version"
```
- Size Unit Library
```
implementation 'com.intuit.sdp:sdp-android:1.1.0'
```

## Complete Project Structure

```
├───activities
├───adapters
├───db
├───fragments
│   └───bottomsheet
├───pojo
├───retrofit
└───viewmodels

```

## Screenshots

<p align="center">
<img src="https://github.com/gunishjain/FoodMVVM/blob/main/screenshots/1.png" width="200" height="400"> 
<img src="https://github.com/gunishjain/FoodMVVM/blob/main/screenshots/2.png" width="200" height="400">
<img src="https://github.com/gunishjain/FoodMVVM/blob/main/screenshots/3.png" width="200" height="400">
</p>



