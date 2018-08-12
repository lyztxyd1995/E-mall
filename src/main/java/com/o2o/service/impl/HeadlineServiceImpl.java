package com.o2o.service.impl;

import com.o2o.dao.HeadLineDao;
import com.o2o.entity.HeadLine;
import com.o2o.service.HeadLineService;
import com.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class HeadlineServiceImpl implements HeadLineService {

    @Autowired
    private HeadLineDao headLineDao;
    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {
        List<HeadLine>result = headLineDao.queryHeadLine(headLineCondition);
        return result;
    }
}
