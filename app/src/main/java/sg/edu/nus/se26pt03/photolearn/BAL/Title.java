package sg.edu.nus.se26pt03.photolearn.BAL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chen ping on 3/10/2018.
 */

public abstract class Title {
    public int id;
    public String title;
    public int sessionId;
    public int createdBy;
    public Date timestamp;

    public abstract void createItem(Item item) ;
    public abstract void updateItem(Item item);
    public abstract void deleteItem(int itemId) ;
    public abstract List<Item> getItems();

}
