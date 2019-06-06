package com.xfpay.controller;


import com.xfpay.exception.BusinessException;
import com.xfpay.exception.ExceptionCode;
import com.xfpay.request.RequestHead;
import com.xfpay.response.PayResponse;
import com.xfpay.utils.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * 统一调用网关 目前只支持json格式报文
 *
 * @author Administrator
 */
@Controller
@RequestMapping(value = "/XFpay")
public class GateWayController extends BaseController {
    @Autowired
    private SpringContextUtil context;

    @PostConstruct
    public void preInit() {
        services = new HashMap<>();
        // 交易服务接口
//		services.put("ruifu.unifiedorder".toUpperCase(), context.getBean("PayUnifiedorderReceive"));


//

    }


    //统一入口
    @RequestMapping(value = "/gateway", method = RequestMethod.POST)
    @ResponseBody
    public void receive(HttpServletRequest request, HttpServletResponse rsp) {
        PayResponse response;
        RequestHead head = null;
        try {
            TRACE.info("receive ip:" + request.getRemoteAddr());
            head = resolveHead(request);
            if (head.getVersion().equals("1.0")) {
                response = receive1_0(head, request);//进入交易
                response.setReturn_code("0000");
                response.setChannel_no(head.getChannel_no());
                response.setSign_type("MD5");
                if (StringUtils.isEmpty(response.getReturn_msg())) {
                    response.setReturn_msg("交易成功");
                }
            } else {
                throw new BusinessException(ExceptionCode.F001.getErrorCode(), "所支持版本[1.0]");
            }
        } catch (BusinessException e) {
            response = new PayResponse(e);
        } catch (IllegalArgumentException e) {
            TRACE.error(e.getMessage(), e);
            response = new PayResponse(e);
            TRACE.error(e.getMessage(), e);
        } catch (Exception e) {
            response = new PayResponse(e);
        }
        String repXML = packageResponse(head, response);
//		TRACE.info("response:\n" + repXML);
        outWrite(rsp, repXML);
    }

}
