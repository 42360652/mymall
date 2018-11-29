package com.mymall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mymall.common.ServerResponse;
import com.mymall.dao.ShippingMapper;
import com.mymall.pojo.Shipping;
import com.mymall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("iShippingService")
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ServerResponse add(Integer userId, Shipping shipping){
        shipping.setUserId(userId);
        int rowCount = shippingMapper.insert(shipping);
        if (rowCount > 0){
            Map result = Maps.newHashMap();
            result.put("shippingId",shipping.getId());
            return ServerResponse.createBySuccess("SUCCESS",result);
        }
        return ServerResponse.createBySuccessMessage("failed");
    }

    @Override
    public ServerResponse<String> del(Integer userId, Integer shippingId){
        int resultCount = shippingMapper.deleteByShippingIdUserId(userId,shippingId);
        if (resultCount > 0 ){
            return ServerResponse.createBySuccess("delete success");
        }
        return ServerResponse.createByErrorMessage("delete failed");
    }

    @Override
    public ServerResponse update(Integer userId, Shipping shipping){
        shipping.setUserId(userId);
        int resultCount = shippingMapper.updateByShipping(shipping);
        if (resultCount > 0){
            return ServerResponse.createBySuccess("update success");
        }
        return ServerResponse.createBySuccessMessage("update failed");
    }

    @Override
    public ServerResponse<Shipping> select(Integer userId, Integer shippingId){
        Shipping shipping = shippingMapper.selectByShippingIdUserId(userId,shippingId);
        if (shipping == null){
            return ServerResponse.createByErrorMessage("can't find this address");
        }
        return ServerResponse.createBySuccess("Success", shipping);
    }

    @Override
    public ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
        PageInfo pageInfo = new PageInfo(shippingList);
        return ServerResponse.createBySuccess(pageInfo);
    }

}
