package cn.swordsmen.feishu.entity;

import lombok.*;

import java.util.List;

/**
 * sheet单元格式模型 [蔡旺鸿]
 *
 * @author caiwanghong
 * @version 1.0
 * @apiNote
 * @date 2023/5/29 18:13
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CellStyleRange {
    private List<String> ranges;

    private CellStyle style;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CellStyle {
        private CellFont font;

        private Integer textDecoration;

        private String formatter;

        private Integer hAlign;

        private Integer vAlign;

        private String foreColor;

        private String backColor;

        private String borderType;

        private String borderColor;

        private Boolean clean;

        @Getter
        @Setter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class CellFont {
            private Boolean bold;
            private Boolean italic;
            private String fontSize;
        }
    }
}

