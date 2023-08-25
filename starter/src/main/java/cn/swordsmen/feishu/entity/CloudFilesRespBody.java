package cn.swordsmen.feishu.entity;

import lombok.Data;

import java.util.Map;

/**
 * 文件列表清单
 *
 * @author caiwanghong
 * @date 2023/8/25 15:28
 * @version 1.0
 */
@Data
public class CloudFilesRespBody {
    private Map<String, CloudFile> children;

    private String parentToken;

    @Data
    public static class CloudFile {
        private String name;

        private String token;

        private String type;
    }
}
