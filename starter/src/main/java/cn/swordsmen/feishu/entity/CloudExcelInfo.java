package cn.swordsmen.feishu.entity;

import lombok.*;

/**
 * 云表格信息模型 [蔡旺鸿]
 *
 * @author caiwanghong
 * @version 1.0
 * @apiNote
 * @date 2023/6/28 13:19
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CloudExcelInfo {
    /**
     * 表格单元值数组
     */
    private Object[][] values;

    /**
     * 表头信息
     */
    private SheetHeader header;

    /**
     * 表格标题
     */
    private String title;
}
