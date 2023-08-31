package cn.swordsman.feishu.entity;

import lombok.*;

import java.util.List;

/**
 * sheet单元格式模型
 *
 * @author caiwanghong
 * @date 2023/8/25 15:27
 * @version 1.0
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

