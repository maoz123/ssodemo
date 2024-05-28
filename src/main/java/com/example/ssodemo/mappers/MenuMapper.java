package com.example.ssodemo.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ssodemo.models.Menu;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    List<String> selectPermissions(int userId);
}
