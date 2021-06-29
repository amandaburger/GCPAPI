package com.csr.prac;
import com.google.cloud.bigquery.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.UUID;



@Service
public class BigQueryService {

    @Autowired
    BigQuery bigQuery;

    @Value("${spring.cloud.gcp.bigquery.datasetName}")
    private String datasetName;

    public void getData() throws InterruptedException {

        String query = String.format("SELECT * FROM %s.athlete360 LIMIT 10", this.datasetName);
        QueryJobConfiguration queryConfig =
                QueryJobConfiguration.newBuilder(query).build();

        // Run the query using the BigQuery object
        for (FieldValueList row : this.bigQuery.query(queryConfig).iterateAll()) {
            for (FieldValue val : row) {
                System.out.printf("%s,", val.getValue());
            }
            System.out.printf("\n");

        }
    }
}