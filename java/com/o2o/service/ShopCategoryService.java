package com.o2o.service;

import com.o2o.entity.Shop;
import com.o2o.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {
    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}