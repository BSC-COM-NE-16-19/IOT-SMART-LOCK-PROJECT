package engineers.iot.smartlockapp.Database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConnectDB {

    private final DatabaseReference databaseReference;

   public ConnectDB(String path) {
       FirebaseDatabase database = FirebaseDatabase.getInstance();
       databaseReference = database.getReference(path);
   }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }
}
