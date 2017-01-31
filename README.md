## Tinder web version
A web version of Tinder, includes most of the features the app has and some extras like having a list of possible matches instead of 1 at a time, showing the position of your matches, the people who blocked you, the last time a certain user was online and how many times was he/she connected (this one is not very reliable though). It also includes a post-like message system. It's certainly not the best code I've ever written and when I started this project I never thought I'd publish it, so some skills will be required to make it work.

The purpose of this project is to **make Tinder and its users aware of the problem**, it was never my intention to help people violate the intimacy of other users, so use it at your own risk.

#### Update (2017 / 01 / 27)
I replaced the repo with a new project, it has the same features the old one had, but in the near future it will have some more like changing the location and getting to know who liked you in advance.

#### Update (2016 / 08 / 23)
Finally Tinder fixed the issue of the GPS location, now the response does not contain the coordinates of every match, instead they send back the distance in miles from you.

#### Requirements
- Java >= 8
- Basic knowledge of REST services
- The project will need to be deployed in a container like Tomcat

#### Configuration
Before using it, you will need your Facebook OAuth token. The easiest way to get it is by using a proxy service like [Charles](https://www.charlesproxy.com/), this [guide](http://jaanus.com/debugging-http-on-an-android-phone-or-tablet-with-charles-proxy-for-fun-and-profit/) is pretty straightforward. 

After setting up the proxy, open your tinder app and all the generated traffic will come up. Then, look for the following request: **api.gotinder.com/auth**, and within its body, copy the value of the **facebook_token** parameter. Download this project in your local repository and paste the value in the final variable called FACEBOOK_TOKEN inside the Constants class.

The last step is build the war and deploy it inside a container of your choice, I've been using Tomcat, but any other java container will do.

#### Usage
Once you have your container up and running, browse to **http://localhost:8080/tinder-1.0.0**. The main menu will show up and a bunch of options will be available. So far, only recommendations and matches work, the rest are under development and will be ready soon.

#### Extra features (not yet implemented)
Tinder also provides the feature of changing the user position at will. This is not implemented in this project yet, but you can achieve it if you use [Postman](http://www.getpostman.com/) or any other tool which lets you run requests.

https://api.gotinder.com/user/ping is the endpoint, notice that this is a POST request. It needs two header parameters, the first one is _Content-Type:application/json_ and the second _X-Auth-Token_, the value of which you can find if you inspect the traffic with Charles (as explained above), you'll see every request sent to the tinder api have this very same requests, just copy them. 

Last but not least, add the longitude and latitude to the body of the request, this would be for New York: _{"lon": -74.0564942, "lat": 40.7397855}_. Now run the request, check the response is 200 and then browse again to get more matches. This time they will be from the place you chose.
