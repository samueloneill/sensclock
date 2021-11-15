#include <Adafruit_NeoPixel.h> //include library for ring LED
#include <LiquidCrystal.h>     //library for LCD

#define LR 6    //pin number 6 connected to IN of ring led

//define pins for switches
#define inPinG 48//switch 1
#define inPinY 40//switch 2
#define inPinR 37//switch 3

//initialize values for switches to 0 so that no garbage value occurs
int val1 = 0;
int val2 = 0;
int val3 = 0;
int counter;

//define total pixels of your led
int pixnum=16;

char BTDataIn;

LiquidCrystal lcd(12, 11, 5, 4, 3, 2); //Set up LCD
Adafruit_NeoPixel RING = Adafruit_NeoPixel(pixnum, LR, NEO_GRB + NEO_KHZ800);        //initialize RING LED

void setup() {
  Serial.begin(115200);
  
  //declare the pins of 3 leds to output    
  pinMode(7, OUTPUT);
  pinMode(8, OUTPUT);
  pinMode(9, OUTPUT);                                          
                                            
  //define pins of switches to INPUT
  pinMode(inPinG, INPUT); 
  pinMode(inPinY, INPUT); 
  pinMode(inPinR, INPUT); 
      
  RING.begin();                    //start LED ring
  RING.setBrightness(25);          //low brightness
  RING.show();
  lcd.begin(16, 2);
}

void loop() { 
  
  //read values of switches
  val1 = digitalRead(inPinG);  
  val2 = digitalRead(inPinY); 
  val3 = digitalRead(inPinR);

                                         
  if (val1 == HIGH) {            //conditional if-else statement to check which switch is on
    lcd.clear();
    delay(50);          
    for(int i=0;i<pixnum;i++){              //to set all the pixels in a ring
      RING.setPixelColor(i, 0, 255, 0);     //set the colour accordingly
      RING.show();                         
      delay(50);  
     }
     lcd.print("Happy");
  } 
  else if(val2 == HIGH){
    lcd.clear();
      for(int i=0; i<pixnum; i++)
          {
            RING.setPixelColor(i, 255, 255, 0);        //red + green makes the colour yellow
            RING.show();                         
            delay(50);  
          }
      lcd.print("OK");
  }
  else if (val3 == HIGH){                         //statements to check conditions for switches
    lcd.clear();
    for(int i=0; i<pixnum; i++)
    {
     RING.setPixelColor(i, 255, 0, 0);            //255 is the maximum brightness 
     RING.show();                         
    delay(50);  
    }
    lcd.print("Sad");
  }

  if (Serial.available()) {
    // wait a bit for the entire message to arrive
    delay(100);
 
    BTDataIn = Serial.read();
    if( BTDataIn == '1' ) greenFunction(); //if the byte is an ASCII 0 execute function 1
 
    //if it is * then we know to expect text input from Android
    if (BTDataIn == '*')
    {
      //clear lcd screen
      lcd.clear();
      // read all the available characters
      while (Serial.available() > 0)
      {
        // display each character to the LCD
        lcd.write(Serial.read());
        Serial.print(
      }
    }
  }
}


/*#include <Adafruit_NeoPixel.h> //library for LED ring
#include <SPI.h> //Libraries for SD card and bluetooth integration
#include <SD.h>

//Define pins 
#define LED_RING 6     //IN of LED ring
#define HAPPY_BUT 2    //Switch 1 (green)
#define OK_BUT 3       //Switch 2 (yellow)
#define SAD_BUT 4      //Switch 3 (red)
#define HAPPY_LED 7
#define OK_LED 8
#define SAD_LED 9

//Define number of LED pixels
int pixNum = 16;

Adafruit_NeoPixel RING = Adafruit_NeoPixel(pixNum, LED_RING, NEO_GRB + NEO_KHZ800);   //Initialize LED ring
File moodFile; //Create a file on SD card to store data

void setup(){
  //Declare the pins as inputs/outputs
  pinMode(HAPPY_LED, OUTPUT);
  pinMode(OK_LED, OUTPUT);
  pinMode(SAD_LED, OUTPUT);
  pinMode(HAPPY_BUT, INPUT_PULLUP); 
  pinMode(OK_BUT, INPUT_PULLUP); 
  pinMode(SAD_BUT, INPUT_PULLUP); 
  RING.begin();             //Initialise LED ring
  
  Serial.begin(115200); //Start serial communication
  SD.begin(4); //Initialise SD card module at chip select pin
  moodFile = SD.open("mood.txt", FILE_WRITE);  //Open a new text file on SD card
}

void loop() {
  //If-else statement to check which switch is on
  if(digitalRead(HAPPY_BUT)==LOW){
    digitalWrite(HAPPY_LED, HIGH);    
    for(int i=0; i<pixNum; i++){            //Set all the pixels in a ring
      RING.setPixelColor(i, 0, 255, 0);     //Set the colour - syntax: RING.setPixelColor(pixel, red, green, blue)
    }
    RING.setBrightness(25);
    RING.show();                          //Display colour
    Serial.print(1);  //Output a 1 to bluetooth module
  }
  else if(digitalRead(OK_BUT)==LOW){
    digitalWrite(OK_LED, HIGH); 
    for(int i=0; i<pixNum; i++){
      RING.setPixelColor(i, 255, 255, 0);    //Red + green makes the colour yellow
    }
    RING.setBrightness(25);
    RING.show();                         
    Serial.print(2);   //Output a 2 to bluetooth module
  }
   else if(digitalRead(SAD_BUT)==LOW){                       //Statements to check conditions for switches
    digitalWrite(SAD_LED, HIGH);  
    for(int i=0; i<pixNum; i++){
      RING.setPixelColor(i, 255, 0, 0);           //255 is the maximum brightness 
    }
    RING.setBrightness(25);
    RING.show();
    Serial.print(3);   //Output a 3 to bluetooth module
   }
}*/
