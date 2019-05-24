package longnd.com.vn.thesis.ui.tutorial;

import android.content.Intent;
import android.view.View;
import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjector;
import dev147.com.vn.projectpsychological.R;
import dev147.com.vn.projectpsychological.databinding.ActivityTutorialBinding;
import longnd.com.vn.thesis.ui.base.BaseActivity;
import longnd.com.vn.thesis.ui.main.MainActivity;
import longnd.com.vn.thesis.utils.Define;
import longnd.com.vn.thesis.utils.SharedPrefs;

public class TutorialActivity extends BaseActivity<TutorialViewModel, ActivityTutorialBinding> {
    @Override
    protected void initView() {

    }
    @Override
    protected void initData() {
        SharedPrefs.getInstance().putBoolean(Define.SharedPref.KEY_IS_FIRST, true);
        binding.btnStartApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TutorialActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_tutorial;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return null;
    }

    @Override
    protected void initListenerOnClick() {

    }

    @Override
    public void onClick(View v) {

    }


}
