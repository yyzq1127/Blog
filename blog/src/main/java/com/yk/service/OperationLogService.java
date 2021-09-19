package com.yk.service;

import com.yk.utils.AssertUtil;
import com.yk.utils.IpAddressUtil;
import com.yk.dao.OperationLogMapper;
import com.yk.entity.OperationLog;
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
 * @date 2021/5/26 21:34
 */
@Service
public class OperationLogService {
    @Resource
    private OperationLogMapper operationLogMapper;

    public List<OperationLog> getOperationLogs(Date date1, Date date2) {
        return operationLogMapper.getOperationLogs(date1,date2);
    }

    @Transactional
    public void delete(Integer id) {
        AssertUtil.isTrue(operationLogMapper.delete(id)<1,"删除失败!");
    }

    @Transactional
    public void addOperationLog(OperationLog operationLog) {
        String ipSource = IpAddressUtil.getCityInfo(operationLog.getIp());

        //解析agent字符串
        UserAgent userAgent = UserAgent.parseUserAgentString(operationLog.getUserAgent());
        //获取浏览器对象
        Browser browser = userAgent.getBrowser();
        //获取操作系统对象
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();

        /*System.out.println("浏览器名:"+browser.getName());
        System.out.println("浏览器类型:"+browser.getBrowserType());
        System.out.println("浏览器家族:"+browser.getGroup());
        System.out.println("浏览器生产厂商:"+browser.getManufacturer());
        System.out.println("浏览器使用的渲染引擎:"+browser.getRenderingEngine());
        System.out.println("浏览器版本:"+userAgent.getBrowserVersion());

        System.out.println("操作系统名:"+operatingSystem.getName());
        System.out.println("访问设备类型:"+operatingSystem.getDeviceType());
        System.out.println("操作系统家族:"+operatingSystem.getGroup());
        System.out.println("操作系统生产厂商:"+operatingSystem.getManufacturer());*/

        operationLog.setIpSource(ipSource);
        operationLog.setOs(operatingSystem.getName());
        operationLog.setBrowser(browser.getName());
        AssertUtil.isTrue(operationLogMapper.addOperationLog(operationLog)<1,"添加失败!");
    }
}
