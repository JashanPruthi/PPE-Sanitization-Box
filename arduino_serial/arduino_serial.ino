void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
}

void loop() {
  // put your main code here, to run repeatedly:
  Serial.print("1");
  Serial.print("2");
  Serial.print("3");
  Serial.print("4");
  Serial.print("5");
  Serial.println();
  delay(5000);
//  Serial.println("How you doing!");
}
