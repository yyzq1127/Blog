package com.yk.model.dto;

import com.yk.entity.SiteSetting;
import lombok.Data;

import java.util.List;

/**
 * @author yk
 * @version 1.0
 * @date 2021/5/15 15:41
 */
@Data
public class SettingsAndDeleteIds {
    private Long[] deleteIds;
    private List<SiteSetting> settings;
}
