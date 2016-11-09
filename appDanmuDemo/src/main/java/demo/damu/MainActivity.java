package demo.damu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.danmu.DamBuilder;
import com.danmu.DamuFloating;
import com.danmu.Danmu;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.et_dm)
    EditText etDm;
    @BindView(R.id.btn_send)
    Button btnSend;
    @BindView(R.id.root)
    RelativeLayout root;

    DamBuilder dam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        dam = new DamBuilder(this,root)
        .setDamPos(DamuFloating.DamuPos.Top)
        .setOnLikeClickListener(lickClickListener)
        .setDamTxtSize(22)
        ;

    }

    @OnClick(R.id.btn_send)
    public void onClick() {
        String txt = etDm.getText().toString();
        dam.sendDanmu(new Danmu(15,txt));
    }

    DamBuilder.OnLickClickListener lickClickListener = new DamBuilder.OnLickClickListener() {
        @Override
        public void onLickClick(int danmuId) {
            Log.e(TAG,"click like id : "+danmuId);
        }
    };
}
