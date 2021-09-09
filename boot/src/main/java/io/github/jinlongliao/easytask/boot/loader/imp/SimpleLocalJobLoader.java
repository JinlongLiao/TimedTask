package io.github.jinlongliao.easytask.boot.loader.imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.github.jinlongliao.easytask.boot.job.JobRegisterFactory;
import io.github.jinlongliao.easytask.boot.loader.mdoel.LocalJobModel;
import io.github.jinlongliao.easytask.core.job.AbstractJob;
import io.github.jinlongliao.easytask.core.job.IJobLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;

/**
 * 本地加载JOB
 *
 * @author liaojinlong
 * @since 2021/9/7 22:10
 */

public class SimpleLocalJobLoader implements IJobLoader {
  private static final Logger log = LoggerFactory.getLogger(SimpleLocalJobLoader.class);
  /**
   * 基于JSON 的配置的本地
   */
  public static final String JOB_CONF = "local_job.json";
  protected ClassLoader classLoader;
  protected URL localConf;
  protected Set<AbstractJob> jobSet;


  public SimpleLocalJobLoader() {
    this.classLoader = this.getClass().getClassLoader();
  }

  @Override
  public Set<AbstractJob> reLoadJob() {
    if (this.localConf == null) {
      this.localConf = getLocalConf(JOB_CONF);
    }
    if (this.localConf != null) {
      InputStream inputStream = null;
      try {
        URLConnection urlConnection = localConf.openConnection();
        urlConnection.connect();
        StringBuilder textBuilder = new StringBuilder();
        inputStream = urlConnection.getInputStream();
        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
          int c = 0;
          while ((c = reader.read()) != -1) {
            textBuilder.append((char) c);
          }
        }
        String jsonTxt = textBuilder.toString().trim();
        if (jsonTxt.length() < 1) {
          return this.jobSet = new HashSet<>(4);
        }
        List<LocalJobModel> localJobModels = JSONArray.parseArray(jsonTxt, LocalJobModel.class);
        this.jobSet = new HashSet<>(localJobModels.size());
        for (LocalJobModel localJobModel : localJobModels) {
          try {
            Class<? extends AbstractJob> aClass = (Class<? extends AbstractJob>) Class.forName(localJobModel.getClassName());
            this.jobSet.add(JSONObject.parseObject(localJobModel.getJsonData(), aClass));
          } catch (ClassNotFoundException classNotFoundException) {
            log.error("class Not found {}", localJobModel.getClassName());
          }
        }
      } catch (Exception e) {
        this.jobSet = new HashSet<>(4);
        log.warn("Parse LocalJob Error", e);
      } finally {
        if (inputStream != null) {
          try {
            inputStream.close();
          } catch (IOException e) {
          }
        }
      }
    }
    return jobSet;
  }

  @Override
  public Set<AbstractJob> loadAllJob() {
    if (jobSet != null) {
      return jobSet;
    }
    final Set<AbstractJob> abstractJobs = this.reLoadJob();
    return abstractJobs == null ? Collections.emptySet() : abstractJobs;
  }

  @Override
  public boolean addJob(AbstractJob job) {
    return jobSet.add(job);
  }

  private void flushJobToFile(Set<AbstractJob> job, URL url) {
    if (job == null) {
      return;
    }
    List<LocalJobModel> jobModels = new ArrayList<>(job.size());
    job.stream().forEach(node -> {
      jobModels.add(new LocalJobModel(node.getClass().getName(), JSONObject.toJSONString(node)));
    });
    try {
      Files.write(new File(url.getFile()).toPath(), JSONArray.toJSONString(jobModels).getBytes(StandardCharsets.UTF_8));
    } catch (IOException e) {
      log.error("write Load Job Conf error", e);
    }
  }

  @Override
  public boolean removeJob(AbstractJob job) {
    return jobSet.remove(job);
  }

  @Override
  public boolean stopJob(AbstractJob job) {
    // 默认不支持
    return false;
  }

  @Override
  public void flush() {
    this.flushJobToFile(jobSet, localConf);
  }

  public void setClassLoader(ClassLoader classLoader) {
    this.classLoader = classLoader;
  }

  /**
   * 获取根目录的配置文件
   *
   * @param confName
   * @return /
   */
  protected URL getLocalConf(String confName) {
    Enumeration<URL> urls;
    if (this.classLoader == null) {
      this.classLoader = ClassLoader.getSystemClassLoader();
    }
    urls = AccessController.doPrivileged((PrivilegedAction<Enumeration<URL>>) () -> {
      try {
        return this.classLoader.getResources(confName);
      } catch (IOException e) {
        log.warn("加载LocalJob Error");
      }
      return null;
    });
    if (urls == null) {
      return null;
    }
    URL url = null;
    while (urls.hasMoreElements()) {
      URL element = urls.nextElement();
      String file = element.getFile();
      if (!file.toLowerCase(Locale.ROOT).contains("jar")) {
        url = element;
        break;
      }
    }
    return url;
  }
}
