package org.acme.batchcore.listener;

import jakarta.batch.api.listener.JobListener;
import jakarta.inject.Named;

@Named
public class SampleJobListener implements JobListener {

    @Override
    public void beforeJob() throws Exception {
        //TODO: Logic to insert execution entry in custom table or event it out
    }

    @Override
    public void afterJob() throws Exception {
        //TODO: Logic to update execution entry with post job exec status in custom table or event it out.
    }
}
