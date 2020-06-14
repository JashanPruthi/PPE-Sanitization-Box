void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
}

void loop() {
  // put your main code here, to run repeatedly:
  int a;
  char b;
  if(Serial.available()){
    int a = Serial.read();
    char b = a;
    Serial.println(b);
  }
}
