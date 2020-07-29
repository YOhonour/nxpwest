package nxp.west.infobase.nxpwest.service;

import nxp.west.infobase.nxpwest.utils.GenerateVerificationCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.stereotype.Service;

@Service
public class VerificationService {

    @Autowired
    ConcurrentMapCacheManager concurrentMapCacheManager;
    @Cacheable(cacheNames = "code",key = "#phone")
    public String getVerificationCode(String phone){
        String code_num = GenerateVerificationCode.getVerificationCode_NUM();
        return code_num;
    }
    public boolean isRightCode(String phone,String code){
        //获取缓存管理类
        Cache cache = concurrentMapCacheManager.getCache("code");
        if (cache==null){
            return false;
        }
        String s = cache.get(phone, String.class);
        if (s.equals(code)){
            //清除缓存
            cache.evict(phone);
            return true;
        }
        return false;
    }
}
