package com.example.serge.test1;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;

import com.example.serge.test1.CustomEvents.AddItem;
import com.example.serge.test1.CustomEvents.CustomEvents;
import com.example.serge.test1.CustomEvents.CustomMusic;
import com.example.serge.test1.CustomEvents.Die;
import com.example.serge.test1.CustomEvents.ImportantMessage;
import com.example.serge.test1.CustomEvents.Question;
import com.example.serge.test1.CustomEvents.Questions;
import com.example.serge.test1.CustomEvents.RandomEvent;
import com.example.serge.test1.CustomEvents.RemoveItem;
import com.example.serge.test1.CustomEvents.Stage;
import com.example.serge.test1.CustomEvents.StageJump;
import com.example.serge.test1.CustomEvents.TacticalChoice;
import com.example.serge.test1.CustomEvents.TacticalEvent;
import com.example.serge.test1.CustomEvents.TextMessage;
import com.example.serge.test1.CustomEvents.Waiting;

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
    public static TreeMap<String, Stage> scenarioList = new TreeMap<>();
    static String TAG = "MyLogScenario";


    public static void loadSceanrio(Context context) throws XmlPullParserException, IOException{
        Resources res = context.getResources();
        XmlResourceParser parser = res.getXml(R.xml.scenario);
        String tagName = null, stageName = null;
        Stage stage = null;
        Questions questions = null;
        TacticalEvent tacticalEvent = null;
        parser.next();
        int eventType = parser.getEventType();
        while(eventType != XmlPullParser.END_DOCUMENT){

            if(eventType == XmlPullParser.START_TAG){
                if((tagName = parser.getName()).equals("stage")){
                    stage = new Stage(parser.getAttributeValue( null, "id" ));
                    stageName = parser.getAttributeValue( null, "id");
                }
                else if(stage != null){
                    if(tagName.equals("message")){
                        TextMessage mes = new TextMessage();
                        mes.setText(parser.getAttributeValue( null, "text" ));
                        stage.addToArray( mes );
                    }else if(tagName.equals("important_message")){
                        ImportantMessage importantMessage = new ImportantMessage();
                        importantMessage.setText( parser.getAttributeValue( null, "text" ));
                        stage.addToArray( importantMessage );
                    }else if(tagName.equals( "questions" )){
                        questions = new Questions();
                    }else if(tagName.equals("case")){
                        if(questions!=null){
                            Question question = new Question();
                            question.setText(parser.getAttributeValue( null, "text" ));
                            question.setNeedItem( parser.getAttributeIntValue( null, "need_item", -1 ) );
                            question.setTarget(parser.getAttributeValue( null, "target" ));
                            questions.put(question);
                        }
                    }else if(tagName.equals("waiting")){
                        Waiting waiting = new Waiting();
                        waiting.setValue(  parser.getAttributeIntValue(null, "value", 2500) );
                        stage.addToArray( waiting );
                    }else if(tagName.equals("random_event")){
                        RandomEvent randomEvent = new RandomEvent(parser.getAttributeIntValue( null, "chance", 15 ));
                        randomEvent.setTarget(parser.getAttributeValue(null, "target"));
                        stage.addToArray( randomEvent );
                    }
                    else if(tagName.equals("add_item")){
                        AddItem addItem = new AddItem();
                        addItem.setItem(parser.getAttributeIntValue( null, "item", -1 ));
                        stage.addToArray( addItem );
                    }else if(tagName.equals("remove_item")){
                        RemoveItem removeItem = new RemoveItem();
                        removeItem.setItem(parser.getAttributeIntValue( null, "item", -1 ));
                        stage.addToArray( removeItem );
                    }else if(tagName.equals("die")){
                        Die die = new Die();
                        stage.addToArray( die );
                    }else if(tagName.equals("music")){
                        CustomMusic music = new CustomMusic();
                        music.set_music_name( parser.getAttributeValue( null, "title" ) );
                        stage.addToArray( music );
                    }else if(tagName.equals( "stage_jump" )){
                        StageJump stageJump = new StageJump();
                        stageJump.setTarget( parser.getAttributeValue( null, "target" ) );
                        stage.addToArray( stageJump );
                    }else if(tagName.equals( "tactics" )){
                        tacticalEvent = new TacticalEvent();
                    }else if(tagName.equals( "tactical_choice" )){
                        if(tacticalEvent!=null){
                            TacticalChoice tacticalChoice = new TacticalChoice();
                            tacticalChoice.setText( parser.getAttributeValue( null, "text" ) )
                                    .setTarget( parser.getAttributeValue( null, "target" ))
                                    .setChance( parser.getAttributeIntValue( null, "chance", 0 ) );
                            tacticalEvent.add_choice( tacticalChoice );
                        }

                    }

                }
                Log.i(TAG, parser.getName());
            }
            if(eventType == XmlPullParser.END_TAG){
                if(parser.getName().equals("stage")){
                    if(stage!=null&&stageName!=null){
                        scenarioList.put(stageName, stage);
                        stageName = null;
                        stage = null;
                    }
                }else if(parser.getName().equals( "questions" )){
                    if(questions!=null&&questions.getList().size()!=0)
                        stage.addToArray( questions );
                }else if(parser.getName().equals( "tactics" )){
                    if(tacticalEvent!=null){
                        stage.addToArray( tacticalEvent );
                        tacticalEvent = null;
                    }
                }
            }
            eventType = parser.next();
        }
    }
}
