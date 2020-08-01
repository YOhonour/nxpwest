package nxp.west.infobase.nxpwest;

import com.fasterxml.jackson.core.JsonProcessingException;
import nxp.west.infobase.nxpwest.bean.ResultBean;
import nxp.west.infobase.nxpwest.controller.UserController;
import nxp.west.infobase.nxpwest.exception.DrawException;
import nxp.west.infobase.nxpwest.exception.DrawErrorException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author MaYunHao
 * @version 1.0
 * @description
 * @date 2020/7/31 21:41
 */

@SpringBootTest
public class DrawTest {
    @Autowired
    UserController userController;

    @Test
    void drawLotsTest() throws DrawException,
            DrawErrorException,
            JsonProcessingException {
        ResultBean resultBean = userController.doDrawLots(1, "15002320582");
        System.out.println(resultBean);
    }
}
