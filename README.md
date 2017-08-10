# Weather
App to show current weather conditions for current location. Main purpose is to use it on the 'always on' tablet on the wall next to the front door.

## Features
* Shows current weather
* Shows color-coded weather forecast for 9 hours

![main_screen](http://simmlemming.github.io/readme_img/weather/main_screen.png)

## Known issues
* Does not actively request current location, but uses last known one
* Does not retry network requests if they fail, also does not show meaningful error messages in these cases
