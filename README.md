## Tinder web version
A web version for the Tinder app

## Installation
git clone [https://github.com/muilpp/tinder-web-version.git](https://github.com/muilpp/tinder-web-version.git)

## Configuration
Before using it, you will need your facebook oauth token. The easiest way to do so, you'll need a proxy service like [Charles](https://www.charlesproxy.com/), this [guide](http://jaanus.com/debugging-http-on-an-android-phone-or-tablet-with-charles-proxy-for-fun-and-profit/) is pretty straightforward. 

Once the proxy is set up, open your tinder app, and you'll see the generated traffic coming up. Then, look for the following request: _https://api.gotinder.com/auth_, and within the body copy the value of the _facebook_token_ parameter.