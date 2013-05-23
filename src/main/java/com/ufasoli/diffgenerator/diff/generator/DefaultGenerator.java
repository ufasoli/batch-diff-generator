package com.ufasoli.diffgenerator.diff.generator;

import com.google.inject.Inject;
import com.ufasoli.diffgenerator.diff.compare.Comparator;
import com.ufasoli.diffgenerator.diff.reports.Report;
import com.ufasoli.diffgenerator.util.ApplicationConfig;

import java.io.IOException;

/**
 * User: ufasoli
 * Date: 23/05/13
 * Time: 21:27
 * Project : batch-diff-generator
 */
public class DefaultGenerator  implements Generator {


    private ApplicationConfig applicationConfig;
     private Comparator comparator;
    private Report report ;


    @Inject
    public DefaultGenerator(ApplicationConfig applicationConfig, Comparator comparator, Report report) {
        this.applicationConfig = applicationConfig;
        this.comparator = comparator;
        this.report = report;
    }




    @Override
    public void generate() {

        String baseLeft = applicationConfig.getBaseLeft();
        String baseRight = applicationConfig.getBaseRight();

        for(String url : applicationConfig.getTargetFiles()){

            try {
                report.generateReport(comparator.compare(baseLeft+url, baseRight+url));
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
}
