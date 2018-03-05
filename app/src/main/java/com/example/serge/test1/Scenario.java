package com.example.serge.test1;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;

import com.example.serge.test1.Objects.Contains.CustomEvents;
import com.example.serge.test1.Objects.Contains.ImportantMessage;
import com.example.serge.test1.Objects.Contains.Question;
import com.example.serge.test1.Objects.Contains.Questions;
import com.example.serge.test1.Objects.Contains.RandomEvent;
import com.example.serge.test1.Objects.Contains.TextMessage;
import com.example.serge.test1.Objects.Contains.Waiting;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by sergey37192 on 30.01.2018.
 */

public class Scenario {
    //Коллекция сценария
    public static TreeMap<String, ArrayList<CustomEvents>> scenario = new TreeMap<>();
    static String TAG = "MyLogScenario";

    public static void loadSceanrio(Context context) throws XmlPullParserException, IOException{
        Resources res = context.getResources();
        XmlResourceParser parser = res.getXml(R.xml.scenario);
        ArrayList<CustomEvents> events = null;
        String tagName = null, stageName = null;
        Questions questions = null;
        parser.next();
        int eventType = parser.getEventType();
        while(eventType != XmlPullParser.END_DOCUMENT){

            if(eventType == XmlPullParser.START_TAG){
                if((tagName = parser.getName()).equals("stage")){
                    events = new ArrayList<>();
                    stageName = parser.getAttributeValue( null, "id");
                }
                else{
                    if(tagName.equals("message")){
                        TextMessage mes = new TextMessage();
                        mes.setText(parser.getAttributeValue( null, "text" ));
                        events.add(mes);
                    }else if(tagName.equals("important_message")){
                        ImportantMessage importantMessage = new ImportantMessage();
                        importantMessage.setText( parser.getAttributeValue( null, "text" ));
                        events.add(importantMessage);
                    }else if(tagName.equals( "questions" )){
                        questions = new Questions();
                    }else if(tagName.equals("case")){
                        if(questions!=null){
                            Question question = new Question();
                            question.setText(parser.getAttributeValue( null, "text" ));
                            question.setGoTo(parser.getAttributeValue( null, "target" ));
                            questions.put(question);
                        }
                    }else if(tagName.equals("waiting")){
                        Waiting waiting = new Waiting();
                        waiting.setValue(  parser.getAttributeIntValue(null, "value", 2500) );
                        events.add(waiting);
                    }else if(tagName.equals("random_event")){
                        RandomEvent randomEvent = new RandomEvent();
                        randomEvent.setStage(parser.getAttributeValue(null, "stage"));
                        events.add(randomEvent);
                    }

                }
                Log.i(TAG, parser.getName());
            }
            if(eventType == XmlPullParser.END_TAG){
                if(parser.getName().equals("stage")){
                    if(events!=null&&events.size()!=0&&stageName!=null){
                        scenario.put(stageName, events);
                        events = null;
                        stageName = null;
                    }
                }else if(parser.getName().equals( "questions" )){
                    if(questions!=null&&questions.getList().size()!=0)
                        events.add(questions);
                }
            }
            eventType = parser.next();
        }
    }
}
