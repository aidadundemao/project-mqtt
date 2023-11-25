package com.itlinxi.service.impl;

import com.google.gson.JsonObject;
import com.itlinxi.service.MqttService;
import com.itlinxi.utils.FastjsonUtils;

public class MqttServiceImpl implements MqttService {

    @Override
    public void startCase(String message) {
        System.out.println("收到单片机消息："+message);
    }
}
