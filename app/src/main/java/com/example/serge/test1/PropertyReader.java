package com.example.serge.test1;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Properties;

public class PropertyReader {
    private Context context;
    private Properties properties;

    public PropertyReader(Context context){
        this.context = context;
        properties = new Properties(  );
    }

    public Properties load_properties(String file_name) {
        File file = new File(context.getFilesDir(), file_name);
        try{
            if(!file.exists())
                return get_asset_prop( file_name );
            InputStreamReader in = new InputStreamReader( new FileInputStream( file ) );
            properties.load( in );
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        }

        return properties;
    }

    private Properties get_asset_prop(String file) throws  IOException{
        AssetManager assetManager = context.getAssets();

        InputStream inputStream;

        inputStream = assetManager.open( file );
        properties.load(inputStream);

        return properties;
    }

    public void save_properties(String file_name){

        try {
            FileOutputStream out = context.openFileOutput( file_name, Context.MODE_PRIVATE );
            Shared.properties.store(out, "gg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
