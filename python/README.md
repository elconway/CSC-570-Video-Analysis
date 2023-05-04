# Python Example

## Requirement
- This example works with Python >= 3.7
- Install websocket client via  
```sh
pip3 install websocket-client
```
- Install python-dispatch via 
```sh
pip3 install python-dispatch
```

## Before you start

To run the existing example you will need to do a few things.

1. You will need an EMOTIV headset.  You can purchase a headset in our [online
   store](https://www.emotiv.com/)
2. Next, [download and install](https://www.emotiv.com/developer/) the Cortex
   service.  Please note that currently, the Cortex service is only available
   for Windows and macOS.
3. We have updated our Terms of Use, Privacy Policy and EULA to comply with
   GDPR. Please login via the EMOTIV Launcher to read and accept our latest policies
   in order to proceed using the following examples.
4. Next, to get a client id and a client secret, you must connect to your
   Emotiv account on
   [emotiv.com](https://www.emotiv.com/my-account/cortex-apps/) and create a
   Cortex app. If you don't have a EmotivID, you can [register
   here](https://id.emotivcloud.com/eoidc/account/registration/).
5. Then, if you have not already, you will need to login with your Emotiv id in
   the EMOTIV Launcher.
6. Finally, the first time you run these examples, you also need to authorize
   them in the EMOTIV Launcher.

This code is purely an example of how to work with Cortex.  We strongly
recommend adjusting the code to your purposes.

## Cortex Library
- [`cortex.py`](./cortex.py) - the wrapper lib around EMOTIV Cortex API.

## Create record and export to file
- [`record.py`](./record.py) shows how to create record and export data to CSV or EDF format.
- For more details https://emotiv.gitbook.io/cortex-api/records


