package cn.swordsmen.feishu.entity;

import lombok.Data;

/**
 * 云表格表头模型 [蔡旺鸿]
 *
 * @author caiwanghong
 * @version 1.0
 * @apiNote
 * @date 2023/5/29 16:08
 */
@Data
public class SheetHeader {
    /* 表头类型名称 */
    private String typeName;

    /* 表头类型索引 */
    private Integer typeIndex;

    /* 起始列 */
    private String colStartIndex;

    /* 结束列 */
    private String colEndIndex;

    /* 表头字段名列表 */
    private String[] header;
}
