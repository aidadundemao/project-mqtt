
#include "beep.h"
#include "stm32f10x.h"                  // Device header

/*******************************************************************************
 函数名称: GPIO_BEEP_Init
 函数说明: 蜂鸣器初始化函数 
 输    入: 无
 返 回 值: 无
*******************************************************************************/	   
void GPIO_BEEP_Init(void)
{
 
 GPIO_InitTypeDef  GPIO_InitStructure;
 	
 RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOB, ENABLE);	         //使能GPIOB端口时钟
 
 GPIO_InitStructure.GPIO_Pin = GPIO_Pin_8;				               //BEEP-->PB.8 端口配置
 GPIO_InitStructure.GPIO_Mode = GPIO_Mode_Out_PP; 		           //推挽输出
 GPIO_InitStructure.GPIO_Speed = GPIO_Speed_2MHz;	               //速度为2MHz
 GPIO_Init(GPIOB, &GPIO_InitStructure);	                         //根据参数初始化GPIOB.8
 
 GPIO_ResetBits(GPIOB,GPIO_Pin_8);                               //输出0，关闭蜂鸣器输出
}
