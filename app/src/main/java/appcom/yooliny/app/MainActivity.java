package appcom.yooliny.app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.king.zxing.CaptureActivity;
import com.king.zxing.CaptureFragment;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_TITLE = "key_title";
    private static final String KEY_IS_CONTINUOUS = "key_continuous_scan";
    public static final int REQUEST_CODE_SCAN = 0X01;
    private Class<?> cls;

    private static final int RC_CAMERA = 0X01;
    private Toast toast;
    private String title;
    private boolean isContinuousScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = findViewById(R.id.btn0);
        btn.setOnClickListener(view -> {
            isContinuousScan = false;
            cls = CaptureActivity.class;
            title = ((Button)view).getText().toString();
            checkCameraPermissions();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    @AfterPermissionGranted(RC_CAMERA)
    public void checkCameraPermissions(){
        String[] perms = {Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this,perms)){
            startScan(cls,title);
            showToast("有权限");
        }else {
            EasyPermissions.requestPermissions(this,getString(R.string.permission_camera),RC_CAMERA,perms);
        }
    }

    private void showToast(String text) {
        if(toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(this,text,Toast.LENGTH_LONG);
        toast.show();
    }

    public void startScan(Class<?> cls,String title){
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeCustomAnimation(this,R.anim.in,R.anim.out);
        Intent intent = new Intent(this,cls);
        intent.putExtra(KEY_TITLE,title);
        intent.putExtra(KEY_IS_CONTINUOUS,isContinuousScan);
        ActivityCompat.startActivityForResult(this,intent,REQUEST_CODE_SCAN,optionsCompat.toBundle());
    }
}