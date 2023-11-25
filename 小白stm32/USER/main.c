#include "sys.h"
#include "delay.h"
#include "usart.h"
#include "led.h"
#include "ds18b20.h"
#include "oled.h"
#include "stm32f10x.h"                  // Device header
#include "timer.h"
#include "esp8266.h"
#include "onenet.h"
#include "stdio.h"
#include "beep.h"
          
uint8_t a;//С��
uint8_t b;//����
char PUB_BUF[256];//�ϴ����ݵ�buff
const char *devSubTopic[] = {"/mylinxismarthome/sub"};
const char devPubTopic[] = "/mylinxismarthome/pub";
 int main(void)
 {	
    
    short temp;
    float tempVal;
    uint16_t led_Status; //�Ƶ�״̬
    uint16_t beep_Status; //�Ƶ�״̬
	unsigned short timeCount = 0;	//���ͼ������
	
	unsigned char *dataPtr = NULL;
     
    Usart1_Init(115200); //debug����
    Usart2_Init(115200); //stm32-8266����
     
	delay_init();	    //��ʱ������ʼ��	  
	LED_Init();		  	//��ʼ����LED���ӵ�Ӳ���ӿ�
    DS18B20_Init();//��ʼ��DS18B20
    TIM2_Int_Init(4999,7199);
    OLED_Init();  //��ʾģ���ʼ��
    OLED_ShowString(1,3,"WELCOME");
	UsartPrintf(USART_DEBUG, " Hardware init OK\r\n");
     
    ESP8266_Init(); //��ʼ��ESP8266
	while(OneNet_DevLink())			//����OneNET
	delay_ms(500);
    OneNet_Subscribe(devSubTopic,1);
    BEEP =0;				//LED��ʾ����ɹ�
	delay_ms(250);
	BEEP = 1;
    
     while(1)
     {
          if(timeCount % 40 == 0)   //1000 /25 /40 = 1s
          {
               temp = DS18B20_Get_Temp();
               a = temp % 100;
               b = temp /10;
               tempVal = temp * 0.1;
               UsartPrintf(USART_DEBUG,"temp=%0.1f\r\n",tempVal);
               
               if(LED0_STATUS == 0)
               {
                    led_Status = 1;
               }
               else
               {
                    led_Status = 0;
               }
               
                  
               if(LED1_STATUS == 0)
               {
                    beep_Status = 1;
               }
               else
               {
                    beep_Status = 0;
               }

           }
           //LED0=0;
          // LED1=1;
           //delay_ms(300);	 //��ʱ300ms
          // LED0=1;
           //LED1=0;
            
           if(timeCount >= 200)//5000ms/25 == 200
            {
                UsartPrintf(USART_DEBUG, "OneNet_Publish\r\n");
                sprintf(PUB_BUF,"{\"Temp\" : %0.1f,\"Led\" : %d,\"Beep\" : %d}",tempVal,led_Status,beep_Status);
                OneNet_Publish(devPubTopic,PUB_BUF);
                
                timeCount = 0;
                ESP8266_Clear();
           }
            timeCount++;
            dataPtr = ESP8266_GetIPD(2);  //��ʱ15ms
            if(dataPtr != NULL)
            OneNet_RevPro(dataPtr); 
            delay_ms(10);	//��ʱ10ms
	}
 }
 //��ʱ��2�жϷ������
void TIM2_IRQHandler(void)   //TIM2�ж�
{
	if (TIM_GetITStatus(TIM2, TIM_IT_Update) != RESET)  //���TIM2�����жϷ������
		{
            OLED_ShowString(2,1,"temp:");
            OLED_ShowNum(2,6,b,2);
            OLED_ShowString(2,8,".");
            OLED_ShowNum(2,9,a,1);
            TIM_ClearITPendingBit(TIM2, TIM_IT_Update);  //���TIMx�����жϱ�־ 
		}
}




