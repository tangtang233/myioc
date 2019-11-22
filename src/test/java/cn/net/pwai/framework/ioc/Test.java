package cn.net.pwai.framework.ioc;

import cn.net.pwai.framework.ioc.core.JsonApplicationContext;
import cn.net.pwai.framework.ioc.entity.Robot;

public class Test {
    public static void main(String[] args) throws Exception {

        JsonApplicationContext applicationContext = new JsonApplicationContext("application.json");
        applicationContext.init();
        Robot aiRobot = (Robot) applicationContext.getBean("robot");

        aiRobot.show();

    }

}
