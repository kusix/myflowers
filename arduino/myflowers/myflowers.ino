#include "DHT.h"
#define DHT_PIN 2
#define LIGHT_PIN 4
#define DHT_TYPE DHT22


DHT dht(DHT_PIN, DHT_TYPE);
int rate4collect = 5000;
int lightValue = 0;
int MAX_ANALOG_READ_VALUE = 1024;

void setup() {
  Serial.begin(9600); 
  //Serial.println("myflower wakeup!"); 
  dht.begin();
}

void loop() {   
  char buf[10];  
  int cmd = Serial.read();
  if(cmd !=-1 && cmd!= 'C'){
    String info = "{\"cmd\":";
    sprintf(buf, "%d", cmd);
    info += buf;
    //{"cmd":115,"d":{"ati":"Read fail","at":-1,"ahi":"","ah":-1,"l":549}}
    info += ",\"d\":{";
    // read temperature
    float t = dht.readTemperature();
    info += "\"at\":";
    if (isnan(t)) {
      info += "-1";
    } else {          
      sprintf(buf, "%d", t);
      info += buf;
    }
    
    // read humidity
    float h = dht.readHumidity();
    info += ",\"ah\":";
    if (isnan(h) || isnan(t)) {
      info += "-1";
    } else {
      sprintf(buf, "%d", h);
      info += buf;
    }    
    //read light value
    lightValue = MAX_ANALOG_READ_VALUE - analogRead(LIGHT_PIN);
    info += ",\"l\":";
    sprintf(buf, "%d", lightValue);
    info += buf;
    info += "}}";
    Serial.println(info);
    delay(rate4collect);
  }
}
