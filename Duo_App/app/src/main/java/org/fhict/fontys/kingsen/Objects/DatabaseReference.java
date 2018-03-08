package org.fhict.fontys.kingsen.Objects;


import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Maarten on 8-3-2018.
 */

public class DatabaseReference {

    private static com.google.firebase.database.DatabaseReference database;

    public DatabaseReference()
    {
        database = FirebaseDatabase.getInstance().getReference();
    }

    public static com.google.firebase.database.DatabaseReference getDatabase() {
        return database;
    }
}
