package cn.swordsmen.feishu.entity;

import lombok.Data;

import java.util.Map;

/**
 * 文件列表清单 [蔡旺鸿]
 *
 * @author caiwanghong
 * @version 1.0
 * @apiNote
 * @date 2023/6/7 20:38
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
