## Inspiration
The rise in demand of the PPE equipment across the globe during this pandemic led to a new type of pollution known as the PPE pollution. Front-line health care workers across the United States report shortages of PPE ranging from gloves, protective gowns, eye wear and face masks. So we got into thinking how to reuse masks and conserve masks for people on the COVID-19 frontlines.

_In a [materials science study of N95 face masks by Stanford medicine](https://stanfordmedicine.app.box.com/v/covid19-PPE-1-2), an efficient disinfection method was found to be UV light treatment (254 nm, 8W, 30 min). And this method can be used for a maximum of 10 times for disinfecting face masks._ 

So, we designed an android application connected to a box fitted with an UV lamp that can guide us through the procedure of disinfecting and reusing face masks under UV treatment for a maximum of 10 times per mask. 

## What it does
All the masks and other PPEs are provided with an unique id. The user places the mask inside the UV sanitization box for 4 minutes and enters the mask id. This also updates the list in the app and starts a timer for 4 minutes. After the end of 4 minutes, user is notified to put the equipment into the isolation chamber for another 72 hours to eradicate any virus still holding on to the equipment. This treatment will ensure that the corona virus is reduced by a significant amount as a [research by “N. Engl. J. Med.”](https://www.nejm.org/doi/full/10.1056/NEJMc2004973) shows that corona virus cannot live on surfaces for more than 72 hours.

## How we built it
The sanitization box has a UV lamp inside which is controlled by the Arduino. Also connected to arduino are a numpad to feed the equipment ID, a LCD screen to display the id and time remaining and a NodeMcu which is connected to a realtime Firebase database. On entering a new ID, Arduino starts a timer and NodeMcu updates the database which is connected to an Android app. The app keeps track of which equipment has been sanitized how many times and how much time is remaining for it to be sanitized. 

_Please note: We used LEDs instead of UV lamps in our prototype as without proper enclosure, exposure to UV light can pose serious health risks._

## Social impact
This method of PPE sanitization and reuse can greatly reduce the number of protective gear that are discarded after single use, help in reducing pollution and also make sure that sudden surge in demands of PPE does not have a huge impact on supply chain. It can educate people on how careful and safe re-usability is possible in these difficult times.

## Challenges we ran into
It was particularly difficult adding a screen, a keypad and NodeMcu to a single Arduino board. Setting up proper serial communication between arduino and nodemcu and formatting properly was a real challenge.

## Accomplishments that we're proud of
This was the first for both of us for working with Firebase and NodeMcu. We are proud that we were able to create a prototype in the time provided. 

## What we learned 
1. Using Firebase
2. Using NodeMcu
3. Learnt how different surfaces affect how long the viruses can survive on them.
4. Learnt how different masks work and how different cleaning processes affect their efficiency.

## What's next for PPE Sanitization Box
Making a prototype using proper UV enclosure and testing it at a local healthcare facility.
