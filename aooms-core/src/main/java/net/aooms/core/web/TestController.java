package net.aooms.core.web;

import net.aooms.core.properties.PropertiesApplication;
import net.aooms.core.properties.PropertiesTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 一个完整的控制器Demo,方便框架内部调试
 * Created by cccyb on 2018-02-06
 */
@RestController
public class TestController {

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private PropertiesApplication propertiesApplication;

    @Autowired
    private PropertiesTest propertiesTest;

    @Value("${spring.application.name}")
    private String name;

    @Autowired
    private Environment environment;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("simpleRestTemplate")
    private RestTemplate simpleRestTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping(value="/get")
    public String get(String id) {
        logger.error(" value from propertiesApplication ： {} ",propertiesApplication.getName());
        logger.error(" value from property ： {} ", name);
        logger.error(" id value from param ： {} ", id);
        logger.error(" id value from testApplication ： {} ", propertiesTest.getName());

        logger.error(" id value from environment my.yml： {} ", environment.getProperty("my","test.name"));
        logger.error(" id value from environment my2.yml： {} ", environment.getProperty("my2","test.name"));

        return "get do success";
    }

    @GetMapping(value="/get2")
    public String get2(HttpServletRequest request) {
        logger.error(" id value from param ： {} ", request.getParameter("id"));
        return "get2 do success";
    }

    @GetMapping(value="/get3")
    public ModelAndView get3(HttpServletRequest request,ModelAndView mv) {
        logger.error(" id value from param ： {} ", request.getParameter("id"));
        mv.addObject("name", "张三");
        mv.setViewName("/test.html");
        return mv;
    }

    @GetMapping(value="/getRest")
    public String getRest(){
        List<String> services = discoveryClient.getServices();

        System.err.println("===>>>" + discoveryClient.description());
        System.err.println("===>>>" + services.size());

        String ret = restTemplate.getForObject("http://AOOMS/get",String.class);
        logger.error(" restTemplate Request Result is : {} " + ret);

        String ret2 = simpleRestTemplate.getForObject("http://localhost:9000/get2",String.class);
        logger.error(" simpleRestTemplate Request Url Result is : {} " + ret2);

        logger.error(" restTemplate Object Name = {}" + restTemplate.toString());

        return "get REST is success";
    }

    public String upload(){


        return null;
    }

}