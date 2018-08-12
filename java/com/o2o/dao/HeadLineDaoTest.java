package com.o2o.dao;

import com.o2o.BaseTest;
import com.o2o.entity.HeadLine;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HeadLineDaoTest extends BaseTest {
    @Autowired
    HeadLineDao headLineDao;
    @Test
    public void testqueryHeadLine(){
        HeadLine headLine = new HeadLine();
        headLine.setEnableStatus(1);
        List<HeadLine>list = headLineDao.queryHeadLine(headLine);
        System.out.println(list);
    }
}
