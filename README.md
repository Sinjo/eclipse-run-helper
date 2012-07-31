# Eclipse Run Helper

An Eclipse plug-in which provides superior keyboard launching support

## Superior to what?

The current keyboard launching options. This includes:

* Ctrl+F11 or F11 - Run/Debug respectively. By default tries to run your current editor. Can be set to fall back to the last thing you launched or even to only ever run the last thing you launched.
* Alt+Shift+X or Alt+Shift+D - Run/Debug respectively. Gives you a bunch of launch options for your current selection. Seems to remember the last thing you launched for each type if the current selection doesn't fit.

## Superior in what way?

* Provide the most useful launching options. Initial ideas:
    * Your last 5 launches (or some other arbitrary number)
    * Your previous JUnit launch
	* Arbitrary launch config (let the user bind to a key in preferences)
* Provide all of these from one keyboard shortcut which brings up an Alt+Shift+X-esque menu