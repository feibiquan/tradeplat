package com.xfpay.controller;


import com.xfpay.annotation.Ignore;
import com.xfpay.entity.AccessPartner;
import com.xfpay.entity.OrganizationInfo;
import com.xfpay.exception.BusinessException;
import com.xfpay.exception.ExceptionCode;
import com.xfpay.request.RequestHead;
import com.xfpay.response.PayResponse;
import com.xfpay.utils.IReceiveExector;
import com.xfpay.utils.common.FastJsonUtil;
import com.xfpay.utils.common.RSAUtils;
import com.xfpay.utils.kit.StreamKit;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.util.Args;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public abstract class BaseController {
    public final static String SUCCESS = "0000";
    //	@Autowired
//	protected BizCacheFactory cacheFactory;
    protected Logger TRACE = Logger.getLogger("TRACE");
    protected Map<String, IReceiveExector> services;

    protected String packageResponse(RequestHead head, PayResponse response) {
    /*	if (SUCCESS.equals(response.getReturn_code())) {
			response.setNonce_str(generateNonceStr());
			response.setChannel_no(head.getChannel_no());
			try {
				if (head.getSign_type().equalsIgnoreCase("MD5")) {
					response.setSign_type("MD5");
					String pinblock = getPinblock(response);
					pinblock = pinblock + "&key=" + head.getAccessPartner().getMd5Key();
					String sign = DigestUtils.md5Hex(pinblock.getBytes(head.getCharset()));
					TRACE.info("MD5签名\t" + sign);
					response.setSign(sign);
				} else if (head.getSign_type().equalsIgnoreCase("RSA")) {
					response.setSign_type("RSA");
					String pinblock = getPinblock(response);
					String privateKey = head.getAccessPartner().getPrivateKey();
					String sign = RSAUtils.sign(pinblock.getBytes(head.getCharset()), privateKey,
							RSAUtils.SIGN_TYPE_RSA2);
					TRACE.info("RSA签名\t" + sign);
					response.setSign(sign);
				}else{
                    throw new BusinessException(ExceptionCode.F008.getErrorCode(), "签名方式[RSA,MD5]");
                }
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return FastJsonUtil.toJSONString(response);*/
        return null;
    }

    protected String getPinblock(Object response) {
        SortedMap<String, Object> packagesMap = new TreeMap<String, Object>();
        for (Class<?> clazz = response.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())
                            || Modifier.isVolatile(field.getModifiers()) || field.isAnnotationPresent(Ignore.class)) {
                        continue;
                    }
                    if (field.isAnnotationPresent(Ignore.class)) {
                        continue;
                    }
                    field.setAccessible(true);
                    try {
                        Object value = field.get(response);
                        if (value != null) {
                            if (value.getClass().isAssignableFrom(String.class) && value.toString().length() == 0) {
                                continue;
                            }
                            packagesMap.put(field.getName(), value);
                        }
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
            } catch (Exception e) {

            }
        }
        return joinStr(packagesMap);
    }

    private String joinStr(SortedMap<String, Object> dataMap) {
        StringBuffer pinblock = new StringBuffer();
        Set<Map.Entry<String, Object>> es = dataMap.entrySet();
        Iterator<Map.Entry<String, Object>> it = es.iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            String k = (String) entry.getKey();
            String v = entry.getValue().toString();
            if (pinblock.toString().length() == 0) {
                pinblock.append(k + "=" + v);
            } else {
                pinblock.append("&");
                pinblock.append(k + "=" + v);
            }
        }
        return pinblock.toString();
    }

    protected String generateNonceStr() {
        return DigestUtils.md5Hex(UUID.randomUUID().toString().getBytes());
    }

    protected void outWrite(HttpServletResponse rsp, String repContent) {
        rsp.setContentType("text/xml;charset=utf-8");
        rsp.setHeader("cache-control", "no-cache");
        OutputStream wiriter = null;
        try {
            wiriter = rsp.getOutputStream();
            wiriter.write(repContent.getBytes("UTF-8"));
            wiriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (wiriter != null) {
                try {
                    wiriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 解析1.0版本
     *
     * @param head
     * @param request
     * @return
     */
    protected PayResponse receive1_0(RequestHead head, HttpServletRequest request) {
        head.setCharset("UTF-8");
        if (!services.containsKey(head.getService().toUpperCase())) {
            throw new BusinessException(ExceptionCode.F003.getErrorCode(), "服务接口输入有误");
        }
		/*if (!head.getSign_type().equalsIgnoreCase("MD5") && !head.getSign_type().equalsIgnoreCase("RSA")) {
			throw new BusinessException(ExceptionCode.F008.getErrorCode(), "签名方式[RSA,MD5]");
		}*/
        if (!head.getSign_type().equalsIgnoreCase("MD5")) {
            throw new BusinessException(ExceptionCode.F008.getErrorCode(), "签名方式[MD5]");
        }
//		OrganizationInfo channel = cacheFactory.getOrgCache().getOrgInfo(Long.parseLong(head.getChannel_no()));
//		if (channel == null) {
//			throw new BusinessException(ExceptionCode.F008.getErrorCode(), "渠道不存在");
//		}
//		head.setChannel(channel);
//		head.setAccessPartner(cacheFactory.getOrgCache().getAccess(channel.getOrganizationId()));
        //缓存中取AccessPartner信息
        AccessPartner accessPartner = new AccessPartner();
        accessPartner.setMd5Key(accessPartner.getMd5Key());
        RequestHead requestData = null;
        try {
            requestData = services.get(head.getService().toUpperCase()).resolve(head);
//			requestData.setChannel(channel);
            requestData.setAccessPartner(head.getAccessPartner());
            resolveSign(requestData, head.getAccessPartner());
        } catch (UnsupportedEncodingException e) {
            throw new BusinessException(ExceptionCode.F001.getErrorCode(), "签名验证错误");
        }
        return services.get(head.getService().toUpperCase()).execute(requestData, request);
    }


    /**
     * 验签：
     * 1.1 暂只支持MD5
     *
     * @param head
     * @param accessPartner
     * @throws UnsupportedEncodingException
     */
    protected void resolveSign(RequestHead head, AccessPartner accessPartner) throws UnsupportedEncodingException {
        String pinblock = getPinblock(head);
        if (head.getSign_type().equalsIgnoreCase("MD5")) {
            pinblock += "&key=" + accessPartner.getMd5Key();
            System.out.println("pinblock:" + pinblock);
            String sign = DigestUtils.md5Hex(pinblock.getBytes(head.getCharset()));
            if (!sign.equalsIgnoreCase(head.getSign())) {
                throw new BusinessException(ExceptionCode.F002.getErrorCode(), "签名不正确");
            }
        }

		/*else if (head.getSign_type().equalsIgnoreCase("RSA")) {
			String publicKey = accessPartner.getPatnerPublicKey();
			if (StringUtils.isEmpty(publicKey)) {
				throw new BusinessException(ExceptionCode.F002.getErrorCode(), "未配置公钥");
			}
			try {
				if (!RSAUtils.verify(pinblock.getBytes(head.getCharset()), publicKey, head.getSign(),
						RSAUtils.SIGN_TYPE_RSA2)) {
					throw new BusinessException(ExceptionCode.F002.getErrorCode(), "签名不正确");
				}
			} catch (Exception e) {
				throw new BusinessException(ExceptionCode.F002.getErrorCode(), "签名不正确");
			}
		}*/
    }

    public String addUrlValue(String url, String name, String value) {
        String a = "";
        if (!url.contains("/")) {// 编码后的URL不可能存在/
            // 编码化url处理 %3F->? %3D->=
            if (url.contains("%3F") && !url.contains("%3D")) {
                a = "";
            } else if (url.contains("%3F") && url.contains("%3D")) {
                a = "%26";
            } else {
                a = "%3F";
            }
        } else {
            // 非编码化url处理
            if (url.contains("?") && !url.contains("=")) {// http://zjw.cn?
                a = "";
            } else if (url.contains("?") && url.contains("=")) {// http://zjw.cn?a=b
                a = "&";
            } else {
                a = "?";
            }
        }
        return url += (a + name + "=" + value);
    }

    protected RequestHead resolveHead(HttpServletRequest request) {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
//            head.setCharset(request.getParameter("charset"));
//            head.setNonce_str(request.getParameter("nonce_str"));
//            head.setService(request.getParameter("service"));
//            head.setChannel_no(request.getParameter("channel_no"));
//            head.setVersion(request.getParameter("version"));
//            head.setSign_type(request.getParameter("sign_type"));
//            head.setSign(request.getParameter("sign"));
            String content = FastJsonUtil.mapToString(request.getParameterMap());
//			TRACE.info("request:" + content);
            content = content.replaceAll("\\[", "");
            content = content.replaceAll("]", "");
            RequestHead head = FastJsonUtil.toBean(content, RequestHead.class);
            head.setData_content(content);
            return head;
        }
//        String jsonBody = readBody(request);

        String jsonBody = FastJsonUtil.toJSONString(request.getParameterMap());
        RequestHead head = FastJsonUtil.toBean(jsonBody, RequestHead.class);
        if (StringUtils.isEmpty(head.getVersion())) {
            throw new BusinessException(ExceptionCode.F001.getErrorCode(), "版本号错误");
        }
        if (StringUtils.isEmpty(head.getChannel_no())) {
            throw new BusinessException(ExceptionCode.F001.getErrorCode(), "渠道号错误");
        }
        if (StringUtils.isEmpty(head.getNonce_str())) {
            throw new BusinessException(ExceptionCode.F001.getErrorCode(), "随机字符串错误");
        }
        if (StringUtils.isEmpty(head.getService())) {
            throw new BusinessException(ExceptionCode.F001.getErrorCode(), "服务名错误");
        }
        if (StringUtils.isEmpty(head.getSign_type()) || !head.getSign_type().equalsIgnoreCase("MD5")) {
            throw new BusinessException(ExceptionCode.F001.getErrorCode(), "签名方式错误");
        }
        head.setData_content(jsonBody);
        return head;
    }

    protected String readBody(HttpServletRequest request) {
        InputStream inputStream = null;
        try {
            Args.check(request.getContentLength() <= Integer.MAX_VALUE,
                    "HTTP entity too large to be buffered in memory");
            inputStream = request.getInputStream();
            int i = (int) request.getContentLength();
            if (i < 0) {
                i = 4096;
            }
            final ByteArrayBuffer buffer = new ByteArrayBuffer(i);
            final byte[] tmp = new byte[4096];
            int l;
            while ((l = inputStream.read(tmp)) != -1) {
                buffer.append(tmp, 0, l);
            }
            return new String(buffer.toByteArray(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                StreamKit.close(inputStream);
            }
        }
        return null;
    }

    public <T> T dataBinder(T t, HttpServletRequest request) {
        ServletRequestDataBinder binder = new ServletRequestDataBinder(t);
        binder.bind(request);
        return t;
    }

    public <T> T dataBinder(Class<T> obj, HttpServletRequest request) {
        T t = null;
        try {
            t = obj.newInstance();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dataBinder(t, request);
    }

/*	protected byte[] getImg(String headUrl){
		byte[] contentBytes = null;
		try {
			ResponseContent res = HttpClientExecutor.get(headUrl);
			contentBytes = res.getContentBytes();
		} catch (Exception e) {
			TRACE.error(e.getMessage(),e);
		}
		return contentBytes;
	}*/


    /**
     * 获取当前项目根路径
     *
     * @return
     */
    protected String getImgPath(HttpServletRequest request) {
        return request.getSession().getServletContext().getRealPath("/");
    }

}
