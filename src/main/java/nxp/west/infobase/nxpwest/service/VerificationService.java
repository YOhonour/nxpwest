package nxp.west.infobase.nxpwest.service;

import nxp.west.infobase.nxpwest.utils.GenerateVerificationCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class VerificationService {

    @Autowired
    ConcurrentMapCacheManager concurrentMapCacheManager;

    @Cacheable(cacheNames = "code", key = "#phone")
    public String getVerificationCode(String phone) {
        return GenerateVerificationCode.getVerificationCode_NUM();
    }

    public boolean isRightCode(String phone, String code) {
        //获取缓存管理类
        Cache cache = concurrentMapCacheManager.getCache("code");
        if (cache == null) {
            return false;
        }
        // 获取缓存code
        String s = cache.get(phone, String.class);
        // 为真返回true
        return code != null && code.equals(s);
    }

    public void erase(String phone) {
        Cache cache = concurrentMapCacheManager.getCache("code");
        //清除缓存
        if (phone != null && cache != null) {
            cache.evict(phone);
        }
    }

}
