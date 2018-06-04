package com.example.serge.test1;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    private Context context;
    private Properties properties;

    public PropertyReader(Context context){
        this.context = context;
        properties = new Properties(  );
    }

    public Properties load_properties(String file){
        AssetManager assetManager = context.getAssets();

        InputStream inputStream = null;
        try {
            inputStream = assetManager.open( file );
            properties.load(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
