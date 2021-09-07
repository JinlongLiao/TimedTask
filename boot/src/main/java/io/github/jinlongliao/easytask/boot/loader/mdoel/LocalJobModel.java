package io.github.jinlongliao.easytask.boot.loader.mdoel;

/**
 * @author liaojinlong
 * @since 2021/9/7 22:49
 */
public class LocalJobModel {
  /**
   * 映射的任务CLass
   */
  private String className;
  /**
   * 任务数据
   */
  private String jsonData;

  public LocalJobModel() {
  }

  public LocalJobModel(String className, String jsonData) {
    this.className = className;
    this.jsonData = jsonData;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public String getJsonData() {
    return jsonData;
  }

  public void setJsonData(String jsonData) {
    this.jsonData = jsonData;
  }
}
