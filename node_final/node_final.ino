#include <ESP8266WiFi.h>

#include <ESP8266HTTPClient.h>
#include <SoftwareSerial.h>
#include <FirebaseArduino.h>
//#include <ArduinoJson.h>
// #include <ESP8266HTTPClient.h>
 
// Set these to run example.
#define FIREBASE_HOST "uvtesting-67038.firebaseio.com"
//"ppe-reduction.firebaseio.com" 
#define FIREBASE_AUTH "dVwMWK2oa3FCnDUqv1xxkeZREduMAow0iKS6IAPU"
//"4YypjxFOsgh768aV2SHtOPajQr4WWeGsWGM8B9NT"
#define WIFI_SSID "realme 3a"
#define WIFI_PASSWORD "ventriloquista"


String myString;
int vr = A0; // variable resistor connected 
int sdata = 0; // The variable resistor value will be stored in sdata.
 int len = 0; 
 String str = "";
void setup()
{
  // Debug console
  Serial.begin(115200);
  pinMode(vr ,INPUT);
  // connect to wifi.
  pinMode(D0,OUTPUT);
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("connecting");
  while (WiFi.status() != WL_CONNECTED)
      {
    Serial.print(".");
    delay(500);
      }
  Serial.println();
  Serial.print("connected: ");
  Serial.println(WiFi.localIP());
  
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);


}
 
void loop()
{
  
  if(Serial.available()){
    
    int a = Serial.read();
    if (a>48 and a <60)
    {
      char b = a;
      str += b;
      len++;
      Serial.print("string:");
      Serial.println(str);
      Serial.print("len:");
      Serial.println(len);
      
      if(len == 3)
      {
        Firebase.setString("Uv/id",str);
        str = "";
        len = 0;
      }
    } 
  }  
//  delay(1000);            
}
