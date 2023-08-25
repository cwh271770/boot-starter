package cn.swordsmen.feishu.entity;

import lombok.*;

/**
 * sheet单元格文本模型 [蔡旺鸿]
 *
 * @author caiwanghong
 * @version 1.0
 * @apiNote
 * @date 2023/5/29 17:24
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CellTextValueRange {
    /**
     * 表格单元格范围
     */
    private String range;

    /**
     * 表格单元值数组
     */
    private Object[][] values;
}
