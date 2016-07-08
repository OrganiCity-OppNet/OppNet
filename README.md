# OrganiCity - OppNet Application v0.2

## Introduction
###### OrganiCity

[OrganiCity](organicity.eu) is a new EU project with € 7.2m in funding that puts people at the centre of the development of future cities.

###### Opportunistic connectivity service

Opportunistic connectivity service is a tool in OrganiCity to enable co-creation of smart citizens. 
The opportunistic network enabler uses smartphones and IoT devices to build a dynamic wireless communication "infrastructure" at an OC partner site. The software component that enables opportunistic communication will allow the smartphones of OC participants and IoT devices to become part of the networking infrastructure at OC partner sites.

## Application Architecture

The application architecture is shown below:

<img src="https://raw.githubusercontent.com/OrganiCity-OppNet/OppNet/master/oppnet.png" width="500">

The major components of the application include an account manager, a data manager, a device manager and a communication manager. Besides, an incentivisation scheme is also introduced here. 

###### Account manager

Account manager handles the federated identity for using the application in accordance with OC platform accounts. To use the application, user has to log in through OC platform and acquire relevant token for further purpose.

###### Data manager

Data manager manages the accounting of the data. It stores data when received or grants data when sent. It also uploads data to Orion context broker when free WiFi is available. 

###### Device manager

Device manager handles the registration of the device by communicating with Orion context broker.

###### Communication manager

It schedules the bluetooth scanning and handles existing communication tasks. It uses callbacks to send data or response to the data manager to help it maintain data record. 

###### Experiment manager

Experiment manager manages the interaction between oppnet and OC experiment platform. It will be developed when the experiment platform comes out.

###### Incentivisation
The incentivisation algorithm is designed based on the selfish mule protocol introduced in this [paper](https://www.google.co.uk/url?sa=t&rct=j&q=&esrc=s&source=web&cd=1&cad=rja&uact=8&ved=0ahUKEwix3qCktMbNAhXFDsAKHWeXCUYQFggfMAA&url=http%3A%2F%2Fieeexplore.ieee.org%2Fxpls%2Fabs_all.jsp%3Farnumber%3D6517116&usg=AFQjCNGuwb4q2gVJcBC2ildR4pA_usTD1A&sig2=SUX10aK4Z4zcO1hJ1_3rzA), which is based on a Backpressure routing protocol. Incentivisation is calculated locally and then relevant data will be transmitted to context broker during the experiment. 

## User Manuel

###### Install the app

The app can be installed either through downloading the [.apk](https://github.com/OrganiCity-OppNet/OppNet/releases/download/0.2/OppNet.apk) or compiling on your local machine. 

Screenshots of the application when running are shown here:

<img src="https://raw.githubusercontent.com/OrganiCity-OppNet/OppNet/master/Screenshot_2016-07-08-15-14-06%5B1%5D.png" width="170">
<img src="https://raw.githubusercontent.com/OrganiCity-OppNet/OppNet/master/Screenshot_2016-07-08-15-14-10%5B1%5D.png" width="170">
<img src="https://raw.githubusercontent.com/OrganiCity-OppNet/OppNet/master/Screenshot_2016-07-08-15-14-14%5B1%5D.png" width="170">
<img src="https://raw.githubusercontent.com/OrganiCity-OppNet/OppNet/master/Screenshot_2016-07-08-15-14-17%5B1%5D.png" width="170">
<img src="https://raw.githubusercontent.com/OrganiCity-OppNet/OppNet/master/Screenshot_2016-07-08-15-14-24%5B1%5D.png" width="170">

There are five main UI:

1. Experiment list UI: it shows the list of all available experiments.
2. Account UI: it enables authorisation of the app.
3. Network UI: it shows the status of the network.
4. Settings UI: it enables basic settings for the app.
5. Experiment UI: it shows details for a certain experiment.
