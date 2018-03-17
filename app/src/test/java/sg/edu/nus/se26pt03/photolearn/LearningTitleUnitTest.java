package sg.edu.nus.se26pt03.photolearn;

import android.support.annotation.NonNull;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import sg.edu.nus.se26pt03.photolearn.BAL.Item;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningItem;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizItem;
import sg.edu.nus.se26pt03.photolearn.database.BaseRepo;

import static org.junit.Assert.assertEquals;

/**
 * Created by c.banisetty on 3/13/2018.
 */


public class LearningTitleUnitTest {

    private int TitleID=1;
    private String TitleName="Test Title";
    private int createdBy=12121;
    LearningTitle title=null;
    private Collection<Item> learningItems=null;
    public LearningTitleUnitTest() {
        SetInitialValues();
    }

    private void SetInitialValues() {
        learningItems = new ArrayList<Item>();
        LearningItem item1=new LearningItem();
        item1.setTitleId(this.TitleID);
        item1.setContent("How old are you ?");
        Date d= new Date();
        item1.setTimestamp(d);
        item1.setCreatedBy(this.createdBy);

        QuizItem item2=new QuizItem();
        item2.setTitleId(this.TitleID);
        item2.setContent("Where are you from ?");
        Date d2= new Date();
        item2.setTimestamp(d2);
        item2.setCreatedBy(this.createdBy);
        learningItems.add(item1);
        learningItems.add(item2);
       /* title=new LearningTitle();
        title.setCreatedBy('1');
        title.setId(this.TitleID);
        title.setTitle(this.TitleName);
        title.setSessionId(1);
        title.setTimestamp(new Date());*/
    }

    @Test
    public void Assert_CreateLearningItem() throws Exception {

        LearningItem item1=new LearningItem();
        item1.setTitleId(this.TitleID);
        item1.setContent("How old are you ?");
        Date d= new Date();
        item1.setTimestamp(d);
        item1.setCreatedBy(this.createdBy);
        //title.createItem(item1);
    }
    @Test
    public void Assert_CreateQuizItem() throws Exception {


    }
    @Test
    public void Assert_UpdateItem() throws Exception {

    }

    @Test
    public void Assert_DeleteItem() throws Exception {

    }

    @Test
    public void Assert_GetItem() throws Exception {

    }
}
