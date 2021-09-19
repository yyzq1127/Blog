package com.yk.service;

import com.yk.dao.ExceptionLogMapper;
import com.yk.utils.AssertUtil;
import com.yk.utils.IpAddressUtil;
import com.yk.entity.ExceptionLog;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author yk
 * @version 1.0
 * @date 2021/4/2 20:02
 */

@Service
public class ExceptionLogService {

    @Resource
    private ExceptionLogMapper exceptionLogMapper;

    public List<ExceptionLog> getExceptionLogs(Date date1, Date date2) {
        return exceptionLogMapper.getExceptionLogs(date1,date2);
    }

    @Transactional
    public void delete(Integer id) {
        AssertUtil.isTrue(exceptionLogMapper.delete(id)<1,"删除失败!");
    }

    public void addExceptionLog(ExceptionLog exceptionLog) {
        String ipSource = IpAddressUtil.getCityInfo(exceptionLog.getIp());
        //解析agent字符串
        UserAgent userAgent = UserAgent.parseUserAgentString(exceptionLog.getUserAgent());
        //获取浏览器对象
        Browser browser = userAgent.getBrowser();
        //获取操作系统对象
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        exceptionLog.setCreateTime(new Date());
        exceptionLog.setIpSource(ipSource);
        exceptionLog.setOs(operatingSystem.getName());
        exceptionLog.setBrowser(browser.getName());
        AssertUtil.isTrue(exceptionLogMapper.addOperationLog(exceptionLog)<1,"添加失败!");
    }
}
