package org.fhict.fontys.kingsen.Objects;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
/**
 * Created by Nick on 15-3-2018.
 */

public class HelperFireBase {

    DatabaseReference db;
    Boolean saveGroup = null;

    public HelperFireBase(DatabaseReference dbr) {
        this.db = dbr;
    }
    public Boolean save(Group group) {

        if (group == null) {
            saveGroup = false;
        } else {
            try {
               db.child("Group").setValue(group);
                saveGroup = true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                saveGroup = false;

            }
        }
        return saveGroup;
    }
}


