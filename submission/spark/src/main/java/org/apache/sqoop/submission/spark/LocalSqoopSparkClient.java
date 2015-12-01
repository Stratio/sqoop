package org.apache.sqoop.submission.spark;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.sqoop.common.Direction;
import org.apache.sqoop.driver.JobRequest;
import org.apache.sqoop.job.SparkJobConstants;
import org.apache.sqoop.job.spark.SparkDestroyerExecutor;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;

public class LocalSqoopSparkClient implements SqoopSparkClient {

    private static final long serialVersionUID = 1L;
    protected static final transient Log LOG = LogFactory.getLog(LocalSqoopSparkClient.class);

    private static final Splitter CSV_SPLITTER = Splitter.on(",").omitEmptyStrings();

    private static LocalSqoopSparkClient client;

    public static synchronized LocalSqoopSparkClient getInstance(SparkConf sparkConf) {
        if (client == null) {
            client = new LocalSqoopSparkClient(sparkConf);
        }
        return client;
    }

    private JavaSparkContext context;

    private List<String> localJars = new ArrayList<String>();

    private List<String> localFiles = new ArrayList<String>();

    public LocalSqoopSparkClient(SparkConf sparkConf) {
        context = new JavaSparkContext(sparkConf);
    }

    public SparkConf getSparkConf() {
        return context.getConf();
    }
    public JavaSparkContext getSparkContext() {
        return context;
    }

    public int getExecutorCount() {
        return context.sc().getExecutorMemoryStatus().size();
    }

    public int getDefaultParallelism() throws Exception {
        return context.sc().defaultParallelism();
    }

    public void execute(JobRequest request) throws Exception {

        //SparkCounters sparkCounters = new SparkCounters(sc);
        SqoopSparkDriver.execute(request, getSparkConf(), context);
        SparkDestroyerExecutor.executeDestroyer(true, request, Direction.FROM, SparkJobConstants.SUBMITTING_USER);
        SparkDestroyerExecutor.executeDestroyer(true, request, Direction.TO,SparkJobConstants.SUBMITTING_USER);

    }

    public void addResources(String addedFiles) {
        for (String addedFile : CSV_SPLITTER.split(Strings.nullToEmpty(addedFiles))) {
            if (!localFiles.contains(addedFile)) {
                localFiles.add(addedFile);
                context.addFile(addedFile);
            }
        }
    }

    private void addJars(String addedJars) {
        for (String addedJar : CSV_SPLITTER.split(Strings.nullToEmpty(addedJars))) {
            if (!localJars.contains(addedJar)) {
                localJars.add(addedJar);
                context.addJar(addedJar);
            }
        }
    }
    public void stop(String jobId) throws Exception {
        context.stop();
        client = null;
    }

    @Override
    public void close() throws IOException {

    }
}
