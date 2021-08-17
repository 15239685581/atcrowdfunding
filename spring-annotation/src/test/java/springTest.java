import com.zwb.spring.MyAnnotationConfiguration;
import com.zwb.spring.entity.Employee;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
public class springTest {
    public static void main(String[] args) {
        //以前使用new ClassPathXmlApplicationContext("")方式加载xml配置文件
        //new ClassPathXmlApplicationContext();
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(MyAnnotationConfiguration.class);

        //从IOC容器中获取bean
        Employee employee = applicationContext.getBean("getEmployee",Employee.class);
        System.out.println(employee);

        applicationContext.close();
    }
}
