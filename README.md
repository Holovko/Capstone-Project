# Description

Mobile app created as example of using Google Firebase with Google Maps. This app is example of using Firebase. This app is a list of places in the city Kyiv where parents can spend time with their children. Get a short place description and coordinates on map. All places have 5-star rating system and each user can rate places in the catalog.

# Intended User

This app is for mothers and families with children who live in Kyiv

# Features

* Add places to favourite

* Add new places

* Rate places

* Find places on map

# User Interface Mocks

These can be created by hand (take a photo of your drawings and insert them in this flow), or using a program like Photoshop or Balsamiq.

## Screen 1

![image alt text](/pic/image_0.png)

Splash screen. When the app is launched for the first time, the data is loaded from the server

## Screen 2

![image alt text](/pic/image_1.png)

Select rubric, which is loaded from the server side

## Screen 4

![image alt text](/pic/image_2.png)

Show places from the selected rubric on the map

## Screen 5

![image alt text](/pic/image_3.png)

Details about the place, which can be rated or added to favourite

## Screen 6

![image alt text](/pic/image_4.png)![image alt text](/pic/image_5.png)

Add new place

## Tablet Screen 1: Splash Screen

![image alt text](/pic/image_6.png)

## Tablet Screen 2: Select Rubric

![image alt text](/pic/image_7.png)

## Tablet Screen 3: Details

![image alt text](/pic/image_8.png)

## Tablet Screen 4: Add point

![image alt text](/pic/image_9.png)

## Widget

![image alt text](/pic/image_10.png)

# Key Considerations

### How will your app handle data persistence?

I will build a Content Provider and the service that load data from the server to the local database

### Describe any corner cases in the UX.

I didn't see any corner cases

### Describe any libraries you’ll be using and share your reasoning for including them.

com.android.support:cardview-v7: add cardview

com.android.support:appcompat-v7: add actiona bar support
com.android.support:recyclerview :add recyclerview for data list
com.android.support:design: add fab and other feature from
com.squareup.picasso: caching places pic loaded from server
com.google.android.gms:play-services-maps: for maps
com.google.firebase: for the backend part

# Next Steps: Required Tasks

## Task 1: Project Setup

* Create project

* Configure libraries

* Add vcs "bitbucket"

## Task 2: Implement UI for Each Activity and Fragment

* Build UI for SplashActivity

* Build UI for MainActivity

* Build UI for MapActivity

* Build UI for DetailsActivity

* Build UI for AddPointActivity

## Task 3: Firebase connection

Creating connection and data flow app with the Firebase

* Create server data structure

* Decide where to put store pictures

* Organize connection

* Fill test data

## Task 4: Content provider

* Create database structure

* Organize content provider

* Create service for the receiving data

* Create service for the sending data

## Task 5: Add other UI elements and connect data to UI

* Create dialog layout (rating, etc)

* Connect data with ui using loaders

