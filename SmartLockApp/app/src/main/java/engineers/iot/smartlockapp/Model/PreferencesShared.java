package engineers.iot.smartlockapp.Model;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesShared {

    private final SharedPreferences preferences;

    private static final String PREF_NAME = "engineers.iot.smartlockapp.Model";

    public PreferencesShared(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }
}
