## Tinder web version
A web version for the Tinder app

## Requirements
- Java >= 7
- Basic knowledge of REST
- The project is thought to be deployed in a container like Tomcat

## Installation
git clone [https://github.com/muilpp/tinder-web-version.git](https://github.com/muilpp/tinder-web-version.git)

## Configuration
Before using it, you will need your Facebook OAuth token. The easiest way to do so is by usinga proxy service like [Charles](https://www.charlesproxy.com/), this [guide](http://jaanus.com/debugging-http-on-an-android-phone-or-tablet-with-charles-proxy-for-fun-and-profit/) is pretty straightforward. 

Once the proxy is set up, open your tinder app, and you'll see the generated traffic coming up. Then, look for the following request: **api.gotinder.com/auth**, and within its body, copy the value of the **facebook_token** parameter.

## Usage
Paste the facebook token in the object called FACEBOOK_TOKEN inside the TinderClient class.