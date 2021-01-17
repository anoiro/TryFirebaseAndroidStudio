package to.msn.wings.tryfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.functions.FirebaseFunctions;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnCurrent_onClick(View view){
        TextView txt = (TextView)findViewById(R.id.txtResult);
        txt.setText(new Date().toString());

        // トーストバージョン
//        Toast toast = Toast.makeText(
//                this, new Date().toString(), Toast.LENGTH_LONG);
//        toast.show();

        // FirestoreのDB
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // データの登録
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);

        // Add a new document with a generated ID
//        db.collection("users")
//                .add(user)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    // 登録成功
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Toast toast = Toast.makeText(
//                                MainActivity.this, "DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_LONG);
//                        toast.show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    // 登録失敗
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast toast = Toast.makeText(
//                                MainActivity.this, "失敗した", Toast.LENGTH_LONG);
//                        toast.show();
//                    }
//                });

        // データの取得
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        // 取得成功
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Toast toast = Toast.makeText(
                                        MainActivity.this, document.getId() + " => " + document.get("first"), Toast.LENGTH_LONG);
                                toast.show();
                            }
                        // 取得失敗
                        } else {
                            Toast toast = Toast.makeText(
                                    MainActivity.this, "Error getting documents.", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                });
    }

    // 画面回転対策
    // 画面が破棄される前に状態を保存
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        TextView txtResult = (TextView)findViewById(R.id.txtResult);
        outState.putString("txtResult", txtResult.getText().toString());
    }

    // 画面が復元される際に状態を取り出し
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        TextView txtResult = (TextView)findViewById(R.id.txtResult);
        txtResult.setText(savedInstanceState.getString("txtResult"));
    }
}