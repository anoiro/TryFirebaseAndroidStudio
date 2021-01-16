package to.msn.wings.tryfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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

        // ドキュメントとコレクションの指定
        DocumentReference mDocRef = FirebaseFirestore.getInstance().document("test/sample");
        // Firebaseからの受信
        mDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //値が取得できた時の処理
                        String save = (String) document.get("sampleString");
                        Toast toast = Toast.makeText(
                                MainActivity.this, save, Toast.LENGTH_LONG);
                        toast.show();
                    }else{
                        Toast toast = Toast.makeText(
                                MainActivity.this, "documentがなかった", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }else{
                    Toast toast = Toast.makeText(
                            MainActivity.this, "値の取得に失敗", Toast.LENGTH_LONG);
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