package com.wayn.commom.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.wayn.commom.domain.TableInfo;

/**
 * 代码生成service
 */
public interface GenService extends IService<TableInfo> {
    /**
     * 查询ry数据库表信息
     *
     *
     * @param page
     * @param tableInfo 表信息
     * @return 数据库表列表
     */
    public Page<TableInfo> selectTableList(Page<TableInfo> page, TableInfo tableInfo);

    /**
     * 生成代码
     *
     * @param tableName 表名称
     * @return 数据
     */
    public byte[] generatorCode(String tableName);

    /**
     * 批量生成代码
     *
     * @param tableNames 表数组
     * @return 数据
     */
    public byte[] generatorCode(String[] tableNames);
}