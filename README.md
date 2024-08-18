# Page Checker

This application checks for the presence of an html attribute in a certain element in a web page, or a specific
condition in a json response from an HTTP endpoint.

When found, it notifies registered users via email.

## Environment variables

- `PORT`: server port on which to run the app (default: `8084`)
- `HTML_JOB_ACTIVE`: flag to indicate if the html checker job is active (default: `false`)
- `JSON_JOB_ACTIVE`: flag to indicate if the json checker job is active (default: `true`)
- `SENDER_PASSWORD`: sender's email password (not necessarily the actual account password - e.g. in the case of gmail,
  this is the so-called "app password")

## Configuration

The general behavior of the application can be configured through the properties in `application.yaml`.

