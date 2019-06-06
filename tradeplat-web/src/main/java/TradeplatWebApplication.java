import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.xfpay")//扫描自动装配注解信息
@MapperScan("com.xfpay.mapper")//扫描dao.java(xxMapper.java)文件
@PropertySource({"dbconfig.properties"})
public class TradeplatWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradeplatWebApplication.class, args);
	}

}
