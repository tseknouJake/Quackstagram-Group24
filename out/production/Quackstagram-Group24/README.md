# Quackstagram

## Description: 
Quackstagram is social media software that lets DACS students share images with each other.
It includes features like user profiles, images posting, likes, followers.
The core of this app revolves around letting users set up and personalize their profiles. 
Users sign up, log in, and add a personal touch with photos and bios, just like they would 
on Instagram. Once that's done, users can start sharing snapshots of their lives by uploading 
images with captions.

## Getting Started:

### Prerequisites:
* You need to have Java 23 in order to use Quackstagram
  You can download it here: https://www.oracle.com/hk/java/technologies/downloads/.
  Follow installation instructions based on which OS you're using.

### Installation:
1. Clone the repo
 ```sh
   git clone https://github.com/tseknouJake/Quackstagram-Group23.git
 ```
 2. Install required packages if prompted
 3. Change git remote url to avoid accidental pushes to base project
```sh
   git remote set-url origin github_username/repo_name
   git remote -v # confirm the changes
```


## Usage:
How to run Quackstagram:
1. **Open the project:**
In your preferred IDE, open the ```Quackstagram-Group23``` folder. Navigate to ```UI/BaseFrame.java``` and click run (generally a triangle at the top right of your IDE). If it doesn't run you haven't installed all the required packages so you will want to follow the error message instructions to get this done.

2. **Sign in or Sign Up:**
New Users: Click the Sign Up button at the bottom of the page.
Existing Users: Enter your username and password to log in.


3. **Creating an Account:**
In the "Sign Up" is where you will make an account. Over here enter your desired username, password, bio and upload your profile picture. The bio is the message users will see when visiting your profile. Remember your username and password, you will need them for future logins. Once signed up you will be automatically signed in.

4. **Home Page:**
The leftmost icon in the navigation bar is the button you use to reach the "home page", this is where you will see the photos posted by users you follow. Over here you can like/unlike users' posts, navigate to the showpost page by clicking an image and also navigate to the posters profile by clicking the users name in the showpost page.

    ![navbar][navbarHomepage]

5. **Explore Page:**
To the immediate left of the centre in the navigation bar is button you use to reach the "explore page", this is where you will see posts of users who you don't follow but might want to, from this page, if you click an image you will go to the showpost page where you can like/dislike and navigate to the posters profile. You are also able to search for users to follow in this page by typing in their username and hitting ```Enter```. If the username you entered doesn't exist, you will be prompted with an error message "No User found"

    ![explorepg][explorepg]

6. **Upload Image:**
In the centre of the navigation bar is the button you use to reach the "Upload Image" page, here you will select an image that you want to post by clicking the "upload image" button. Once you have chosen your image, type an appropriate caption for the image (if you want) then to post the selected image click "Post Image". This will add the post to your profile and appear on the homepage of users who follow you.

    ![uploadimg][uploadimg]

7. **Notifications:**
On the immediate right of the "Upload Image" button in the navigation bar is the button you use to navigate to the "notifications" page. This page is dedicated to showing you who liked your post and when they did so. It also shows you which users followed you and when they did.  

    ![notif][notif]


8. **Your Profile:**
The rightmost icon in the navigation bar is the button you use to navigate to the "Your profile" page. This page is used to keep track of how many people are following you, how many you follow and how many posts you have. This also gives you an overview of your posted images. From this page you can navigate to the "showPost" page by selecting one of your posts. You can also use this page to log out of your account.

    ![yourprof][yourprof]


## Contact:
If you need help or have questions, suggestions or just general feedback, you can follow the contact details below. (he is available 24/7 on WhatsApp)

David Jiménez Lorenzo - +34 689 90 97 63

## Acknowledgements
Thank you to all our fans who have supported us from the beginning and to Shaked, Noa, David and Jake for making this beautiful app.

<!-- Images -->
[navbarHomepage]: readmepics/navbarHomePage.png
[explorepg]: readmepics/explore.png
[uploadimg]: readmepics/upload.png
[notif]: readmepics/notif.png
[yourprof]: readmepics/yourprof.png
