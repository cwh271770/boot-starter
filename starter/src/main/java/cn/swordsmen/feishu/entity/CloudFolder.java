package cn.swordsmen.feishu.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 云文件夹信息
 *
 * @author caiwanghong
 * @date 2023/8/25 15:29
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CloudFolder {
    /**
     * 文件夹名
     */
    private String folderName;

    /**
     * 文件夹token
     */
    private String folderToken;

    public static CloudFolder toFolder(String folderStr) {
        return JSONObject.parseObject(folderStr, CloudFolder.class);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
