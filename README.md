## Tinder web version
A web version of Tinder, includes most of the features the app has and some extra like having a list of possible matches instead of 1 at a time, shows the position of your matches, shows the last time a certain user was online, how many times was he/she connected (this one is not very reliable though). It also includes a post-like message system.

## Requirements
- Java >= 7
- Basic knowledge of REST
- The project will need to be deployed in a container like Tomcat

## Installation
git clone [https://github.com/muilpp/tinder-web-version.git](https://github.com/muilpp/tinder-web-version.git)

## Configuration
Before using it, you will need your Facebook OAuth token. The easiest way to do so is by using a proxy service like [Charles](https://www.charlesproxy.com/), this [guide](http://jaanus.com/debugging-http-on-an-android-phone-or-tablet-with-charles-proxy-for-fun-and-profit/) is pretty straightforward. 

Once the proxy is set up, open your tinder app, and you'll see the generated traffic coming up. Then, look for the following request: **api.gotinder.com/auth**, and within its body, copy the value of the **facebook_token** parameter and paste it in the object called FACEBOOK_TOKEN inside the TinderClient class.

The last step is build the war and deploy it inside a container of your choice, I've been using Tomcat, but any other java container will do.

## Usage
