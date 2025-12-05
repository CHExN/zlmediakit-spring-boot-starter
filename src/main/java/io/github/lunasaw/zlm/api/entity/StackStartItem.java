package io.github.lunasaw.zlm.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 多屏拼接任务参数。
 *
 * @param gapV 垂直拼接间隔（比例）
 * @param gapH 水平拼接间隔（比例）
 * @param width 输出宽度
 * @param height 输出高度
 * @param url 拼接源 URL，二维数组
 * @param id 任务 ID
 * @param row 行数
 * @param col 列数
 * @param span 区域合并定义
 */
@Builder
public record StackStartItem(
        @JsonProperty("gapv") Double gapV,
        @JsonProperty("gaph") Double gapH,
        @JsonProperty("width") Integer width,
        @JsonProperty("height") Integer height,
        @JsonProperty("url") List<List<String>> url,
        @JsonProperty("id") String id,
        @JsonProperty("row") Integer row,
        @JsonProperty("col") Integer col,
        @JsonProperty("span") List<List<List<Integer>>> span
) {

    public StackStartItem {
        Assert.notNull(width, "width must not be null");
        Assert.notNull(height, "height must not be null");
        Assert.notNull(url, "url must not be null");
        Assert.notNull(id, "id must not be null");
        Assert.notNull(row, "row must not be null");
        Assert.notNull(col, "col must not be null");
    }
}
