package org.fhict.fontys.kingsen.Objects;

import java.util.List;

/**
 * Created by Nick on 15-3-2018.
 */

public class Group {
    private String name;
    private List<String> users;

    public Group(String name, List<String> users){
        this.name = name;
        this.users = users;

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getUsers(){return users;}

}
