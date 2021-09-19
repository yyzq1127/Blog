package com.yk.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yk.entity.Moment;
import com.yk.model.Page;
import com.yk.model.ResultInfo;
import com.yk.utils.AssertUtil;
import com.yk.dao.MomentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yk
 * @version 1.0
 * @date 2021/4/11 16:17
 */
@Service
public class MomentService {

    @Resource
    private MomentMapper momentMapper;

    /**
     * 查询动态
     * @return
     */
    public ResultInfo selectMoments() {
        PageHelper.startPage(Page.getPageNumber(),Page.getPageSize());
        PageInfo<Moment> pageInfo = new PageInfo<>(momentMapper.selectMoments());
        Map<String,Object> map = new HashMap<>();
        map.put("list",pageInfo.getList());
        map.put("total",pageInfo.getTotal());
        //发布状态

        return ResultInfo.createResult("查询成功!",map);
    }

    /**
     * 更新发布状态
     * @param id
     * @param published
     * @return
     */
    @Transactional
    public ResultInfo updatePublished(Long id, boolean published) {
        //查询是否存在
        Moment moment = momentMapper.selectMomentById(id);
        AssertUtil.isTrue(moment == null,"待修改数据不存在!");
        AssertUtil.isTrue(momentMapper.updatePublished(id,published) < 1,"发布状态修改失败!");
        return ResultInfo.createResult("发布状态修改成功!");
    }

    /**
     * 通过id查询动态
     * @param id
     * @return
     */
    @Transactional
    public ResultInfo getMomentById(Long id) {
        Moment moment = momentMapper.selectMomentById(id);
        AssertUtil.isTrue(moment == null,"动态数据不存在!");
        return ResultInfo.createResult("查询成功!",moment);
    }

    /**
     * 通过id删除动态
     * @param id
     * @return
     */
    @Transactional
    public ResultInfo deleteMomentById(Long id) {
        AssertUtil.isTrue(momentMapper.deleteMomentById(id) < 1,"动态删除失败!");
        return ResultInfo.createResult("动态删除成功!");
    }

    /**
     * 添加动态
     * @param moment
     * @return
     */
    @Transactional
    public ResultInfo addMoment(Moment moment) {
        return addOrUpdateMethod(moment,"添加");
    }

    /**
     * 更新动态
     * @param moment
     * @return
     */
    @Transactional
    public ResultInfo updateMoment(Moment moment) {
        return addOrUpdateMethod(moment,"更新");
    }

    /**
     * 添加or更新方法
     * @param moment
     * @param type
     * @return
     */
    private ResultInfo addOrUpdateMethod(Moment moment, String type) {
        //参数非空校验
        if(type.equals("更新")){
            Moment temp = momentMapper.selectMomentById(moment.getId());
            AssertUtil.isTrue(temp == null,"待更新数据不存在!");
        }
        AssertUtil.isTrue(moment.getContent() == null,"动态内容不能为空!");
        AssertUtil.isTrue(moment.getLikes() == null || moment.getLikes()<0,"点赞数格式不正确!");
        //默认值
        if(moment.getCreateTime() == null) {
            moment.setCreateTime(new Date());
        }
        //执行操作(moment)
        String x = "保存";
        if(moment.getPublished()) {
            x = "发布";
        }
        if(type.equals("添加")) {
            AssertUtil.isTrue(momentMapper.addMoment(moment) < 0,x+"失败!");
        } else {
            AssertUtil.isTrue(momentMapper.updateMoment(moment) < 0,x+"失败!");
        }
        return ResultInfo.createResult(x+"成功!");
    }



    public List<Moment> getMoments() {
        return momentMapper.selectMoments();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void like(Long id) {
        momentMapper.like(id);
    }
}
