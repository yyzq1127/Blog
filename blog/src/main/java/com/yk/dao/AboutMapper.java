package com.yk.dao;


import com.yk.entity.About;

import java.util.List;

public interface AboutMapper {
    List<About> getAbout();

    Integer updateAbout(About about);
}
