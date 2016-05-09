/*
 * Copyright (C) 2016 Stratio (http://stratio.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.sqoop.client.request;

import org.apache.hadoop.security.token.delegation.web.DelegationTokenAuthenticatedURL;
import org.apache.sqoop.json.JSONUtils;
import org.apache.sqoop.json.JobBean;
import org.apache.sqoop.json.JobsBean;
import org.apache.sqoop.json.SubmissionBean;
import org.apache.sqoop.json.ValidationResultBean;
import org.apache.sqoop.model.MJob;
import org.apache.sqoop.utils.UrlSafeUtils;
import org.json.simple.JSONObject;

/**
 * Provide CRUD semantics over RESTfull HTTP API for jobs. All operations are
 * normally supported.
 */
public class JobResourceRequest extends ResourceRequest {

  public static final String RESOURCE = "v1/job/";

  private static final String ENABLE = "/enable";
  private static final String DISABLE = "/disable";
  private static final String START = "/start";
  private static final String STOP = "/stop";
  private static final String STATUS = "/status";

  public JobResourceRequest(){
    super();
  }

  public JobResourceRequest(DelegationTokenAuthenticatedURL.Token token){
    super(token);
  }

  public JobBean readByConnector(String serverUrl, String cArg) {
    JobsBean bean = new JobsBean();
    if (cArg != null) {
      String response = super.get(serverUrl + RESOURCE + "?cname=" + UrlSafeUtils.urlEncode(cArg));
      JSONObject jsonObject = JSONUtils.parse(response);
      bean.restore(jsonObject);
    }
    return bean;
  }

  public JobBean read(String serverUrl, String jobArg) {
    String response;
    if (jobArg == null) {
      response = super.get(serverUrl + RESOURCE + "all");
    } else {
      response = super.get(serverUrl + RESOURCE + UrlSafeUtils.urlPathEncode(jobArg));
    }
    JSONObject jsonObject = JSONUtils.parse(response);
    // defaults to all
    JobBean bean = new JobsBean();
    if (jobArg != null) {
      bean = new JobBean();
    }
    bean.restore(jsonObject);
    return bean;
  }

  public ValidationResultBean create(String serverUrl, MJob job) {
    JobBean jobBean = new JobBean(job);
    // Extract all config inputs including sensitive inputs
    JSONObject jobJson = jobBean.extract(false);
    String response = super.post(serverUrl + RESOURCE, jobJson.toJSONString());
    ValidationResultBean validationResultBean = new ValidationResultBean();
    validationResultBean.restore(JSONUtils.parse(response));
    return validationResultBean;
  }

  public ValidationResultBean update(String serverUrl, MJob job) {
    JobBean jobBean = new JobBean(job);
    // Extract all config inputs including sensitive inputs
    JSONObject jobJson = jobBean.extract(false);
    String response = super.put(serverUrl + RESOURCE + UrlSafeUtils.urlPathEncode(job.getName()),
        jobJson.toJSONString());
    ValidationResultBean validationBean = new ValidationResultBean();
    validationBean.restore(JSONUtils.parse(response));
    return validationBean;
  }

  public void delete(String serverUrl, String jobArg) {
    super.delete(serverUrl + RESOURCE + UrlSafeUtils.urlPathEncode(jobArg));
  }

  public void enable(String serverUrl, String jobArg, Boolean enabled) {
    if (enabled) {
      super.put(serverUrl + RESOURCE + UrlSafeUtils.urlPathEncode(jobArg) + ENABLE, null);
    } else {
      super.put(serverUrl + RESOURCE + UrlSafeUtils.urlPathEncode(jobArg) + DISABLE, null);
    }
  }

  public SubmissionBean start(String serverUrl, String jobArg) {
    String response = super.put(serverUrl + RESOURCE + UrlSafeUtils.urlPathEncode(jobArg) + START, null);
    return createJobSubmissionResponse(response);
  }

  public SubmissionBean stop(String serverUrl, String jobArg) {
    String response = super.put(serverUrl + RESOURCE + UrlSafeUtils.urlPathEncode(jobArg) + STOP, null);
    return createJobSubmissionResponse(response);
  }

  public SubmissionBean status(String serverUrl, String jobArg) {
    String response = super.get(serverUrl + RESOURCE + UrlSafeUtils.urlPathEncode(jobArg) + STATUS);
    return createJobSubmissionResponse(response);
  }

  private SubmissionBean createJobSubmissionResponse(String response) {
    SubmissionBean submissionBean = new SubmissionBean();
    submissionBean.restore(JSONUtils.parse(response));
    return submissionBean;
  }
}
