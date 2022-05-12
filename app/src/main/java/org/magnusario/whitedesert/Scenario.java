package org.magnusario.whitedesert;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;

import org.magnusario.whitedesert.engine.event.AddItem;
import org.magnusario.whitedesert.engine.event.CustomMusic;
import org.magnusario.whitedesert.engine.event.Die;
import org.magnusario.whitedesert.engine.event.Hint;
import org.magnusario.whitedesert.engine.event.IEvent;
import org.magnusario.whitedesert.engine.event.ImportantMessage;
import org.magnusario.whitedesert.engine.event.Question;
import org.magnusario.whitedesert.engine.event.Questions;
import org.magnusario.whitedesert.engine.event.RandomEvent;
import org.magnusario.whitedesert.engine.event.RemoveItem;
import org.magnusario.whitedesert.engine.event.Stage;
import org.magnusario.whitedesert.engine.event.StageJump;
import org.magnusario.whitedesert.engine.event.TacticalChildNode;
import org.magnusario.whitedesert.engine.event.TacticalEvent;
import org.magnusario.whitedesert.engine.event.TextMessage;
import org.magnusario.whitedesert.engine.event.Waiting;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.TreeMap;

/**
 * Created by sergey37192 on 30.01.2018.
 */

public class Scenario {
    //Коллекция сценария
    // public static TreeMap<String, ArrayList<Event>> scenario = new TreeMap<>();
    public static TreeMap<String, Stage> scenarioList = new TreeMap<>();
    static String TAG = "MyLogScenario";


    public static void loadSceanrio(Context context) throws XmlPullParserException, IOException {
        Resources res = context.getResources();
        XmlResourceParser parser = res.getXml(R.xml.scenario);
        String tagName = null, stageName = null;
        Stage stage = null;
        Questions questions = null;
        TacticalEvent tacticalEvent = null;
        TacticalChildNode tchildNode = null;
        parser.next();
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {

            if (eventType == XmlPullParser.START_TAG) {
                if ((tagName = parser.getName()).equals("stage")) {
                    stage = new Stage(parser.getAttributeValue(null, "id"));
                    stageName = parser.getAttributeValue(null, "id");
                } else if (stage != null) {
                    if (tagName.equals("message")) {
                        TextMessage mes = new TextMessage();
                        mes.setText(parser.getAttributeValue(null, "text"));
                        stage.addToArray(mes);
                    } else if (tagName.equals("important_message")) {
                        ImportantMessage importantMessage = new ImportantMessage();
                        importantMessage.setText(parser.getAttributeValue(null, "text"));
                        stage.addToArray(importantMessage);
                    } else if (tagName.equals("questions")) {
                        questions = new Questions();
                    } else if (tagName.equals("case")) {
                        if (questions != null) {
                            Question question = new Question();
                            question.setText(parser.getAttributeValue(null, "text"));
                            question.setTarget(parser.getAttributeValue(null, "target"));
                            questions.put(question);
                        }
                    } else if (tagName.equals("waiting")) {
                        Waiting waiting = new Waiting();
                        waiting.setValue(parser.getAttributeIntValue(null, "value", 2500));
                        stage.addToArray(waiting);
                    } else if (tagName.equals("random_event")) {
                        RandomEvent randomEvent = new RandomEvent(parser.getAttributeIntValue(null, "chance", 15));
                        randomEvent.setTarget(parser.getAttributeValue(null, "target"));
                        stage.addToArray(randomEvent);
                    } else if (tagName.equals("add_item")) {
                        AddItem addItem = new AddItem();
                        addItem.setItem(parser.getAttributeIntValue(null, "item", -1));
                        stage.addToArray(addItem);
                    } else if (tagName.equals("remove_item")) {
                        RemoveItem removeItem = new RemoveItem();
                        removeItem.setItem(parser.getAttributeIntValue(null, "item", -1));
                        stage.addToArray(removeItem);
                    } else if (tagName.equals("die")) {
                        Die die = new Die();
                        stage.addToArray(die);
                    } else if (tagName.equals("music")) {
                        CustomMusic music = new CustomMusic();
                        music.set_music_name(parser.getAttributeValue(null, "title"));
                        stage.addToArray(music);
                    } else if (tagName.equals("stage_jump")) {
                        StageJump stageJump = new StageJump();
                        stageJump.setTarget(parser.getAttributeValue(null, "target"));
                        stage.addToArray(stageJump);
                    } else if (tagName.equals("tactics")) {
                        tacticalEvent = new TacticalEvent();
                        tacticalEvent.addLink(parser.getAttributeValue(null, "link"));
                        stage.addToArray(tacticalEvent);
                    } else if (tagName.equals("tactical_stage") && tacticalEvent != null) {
                        tchildNode = new TacticalChildNode();
                        tchildNode.setText(parser.getAttributeValue(null, "text"))
                                .setId(parser.getAttributeValue(null, "id"))
                                .setTarget(parser.getAttributeValue(null, "target"));
                        tacticalEvent.add(tchildNode);

                    } else if (tagName.equals("tchoice") && tchildNode != null) {
                        tchildNode.addChoice(parser.getAttributeValue(null, "text"), parser.getAttributeValue(null, "target_id"));
                    } else if (tagName.equals("hint")) {
                        Hint hint = new Hint();
                        hint.setText(parser.getAttributeValue(null, "text"));
                        stage.addToArray(hint);
                    }

                }
                Log.i(TAG, parser.getName());
            }
            if (eventType == XmlPullParser.END_TAG) {
                if (parser.getName().equals("stage")) {
                    if (stage != null && stageName != null) {
                        scenarioList.put(stageName, stage);
                        stageName = null;
                        stage = null;
                    }
                } else if (parser.getName().equals("questions")) {
                    if (questions != null && questions.getList().size() != 0)
                        stage.addToArray(questions);
                } else if (parser.getName().equals("tactics")) {
                    if (tacticalEvent != null) {
                        stage.addToArray(tacticalEvent);
                        tacticalEvent = null;
                    }
                }
            }
            eventType = parser.next();
        }
    }

    public static LinkedList<IEvent> events = new LinkedList<>();

    public static void create(Context context) throws XmlPullParserException, IOException {
        Resources res = context.getResources();
        XmlPullParser xpp = res.getXml(R.xml.scenario);

        xpp.next();
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                try {
                    IEvent event = (IEvent) Class.forName(xpp.getName()).newInstance();
                    if (!events.isEmpty())
                        events.getLast().add(event);
                    events.add(event);
                    int len = xpp.getAttributeCount();
                    Properties pr = new Properties();
                    for (int i = 0; i < len; i++) {
                        pr.put(xpp.getAttributeName(i), xpp.getAttributeValue(i));
                    }
                    event.serProperties(pr);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if (eventType == XmlPullParser.END_TAG) {
                IEvent event = events.removeLast();
                if (event instanceof Stage) {
                }
            }
        }

    }
}
