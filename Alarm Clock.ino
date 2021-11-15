// Date and time functions using a DS3231 RTC connected via I2C and Wire lib
#include "RTClib.h"
#include <MD_Parola.h>   //LED Matrix libraries
#include <MD_MAX72xx.h>
#include <SPI.h>

RTC_DS3231 rtc;

MD_Parola P = MD_Parola(2, 5, 3, 4);    //Set up matrix pins

void setup () {
  P.begin();
  Serial.begin(9600);

  if (! rtc.begin()) {
    Serial.println("Couldn't find RTC");
    while (1);
  }
  if (rtc.lostPower()) {
    Serial.println("RTC lost power, lets set the time!");
    // following line sets the RTC to the date & time this sketch was compiled
    rtc.adjust(DateTime(F(__DATE__), F(__TIME__)));
    //rtc.adjust(DateTime(2020, 1, 7, 12, 22, 0));
  }
}

void loop () {
    DateTime now = rtc.now();
    char strTime[5];
    sprintf(strTime, "%d : %d", now.hour(), now.minute());
    P.print(strTime);
    
    delay(100);  //1 second delay
}

/*#include "RTClib.h" // NorthernWidget RTC library
#include <DS3231.h>
#include <Wire.h>
#include <MD_Parola.h>
#include <MD_MAX72xx.h>
#include <SPI.h>

DS3231 rtc; //Initialise RTC

//Declare pins
#define SET_ALARM 10  //Button to set alarm 
#define ALARM_TOG 11  //Button to toggle alarm on/off
#define INCREMENT_HR 12  //Button to increment hour by 1
#define INCREMENT_MIN 13  //Button to increment minute by 1
#define SNOOZE 16     //Snooze button
#define ALARM_PIN 15  //Pin set high when alarm is activated
#define TIME_24_HOUR false // 12 hour format
volatile byte alarmFlag = false; // Create flag to indicate if alarm is active

// Initialise functions
void incrementHr();
void incrementMin();
void setAlarm();
void alarmToggle();
void displayTime();
void displayAlarm();
void snooze();

//LED Matrix display configuration
#define MATRIX_DEVICES 4
#define MATRIX_CLK 5
#define MATRIX_DATA 2
#define MATRIX_CS 3
MD_Parola P = MD_Parola(MATRIX_DATA, MATRIX_CLK, MATRIX_CS, MATRIX_DEVICES);

DateTime now = rtc.now(); //Set up function calling for current time or date
DateTime alarm(DateTime(2019, 1, 1, 0, 0, 0)); //Set default alarm time to Jan 1st 2019 at 00:00

/****************************************START PROGRAM********************************************************

void setup() {
  rtc.begin(); //Start RTC
  rtc.adjust(DateTime(F(__DATE__), F(__TIME__))); //Configure default time to time when sketch was compiled
  P.begin();  //Initialise LED matrix
  
  pinMode(ALARM_PIN, OUTPUT);  //Configure alarm pin as output
  pinMode(SET_ALARM, INPUT);   //Configure button pins as inputs
  pinMode(ALARM_TOG, INPUT);
  pinMode(INCREMENT_HR, INPUT);
  pinMode(INCREMENT_MIN, INPUT);
  pinMode(SNOOZE, INPUT);
}

void loop() {
  displayTime();  //Display current time

  if(alarmFlag==true && now.hour()==alarm.hour() && now.minute()==alarm.minute()){  
  digitalWrite(ALARM_PIN, HIGH);   //Turn on alarm when clock time is equal to alarm time
  }
  else{
    digitalWrite(ALARM_PIN, LOW);   //Set alarm pin low if alarm isn't active
  }
  
  if(digitalRead(SNOOZE)==0){    //Snooze function
    digitalWrite(ALARM_PIN, LOW);  //Turn off alarm
    snooze();  //Call snooze function
  }
  if(digitalRead(INCREMENT_HR)==0){    //Check for button presses
    incrementHr();
  }
  if(digitalRead(INCREMENT_MIN)==0){
    incrementMin();
  }
  if(digitalRead(SET_ALARM)==0){
    setAlarm();
  }
  if(digitalRead(ALARM_TOG)==0){
    alarmToggle();
  }
  
  delay(1000);    //Delay for 1 sec, then repeat
  
}

void incrementHr(){
  DateTime current = RTC_DS3231::now(); //Sets variable 'current' to the current time/date
  current = current + TimeSpan(0, 1, 0, 0);  //Adjusts current by +1 hour
  RTC_DS3231::adjust(current);    //Set RTC to new value of current
  displayTime();
  return;
}

void incrementMin(){
  DateTime current = RTC_DS3231::now(); //Sets variable 'current' to the current time/date
  current = current + TimeSpan(0, 0, 1, 0);  //Adjusts current by +1 minute
  RTC_DS3231::adjust(current);    //Set RTC to new value of current
  displayTime();
  return;
}

void setAlarm(){
  while(digitalRead(SET_ALARM)==0){  //While alarm set button is held down
    if(digitalRead(INCREMENT_HR)==0 && alarm.hour()<23){   //Add 1 hour if Increment Hours is pressed
      alarm = alarm + TimeSpan(0, 1, 0, 0);
      displayAlarm();
    }
    else if(digitalRead(INCREMENT_HR)==0 && alarm.hour()>=23){  //Resets hour to 0 if it is 23, to prevent overflowing
      alarm = alarm - TimeSpan(0, 23, 0, 0);
      displayAlarm();
    }
    else if(digitalRead(INCREMENT_MIN)==0 && alarm.minute()<59){   //Add 1 minute if Increment Mins is pressed
      alarm = alarm + TimeSpan(0, 0, 1, 0);
      displayAlarm();
    }
    else if(digitalRead(INCREMENT_MIN)==0 && alarm.minute()>=59){
      alarm = alarm - TimeSpan(0, 0, 59, 0);
      displayAlarm();
    }
  }
  displayTime();   //When set alarm button is released, display time again and return to loop
  return;
}

void alarmToggle(){
  alarmFlag = !alarmFlag;  //Invert alarm flag
  return;
}

void snooze(){
  if(now.hour()==23 && now.minute()==50){      //If snooze pressed at 23:50, alarm is set to 00:00
    alarm = alarm - TimeSpan(0, 23, 50, 0);
  }
  else if(now.hour()<23 && now.minute()>=50){   //If snooze pressed after XX:50, alarm is set 10 mins in future
    alarm = alarm + TimeSpan(0, 1, 0, 0);
    alarm = alarm - TimeSpan(0, 0, 50, 0);
  }
  else{
  alarm = alarm + TimeSpan(0, 0, 10, 0);   //Snooze pressed at any other time - add 10 mins to alarm
  }
  return;
}

void displayAlarm(){
  P.print(alarm.hour());
  P.print(":");
  P.print(alarm.minute());
}

void displayTime(){
  P.print("12:00");
  P.print(now.hour());
  P.print(":");
  P.print(now.minute());
}*/
