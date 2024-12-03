package org.acme.batchcore;

import jakarta.batch.api.chunk.ItemReader;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;
import org.jboss.logging.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("sampleReader")
@Dependent
public class SampleReader implements ItemReader {

    private static final Logger LOG = Logger.getLogger(SampleReader.class);
    List<String> carList = new ArrayList<>();
    int count;

    @Override
    public void open(Serializable serializable) throws Exception {
        carList = List.of("Suzuki Alto", "Suzuki Swift", "Suzuki Dzire", "Renault Kwid", "Renault Triber", "Hyundai i10", "Hyundai Aura", "Tata Punch");
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public Object readItem() throws Exception {

        if (count >= carList.size()) {
            return null;
        }
        return carList.get(count++);
    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        return null;
    }

    /*
    public Integer readItem1() throws Exception {
        if (count >= tokens.length) {
            return null;
        }

        jobContext.setTransientUserData(count);
        return tokens[count++];
    }


    public void open1(Serializable checkpoint) throws Exception {
        tokens = new Integer[] { 1,2,3,4,5,6,7,8,9,10 };
        count = 0;
    }*/
}
