package com.gb.user.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author sunkailun
 * @DateTime 2018/11/5  2:56 PM
 * @email 376253703@qq.com
 * @phone 13777579028
 * @explain 节点类
 */
@Data
@ApiModel(value = "团队树节点类")
public class TeamTreeNode {
    /**
     * 绑定节点的标识值。
     */
    @ApiModelProperty(value = "绑定节点的标识值")
    private String id;
    /**
     * 显示的节点文本。
     */
    @ApiModelProperty(value = "显示的节点文本")
    private String name;

    /**
     * 父节点id
     */
    @ApiModelProperty(value = "父节点id")
    private String teamId;

    /**
     * 父节点名称
     */
    @ApiModelProperty(value = "父节点名称")
    private String teamName;
    /**
     * 子节点
     */
    @ApiModelProperty(value = "子节点")
    private List<TeamTreeNode> children;
    /**
     * 预选对象
     */
    @ApiModelProperty(value = "预选对象")
    private State state;

    public static class State {
        private Boolean checked;

        public Boolean getChecked() {
            return checked;
        }

        public void setChecked(Boolean checked) {
            this.checked = checked;
        }
    }
}
