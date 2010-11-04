#include <LiquidCrystal.h>
#include <math.h>

//LCD Display Pin Definitions
#define lcdRS 52
#define lcdRW 50
#define lcdE 48
#define lcdD0 47
#define lcdD1 45
#define lcdD2 43
#define lcdD3 41
#define lcdD4 39
#define lcdD5 37
#define lcdD6 35
#define lcdD7 33

//LCD Display Dimension Definitions
#define lcdRows 2
#define lcdCols 16

//Initialize the LCD Global Variables

LiquidCrystal lcd(lcdRS, lcdRW, lcdE, lcdD0, lcdD1, lcdD2, lcdD3, lcdD4, lcdD5, lcdD6, lcdD7);

//This initializes the LCD Screen
void initLCDScreen() {
 
  // set up the LCD's number of columns and rows: 
  lcd.begin(lcdRows, lcdCols);
  
  // Print a message to the LCD.
  LCDPrintLine("Initializing", 1, 0);
  LCDPrintLine("LCD Display", 2, 0);
  
}

//Prints a row to the LCD Screen
//Alignment -1,0,1 (left, center, right)
void LCDPrintLine(String text, int row, int alignment) {
  
  //Clear the row to be printed to
  LCDClearLine(row-1);
  
  //If left align
  if (alignment == -1) {
    lcd.setCursor(0, row-1);
    lcd.print(text);
  }
  
  //If center align
  else if (alignment == 0) {
    int leadingSpace = round((lcdCols - text.length()) / 2);
    lcd.setCursor(leadingSpace, row-1);
    lcd.print(text);
  }
  
  //If right align
  else if (alignment == 1) {
    int leadingSpace = round((lcdCols - text.length()));
    lcd.setCursor(leadingSpace, row-1);
    lcd.print(text);
  }
  
  //Otherwise, default to left align
  else{
    lcd.setCursor(0, row-1);
    lcd.print(text);
  }
}

//Clears the specified row on the LCD screen
void LCDClearLine(int row) {
  lcd.setCursor(0, row);
  lcd.print("                ");
}

//Clears the entire LCD screen 
void LCDClearScreen() {
  lcd.clear(); 
}

//print this!
void BryansTestPrint(int value) {
  lcd.print(value);
}
