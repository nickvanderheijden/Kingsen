package org.fhict.fontys.kingsen.Objects;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Maarten on 9-3-2018.
 */

public class AuthenticationReference {
    private static FirebaseAuth Auth;

    public AuthenticationReference(){Auth = FirebaseAuth.getInstance();}

    public static FirebaseAuth getAuth() {
        return Auth;
    }
}
