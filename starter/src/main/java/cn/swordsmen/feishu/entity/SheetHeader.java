package cn.swordsmen.feishu.entity;

import lombok.Data;

/**
 * 云表格表头模型
 *
 * @author caiwanghong
 * @date 2023/8/25 15:29
 * @version 1.0
 */
@Data
public class SheetHeader {
    /**
     * 表头类型名称
     */
    private String typeName;

    /**
     * 表头类型索引
     */
    private Integer typeIndex;

    /**
     * 起始列
     */
    private String colStartIndex;

    /**
     * 结束列
     */
    private String colEndIndex;

    /**
     * 表头字段名列表
     */
    private String[] header;
}
