package org.acme.batchcore;

import jakarta.batch.api.chunk.AbstractItemWriter;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;

import java.util.List;

@Named("sampleWriter")
@Dependent
public class SampleWriter extends AbstractItemWriter {
    @Override
    public void writeItems(List<Object> list) throws Exception {}
}
