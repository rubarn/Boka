# Description of Boka

This is a simple diary app meant for personal use as a workout diary.  
In this readme, you can read what technologies I have used, and see the prototype of the app as well as screenshots of the final product. I will also discuss what I would do to improve the app if it was to be used publicly.

## Technology used
It is developed in Android Studio using Kotlin. For data handling, I utilized Google's free version of Cloud Firestore. This decision was because I did not need many writes and reads, and it was easily available. Some time into the project, some friends and family noted that they wanted to use the app themselves, so I had to extend the functionality to handle multiple users. It was natural to use Google's Firebase Authentication and add functionality for logging in and registering based on an email address and a custom password. I also now had to add some rules to the cloud to prevent users from accessing other users' data.

## Figma prototype
Before the project, I used Figma to sketch a simple prototype. This was primarily to explore color choices and the mapping of components. Here is a link you can use to access the flow of the prototype, and underneath are some screenshots of the prototype.  
[View the prototype](https://www.figma.com/proto/DGHq48vwqGx01fiKz4p9qv/Boka?node-id=0-1&t=7KYJsThaTQYjIiZu-1)

![Figma prototype](figma_prototype.png)

## App screenshots

### Login activity
<img src="Screenshot_loginactivity.png" alt="Screenshot login activity" align="right" width="300">

This is the login activity. If the user is new or logged out, they will be directed to this activity and have to enter the correct information to be sent to the main activity. If the user already is logged in to a session, they skip this and are directed to the main activity.

<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 

---

### Main activity
<img src="Screenshot_mainactivity.png" alt="Screenshot main activity" align="right" width="300">

This is the main activity. It is nothing more than a calendar where the user can choose which date they want to write a post in. The user presses the selected date and is sent to the date activity.

<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 

---

### Date activity

#### With content:
<img src="Screenshot_dateactivity_content.png" alt="Screenshot date activity with content" align="right" width="300">

These next two images show the date activity, both with and without content. A blank date with no content already has an empty post, so the user doesn’t have to press the add-button (plus-sign) before writing. This is also intended to help new users get started in case they miss the add-button.

<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
   
#### Without content:
<img src="Screenshot_dateactivity_nocontent.png" alt="Screenshot date activity without content" align="right" width="300">

This activity also has the functionality that if a post is without user-made content, the post in question will be deleted when the user goes back to the calendar or closes the app. This is to minimize unnecessary data stored. Users can create as many posts as they want for a selected date, and all posts will be numbered as a list.
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 
<br /> 

---

## Ideas for improvement

### Colors
The first thing i will mention is the colors of certain elements, especially text components, such as in the login activity. The text in the input fields for e-mail and password has a dark color which makes the it hard to read against the dark background. this could easily be fixed with a transparent light bakground as used in the date activity. Another solution could be to have the text appear in a lighter color as well, but as the background shifts from light to dark, this could be suboptimal. 

Another thing is the background itself. I was initially going for an app with a dark theme. This was for two reasons, i enjoy dark themes in apps as it is easier on the eyes, and i was also thinking about privacy. In a diary app, you would not want people around you to easily read what you are writing. As this app was originally meant only for my own use, privacy was not the most important part here, but i will not have other gym goers look over my shoulder and see what i benchpress on bad days. It is embarassing.

### Sizing
Some components should be larger in size, such as (again) the text. For example, the dates in the calendar is too small for a crowd with weaker eyesight.

### Component design
This is only about the buttons for login, register and log out. This was thrown in when i sort of felt done with the app, and focused on getting the backend done rather than the design of the buttons. So, i could easily change the design of them, but i won't, because it is my app. :)
