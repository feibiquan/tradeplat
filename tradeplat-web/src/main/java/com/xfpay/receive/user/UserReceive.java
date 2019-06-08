package com.xfpay.receive.user;

import com.xfpay.cache.BizCacheFactory;
import com.xfpay.exception.BusinessException;
import com.xfpay.exception.ExceptionCode;
import com.xfpay.mapper.UserMapper;
import com.xfpay.receive.Receive;
import com.xfpay.request.RequestHead;
import com.xfpay.request.UserRequest;
import com.xfpay.response.PayResponse;
import com.xfpay.response.user.UserResponse;
import com.xfpay.utils.common.FastJsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by fei on 2019/6/7.
 */
@Component("UserReceive")
public class UserReceive extends Receive {


    @Autowired
    UserMapper userMapper;

    /**
     * 执行方法
     * @param head
     * @param request
     * @return
     */
    @Override
    public PayResponse execute(RequestHead head, HttpServletRequest request) {
        UserRequest user= (UserRequest) head;
        UserResponse response=new UserResponse();
        response.setUser(userMapper.selectByPrimaryKey(user.getId()));
        return null;
    }


    /**
     * 转成所需的request
     * @param head
     * @return
     */
    @Override
    public RequestHead resolve(RequestHead head) {
        return FastJsonUtil.toBean(head.getData_content(),RequestHead.class);
    }


    private void validate(UserRequest user){
        if (user==null||user.getId()==null)
            throw new BusinessException(ExceptionCode.F001.getErrorCode(), "id必输");
    }
}
