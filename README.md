# Page Checker
This application checks for the presence of a html attribute in a web page and, when found, notifies registered users via email.

## Configuration
The general behavior of the application can be configured through the following properties:
- `page.url`: URL of the page to check
- `page.refresh-interval.milliseconds`: interval between checks of the page
- `page.selector`: CSS selector for the HTML element to look for in the page
- `page.attribute`: attribute that the HTML element must possess in order to trigger notification of the users
- `sender.address`: email address from which the notification will be sent
- `sender.password`: sender's password (not necessarily the account password - e.g. in the case of gmail, this is the "app password", usable only by this app)
- `recipient.addresses`: email addresses of the users that will be notified when the `page.attribute` is found - comma separated
