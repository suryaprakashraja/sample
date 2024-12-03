package org.acme.batchcore;

import jakarta.batch.api.chunk.ItemProcessor;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;
import org.apache.commons.lang3.StringUtils;

@Named("sampleProcessor")
@Dependent
public class SampleProcessor implements ItemProcessor {
    @Override
    public Object processItem(Object o) throws Exception {

        System.out.println("List item: "+StringUtils.capitalize((String) o));
        return "Processed: "+StringUtils.capitalize((String) o);
    }
}
