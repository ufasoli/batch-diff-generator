package com.ufasoli.diffgenerator;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.ufasoli.diffgenerator.diff.compare.Comparator;
import com.ufasoli.diffgenerator.diff.compare.url.UrlComparator;
import com.ufasoli.diffgenerator.util.ApplicationConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: ufasoli
 * Date: 21/04/13
 * Time: 18:56
 * To change this template use File | Settings | File Templates.
 */
public class Bootstrapper extends AbstractModule {

    private String configFolder;
    private final String CONFIG_FILE_NAME = "config.properties";
    private final String URL_FILE_NAME = "urls.txt";


    public Bootstrapper(String configFolder){

        this.configFolder = configFolder;
    }

    @Override
    protected void configure() {

        // load application properties
        Properties applicationproperties = new Properties();
        String path =     configFolder + "config.properties";
        try {

            applicationproperties.load(new FileInputStream(new File(path)));
        } catch (IOException e) {
            throw  new RuntimeException(String.format("Unable to start application cannot load the config file in path [%s]", path));
        }

        Names.bindProperties(binder(), applicationproperties);

        bindConstant().annotatedWith(Names.named("config.folder")).to(configFolder);
        bind(ApplicationConfig.class) ;

        bind(Comparator.class).to(UrlComparator.class);

    }
}

