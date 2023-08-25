package cn.swordsmen.feishu.entity;

import lombok.Data;

/**
 * 云文件夹元数据模型 [蔡旺鸿]
 *
 * @author caiwanghong
 * @version 1.0
 * @apiNote
 * @date 2023/6/20 19:51
 */
@Data
public class CloudFolderRespBody {
    /**
     * 文件夹id
     */
    private String id;

    /**
     * 文件夹名称
     */
    private String name;

    /**
     * 文件夹token
     */
    private String token;

    /**
     * 文件夹为个人文件夹时，为文件夹的所有者 id；文件夹为共享文件夹时，为文件夹树id
     */
    private String ownUid;

    /**
     * 文件夹的创建者id
     */
    private String createUid;

    /**
     * 文件夹的上级目录id
     */
    private String parentId;
}
