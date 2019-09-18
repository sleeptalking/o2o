package com.zx.o2o.service;

import com.zx.o2o.BaseTest;
import com.zx.o2o.entity.Area;
import com.zx.o2o.service.impl.AreaServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AreaServiceTest extends BaseTest {
    @Autowired
    private AreaServiceImpl areaService;

    @Test
    public void  testGetAreaList(){
        List<Area> areaList = areaService.getAreaList();
        assertEquals("西苑",areaList.get(0).getAreaName());
    }

}
