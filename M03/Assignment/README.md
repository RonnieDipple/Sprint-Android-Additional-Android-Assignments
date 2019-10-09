# Android Menus Design

For this app, you'll design an app and then implement the navigation drawer and action bar menu for that app.

## Requirements

This project will be all about polish. I know you know how to replace fragments, change activities or whatever else you'd want to do with a menu item. So I want you to instead spend time making these menus look awesome. Look at some of your favorite apps and see how they utilize these menus and then design your own app to use them.
The following pieces are required, I recommend you build them in order.

### App Design
You'll need to write up a simple design for your app with structure example:

>High Level Description:  
>The Light Controller app will allow users to create custom light settings and store them persistently. It will also allow them to select from a list of previously used settings. Finally the user will be able to configure multiple light controllers and select between them in the settings selection page.  
>
>Individual Activities:
>- Light Selecting (which also allows users to select the controller)
>	- Select from a list of previous light configurations
>	- Shows number of times each configuration has been used
>- Light Configuration
>	- Create or Edit light configurations
>- Controller Configutation
>	- Create or Edit controller configurations
>
>Navigation Drawer:  
>Main Menu: Allow user to select which activity or fragment to view.  
>Secondary Menu: Allow user to toggle night mode for the app  
>Header: Show the user the name of the controller which is selected, show them the name of the active pattern, and have the background color be the base color of the configuration.  
>
>Action Bar Menu:
>- As action, turn off lights
>- As Action, add new light configuration or controller depending on active fragment
>- Toggle display count visibility
>- Allow user to select sort mode on light configurations on page


This writeup will be included at the bottom of this README file. As your next project week will be implementing a project of your design. This can be a starting point for it.

### Menu Implementation
Once you have your app design writeup put together, we'll have you implement a menu system which will simulate the implementation of this flow.
When each item is selected, show a `Toast` telling use what would happen.
> Toasts can be made with the following code `Toast.makeText(context, "My message", Toast.LENGTH_SHORT).show();`

If an item is selected that would replace the current UI fragment or activity, indicate this in the text view that is included in your empty activity layout

## Go Further

There are a few additional features which you can include in order to improve your app

* Polish, polish, polish. Take this time to make your menus and app look awesome. Once the menus are done, build a custom theme and app icons for it as well.
* Start implementing some of the features from your design. First step, instead of just changing the text, build multiple stock fragments and swap them out with the menu selection. Once that is done, you can start adding implementation to those fragments.
