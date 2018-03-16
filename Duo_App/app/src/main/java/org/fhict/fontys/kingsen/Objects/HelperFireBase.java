package org.fhict.fontys.kingsen.Objects;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

public class HelperFireBase {

    DatabaseReference db;
    Boolean saveGroup = null;

    public HelperFireBase(DatabaseReference db) {
        this.db = db;
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


