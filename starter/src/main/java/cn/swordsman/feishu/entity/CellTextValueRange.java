package cn.swordsman.feishu.entity;

import lombok.*;

/**
 * sheet单元格文本模型
 *
 * @author caiwanghong
 * @date 2023/8/25 15:28
 * @version 1.0
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
