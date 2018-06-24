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

    public static Properties load_properties(Context context, String file_name) {
        File file = new File(context.getFilesDir(), file_name);
        Properties properties = new Properties(  );
        try{
            if(!file.exists())
                return get_asset_prop( context, file_name, properties );
            InputStreamReader in = new InputStreamReader( new FileInputStream( file ) );
            properties.load( in );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    private static Properties get_asset_prop(Context context, String file, Properties properties) throws  IOException{
        AssetManager assetManager = context.getAssets();

        InputStream inputStream;
        inputStream = assetManager.open( file );
        properties.load(inputStream);

        return properties;
    }

    public static void save_properties(Context context, String file_name, Properties properties){
        if(properties == null)
            return;

        try {
            FileOutputStream out = context.openFileOutput( file_name, Context.MODE_PRIVATE );
            properties.store(out, "gg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
