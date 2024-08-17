# Page Checker
This application checks for the presence of an html attribute for a certain element in a web page and, when found, notifies registered users via email.

## Environment variables
- `SENDER_PASSWORD`: sender's email password (not necessarily the actual account password - e.g. in the case of gmail, this is the so-called "app password")
- `PORT`: server port on which to run the app (default: `8084`)
- `HTML_JOB_ACTIVE`: flag to indicate if the html checker job is active (default: `false`)
- `JSON_JOB_ACTIVE`: flag to indicate if the json checker job is active (default: `true`)

## Configuration
The general behavior of the application can be configured through the following properties:
- `page.url`: URL of the page to check
- `page.refresh-interval.ms`: interval between checks
- `page.selector`: CSS selector for the HTML element to look for in the page
- `page.attribute`: attribute that the HTML element must possess in order to trigger notification of the users
- `sender.address`: email address from which the notification will be sent
- `sender.password`: see environment variable SENDER_PASSWORD
- `recipient.addresses`: email addresses that will be notified when the `page.attribute` is found - comma separated

