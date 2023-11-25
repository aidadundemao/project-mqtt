package com.itlinxi.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itlinxi.dao.TempDao;
import com.itlinxi.pojo.Temp;
import com.itlinxi.service.TempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;


@Service(interfaceClass = TempService.class)
@Transactional
public class TempServiceImpl implements TempService {

    @Autowired
    private TempDao tempDao;

    @Override
    public void add(float tempNum) {
        Temp temp =new Temp();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd :hh :mm :ss");

        System.out.println(tempNum);
        System.out.println(simpleDateFormat.format(date));

        temp.setTime(simpleDateFormat.format(date));
        temp.setTempNum(tempNum);

        tempDao.add(temp);
    }

}
