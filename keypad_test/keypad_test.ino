#include <Keypad.h>
#include<LiquidCrystal.h>

LiquidCrystal lcd(12, 11, 5, 4, 3, 2);  // sets the interfacing pins

const byte ROWS = 4; 
const byte COLS = 4; 
int len = 0;
char hexaKeys[ROWS][COLS] = {
  {'1', '2', '3', 'A'},
  {'4', '5', '6', 'B'},
  {'7', '8', '9', 'C'},
  {'*', '0', '#', 'D'}
};

byte rowPins[ROWS] = {9, 8, 7, 6}; 
byte colPins[COLS] = {17, 16, 15, 14}; 

Keypad customKeypad = Keypad(makeKeymap(hexaKeys), rowPins, colPins, ROWS, COLS); 

void setup(){
  Serial.begin(115200);
  lcd.begin(16, 2);  // initializes the 16x2 LCD
  lcd.setCursor(0,0);
}
  
void loop(){
  char customKey = customKeypad.getKey();
  
  if (customKey){
    len++;
    Serial.println(customKey);
    
    lcd.print(customKey);
    if(len == 3)
    {
      len = 0;
      delay(1000);
      lcd.clear();
      lcd.setCursor(0,0);
      lcd.print("Sanitizing with");
      lcd.setCursor(0,1);
      lcd.print("UV: ");
//      lcd.setCursor(0,1);
      for(int i = 5; i>0; i--)
      {
         lcd.setCursor(3,5);
          lcd.print(i);
          delay(1000);
      }
      lcd.clear();
      lcd.setCursor(0,5);
      lcd.print("Done!");
      delay(2000);
      lcd.clear(); 
    }
  }
}
