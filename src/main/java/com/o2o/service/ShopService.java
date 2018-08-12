package com.o2o.service;

import com.o2o.dto.ImageHolder;
import com.o2o.dto.ShopExecution;
import com.o2o.entity.Shop;
import com.o2o.exceptions.ShopOperationException;

import java.awt.*;
import java.io.File;
import java.io.InputStream;

public interface ShopService {
    ShopExecution addShop(Shop shop, ImageHolder thumbnail);

    Shop getByShopId(long shopId);

    ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;

    ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);
}
