//const app = getAPP()
const { connect } = require('../../utils/mqtt')
const mqttHost = 'broker.emqx.io' //mqtt服务器域名
const mqttPort = 8084 //mqtt服务器端口

const deviceSubTopic = '/mylinxismarthome/sub'  //设备订阅Topic（小程序发布命Topic）
const devicePubTopic = '/mylinxismarthome/pub'  //设备发布Topic（小程序订阅Topic）

const mpSubTopic = devicePubTopic
const mpPubTopic = deviceSubTopic
Page({
  data: {
    client: null,
    Temp : 0,
    Hum : 0,
    Light : 0,
    Led : false,
    Beep : false
  },
  //处理客厅灯
  onLedChange(event){
    const that = this
    console.log(event.detail.value);
    const sw = event.detail.value
    that.setData({Led:sw})
    if(sw){
      that.data.client.publish(mpPubTopic,JSON.stringify({
        target:"LED",
        value:1
      }),function(err){
        if(!err){
          console.log('成功下发指令--开灯');
        }
      })
    }else{
      that.data.client.publish(mpPubTopic,JSON.stringify({
        target:"LED",
        value:0
      }),function(err){
        if(!err){
          console.log('成功下发指令--关灯');
        }
      })
    }
  },
  //处理报警器
  onBeepChange(event){
      const that = this
      console.log(event.detail.value);
      const sw = event.detail.value
      that.setData({Beep:sw})
      if(sw){
        that.data.client.publish(mpPubTopic,JSON.stringify({
          target:"beep",
          value:1
        }),function(err){
          if(!err){
            console.log('成功下发指令--打开报警器');
          }
        })
      }else{
        that.data.client.publish(mpPubTopic,JSON.stringify({
          target:"beep",
          value:0
        }),function(err){
          if(!err){
            console.log('成功下发指令--关闭报警器');
          }
        })
      }
  },
  //事件处理
  onShow(){
    const that = this
    that.setData({
      client:connect(`wxs://${mqttHost}:${mqttPort}/mqtt`)
    })
    that.data.client.on('connect',function(){
      console.log('成功连接MQTT服务器')
      wx.showToast({
        title: '连接成功',
        icon:'success',
        mask:true
      })
        that.data.client.subscribe(mpSubTopic,function(err){
          if(!err){
            console.log('成功订阅设备上行数据Topic')
          }
        })
    })

    that.data.client.on('message',function(topic,message){
      console.log(topic);
      //console.log(message);
      //message 是16进制的buffer字节流
      //解析jSON数据
      let dataFromDev = {}
      //避免错误的解析打断程序的运行
      try {
        dataFromDev = JSON.parse(message);
        console.log(dataFromDev);
        that.setData({
          Temp:dataFromDev.Temp,
          Hum:dataFromDev.Temp,
          Light:dataFromDev.Temp,
          Led:dataFromDev.Led,
          Beep:dataFromDev.Beep,
        })



      } catch (error) {
       // console.log(error);
        console.log('解析JSON失败',error);
      }
      
    })
  }
})





