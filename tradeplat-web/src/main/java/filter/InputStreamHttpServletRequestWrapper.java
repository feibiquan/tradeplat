package filter;


import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.Arrays;
import java.util.stream.Collectors;


/**
 * Created by fei on 2019/6/8.
 * 保留HttpServletRequest的inputstream流.避免springboot在初始时期调用getparamap后无法获取输入流
 */
public class InputStreamHttpServletRequestWrapper extends HttpServletRequestWrapper{

    private final byte[] streamBody;
    private static final int BUFFER_SIZE = 4096;

    //对请求数据判断，getInputStream()为空的数据对key和value值进行拼接
    public InputStreamHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        byte[] bytes = inputStream2Byte(request.getInputStream());
        if (bytes.length == 0 && RequestMethod.POST.name().equals(request.getMethod())) {
            //从ParameterMap获取参数，并保存以便多次获取
            bytes = request.getParameterMap().entrySet().stream()
                    .map(entry -> {
                        String result;
                        String[] value = entry.getValue();
                        if (value != null && value.length > 1) {
                            result = Arrays.stream(value).map(s -> entry.getKey() + "=" + s)
                                    .collect(Collectors.joining("&"));
                        } else {
                            result = entry.getKey() + "=" + value[0];
                        }

                        return result;
                    }).collect(Collectors.joining("&")).getBytes();
        }
        //System.err.println(new String(bytes));
        streamBody = bytes;
    }

    private byte[] inputStream2Byte(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[BUFFER_SIZE];
        int length;
        while ((length = inputStream.read(bytes, 0, BUFFER_SIZE)) != -1) {
            outputStream.write(bytes, 0, length);
        }

        return outputStream.toByteArray();
    }


    @Override
    public ServletInputStream getInputStream() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(streamBody);

        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() throws IOException {
                return inputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }



}




