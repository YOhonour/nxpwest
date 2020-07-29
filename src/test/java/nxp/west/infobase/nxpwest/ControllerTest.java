package nxp.west.infobase.nxpwest;

import nxp.west.infobase.nxpwest.bean.ResultBean;
import nxp.west.infobase.nxpwest.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ControllerTest {
@Autowired
    UserController controller;
    @Test
    void name() throws InterruptedException {
        System.out.println(controller.getAllType().toString());
        Thread.sleep(3);
        System.out.println(controller.getAllType().toString());


    }
    @Test
    void name1() throws InterruptedException {
        ResultBean info = controller.getCompInfo("无线节能组");
        System.out.println(info.getData());
        Thread.sleep(3);
        controller.getCompInfo("无线节能组");
        System.out.println(info.getData());

    }
}
