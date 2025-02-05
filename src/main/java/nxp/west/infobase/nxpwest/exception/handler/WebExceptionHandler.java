package nxp.west.infobase.nxpwest.exception.handler;


import com.aliyuncs.exceptions.ClientException;
import nxp.west.infobase.nxpwest.bean.ResultBean;
import nxp.west.infobase.nxpwest.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class WebExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(WebExceptionHandler.class);
    @ExceptionHandler
    public ResultBean signUpException(SignUpColumnException e) {
        log.error("注册字段错误", e);
        return ResultBean.error(-1, "注册字段错误");
    }

    public ResultBean fucE(HttpRequestMethodNotSupportedException exception){
        log.error("HTTP请求方法错误", exception);
        return ResultBean.error(-2, "HTTP请求方法错误（GET or POST ?）");
    }
    @ExceptionHandler
    public ResultBean unknownAccount(UnknownAccountException e) {
        log.error("账号不存在", e);
        return ResultBean.error(-2, "账号不存在");
    }

    @ExceptionHandler
    public ResultBean incorrectCredentials(IncorrectCredentialsException e) {
        log.error("密码错误", e);
        return ResultBean.error(-3, "密码错误");
    }
//    @ExceptionHandler
//    public ResultBean mailSendException(MailException e) {
//        log.error("邮件发送失败", e);
//        return ResultBean.error(-4, "邮件发送失败");
//    }

    @ExceptionHandler
    public ResultBean smsException(ClientException e) {
        log.error("短信发送失败", e);
        // 发送邮件通知技术人员.
        return ResultBean.error(-5, "短信发送失败");
    }
    @ExceptionHandler
    public ResultBean verificationException(VerificationException e) {
        log.error("验证码错误", e);
        return ResultBean.error(-6, "验证码错误");
    }


    @ExceptionHandler
    public ResultBean unknownException(Exception e) {
        log.error("发生了未知异常", e);
        // 发送邮件通知技术人员.
        return ResultBean.error(-99, "系统出现错误, 请联系网站管理员!");
    }
    @ExceptionHandler
    public ResultBean unknownException(ParameterException e) {
        log.error("参数不合法", e);
        // 发送邮件通知技术人员.
        return ResultBean.error(-9, "参数不合法");
    }
    @ExceptionHandler
    public ResultBean unknownException(FileInfoStoreException e) {
        log.error("文件信息保存失败", e);
        return ResultBean.error(-10, "文件信息保存失败");
    }
    @ExceptionHandler
    public ResultBean unknownException(FileSendException e) {
        log.error("文件发送失败", e);
        return ResultBean.error(-11, "文件发送失败");
    }
    @ExceptionHandler
    public ResultBean unknownException(FileNotFindException1 e) {
        log.error("文件不存在", e);
        return ResultBean.error(-12, "文件不存在");
    }
    @ExceptionHandler
    public ResultBean unknownException(LoginException e) {
        log.error("未登录", e);
        return ResultBean.error(-13, "未登录");
    }

    @ExceptionHandler
    public ResultBean drawErrorExcetion(DrawErrorException e) {
        log.error("非法抽签", e);
        log.error(e.getMessage());
        return ResultBean.error(-14, e.getMessage());
    }

    public ResultBean admin(AdminException e){
        log.error("管理员请登录");
        return ResultBean.error(-1, "管理员请登录");
    }

    public ResultBean admin(DrawException e){
        log.error("请勿重复抽签");
        return ResultBean.error(-1, "请勿重复抽签");
    }

}
