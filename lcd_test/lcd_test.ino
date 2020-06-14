#include<LiquidCrystal.h>

LiquidCrystal lcd(12, 11, 5, 4, 3, 2);  // sets the interfacing pins

void setup()
{
 lcd.begin(16, 2);  // initializes the 16x2 LCD
}

void loop()
{
//  lcd.clear();
  lcd.setCursor(0,0);           //sets the cursor at row 0 column 0
  lcd.print("We "); // prints 16x2 LCD MODULE
  lcd.print("will");
  lcd.setCursor(2,5);           //sets the cursor at row 1 column 2
  lcd.print("- Chainsmokers");     // prints HELLO WORLD
}
