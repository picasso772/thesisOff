package longnd.com.vn.thesis.ui.splash;

import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.github.ybq.android.spinkit.style.CubeGrid;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import dev147.com.vn.projectpsychological.R;
import longnd.com.vn.thesis.data.base.ObjectResponse;
import longnd.com.vn.thesis.data.entity.User;
import longnd.com.vn.thesis.data.model.Customer;
import dev147.com.vn.projectpsychological.databinding.ActivitySplashBinding;
import longnd.com.vn.thesis.ui.base.BaseActivity;
import longnd.com.vn.thesis.ui.customer.CustomerViewModel;
import longnd.com.vn.thesis.ui.main.MainActivity;
import longnd.com.vn.thesis.ui.tutorial.TutorialActivity;
import longnd.com.vn.thesis.utils.DataUtils;
import longnd.com.vn.thesis.utils.Define;
import longnd.com.vn.thesis.utils.Fields;
import longnd.com.vn.thesis.utils.NetworkUtils;
import longnd.com.vn.thesis.utils.SharedPrefs;
import longnd.com.vn.thesis.utils.ToastUtils;
import longnd.com.vn.thesis.utils.Utils;

public class SplashActivity extends BaseActivity<SplashViewModel, ActivitySplashBinding> {
    private CustomerViewModel customerViewModel;

    @Override
    protected void initView() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.my_statusbar_color));
        }
    }

    @Override
    protected void initData() {
        CubeGrid stylePro = new CubeGrid();
        binding.progressBar.setIndeterminateDrawable(stylePro);
        if (!NetworkUtils.isNetworkAvailable(this)) {
            Log.d("QUANGANHs", "initData: Khoong cos ke noi");
        }
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashViewModel.class);
        customerViewModel = ViewModelProviders.of(this, viewModelFactory).get(CustomerViewModel.class);
        User user = Utils.getUser();
        if (!user.isEmpty()) {
            customerViewModel.getCustomerByEmail(user.getEmail(), user.getPass());
        } else {
            // saveQuestionInDB();
            loadQuestion();
        }
    }

    private void loadQuestion() {
        if (DataUtils.getInstance().getNeos() == null) {
            DataUtils.getInstance().setNeos(viewModel.readDataNeo(this, Define.Question.TYPE_NEO));
        }
        if (DataUtils.getInstance().getRiasec() == null) {
            DataUtils.getInstance().setRiasec(viewModel.readDataRiasec(this, Define.Question.TYPE_RIASEC));
        }
        if (DataUtils.getInstance().getPsychological() == null) {
            DataUtils.getInstance().setPsychological(viewModel.readDataPsy(this, Define.Question.TYPE_PSY_POCHOLIGICAL));
        }
        openWorkingScreen();
    }


    @Override
    protected void initListenerOnClick() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        initObserve();
    }

    private void saveQuestionInDB() {
        if (!SharedPrefs.getInstance().getBoolean(Define.SharedPref.KEY_SAVE_QUESTION, false)) {
            viewModel.saveData(this);
        } else {
            openWorkingScreen();
        }
    }

    private void initObserve() {
        customerViewModel.getObserveCustomerByEmail().observe(this, this::observeCustomer);
        viewModel.getIsInsertListQuestionSuccess().observe(this, this::observeSaveQuestion);
    }

    private void observeSaveQuestion(ObjectResponse<Boolean> booleanObjectResponse) {
        if (booleanObjectResponse == null) {
            return;
        }
        switch (booleanObjectResponse.getStatus()) {
            case Define.ResponseStatus.LOADING:
                Log.d("QUANGANHs", "observeSaveQuestion: loading");
                break;
            case Define.ResponseStatus.SUCCESS:
                viewModel.setIsInsertListQuestionSuccess(null);
                viewModel.getIsInsertListQuestionSuccess().removeObservers(this);
                SharedPrefs.getInstance().putBoolean(Define.SharedPref.KEY_SAVE_QUESTION, true);
                openWorkingScreen();
                break;
            case Define.ResponseStatus.ERROR:
                Log.d("QUANGANHs", "observeSaveQuestion: error");
                break;
            default:
                break;
        }
    }

    private void observeCustomer(ObjectResponse<Customer> customerObjectResponse) {
        if (customerObjectResponse == null) {
            return;
        }
        customerViewModel.getObserveCustomerByEmail().setValue(null);
        switch (customerObjectResponse.getStatus()) {
            case Define.ResponseStatus.LOADING:
                break;
            case Define.ResponseStatus.SUCCESS:
                Customer customer = customerObjectResponse.getData();
                if (customer != null) {
                    ToastUtils.showToastSuccess(this, "Chào mừng " + customer.getLastName());
                    DataUtils.getInstance().setCustomer(customer);
                }
                // xong rồi thì loadQuestion chứ ko cần lưu database nữa
                loadQuestion();
                //saveQuestionInDB();
                break;
            case Define.ResponseStatus.ERROR:
                Toast.makeText(this, "Rất tiếc, việc lấy dữ liệu đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void observeCountCustomerSaveDb(ObjectResponse<Long> integerObjectResponse) {
        switch (integerObjectResponse.getStatus()) {
            case Define.ResponseStatus.LOADING:
                break;
            case Define.ResponseStatus.SUCCESS:
                int count = integerObjectResponse.getData().intValue();
                if (count == Fields.NO_DATA) {
                    // no data

                    return;
                }
                break;
            case Define.ResponseStatus.ERROR:
                break;
            default:
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void onClick(View v) {

    }

    private void openWorkingScreen() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (!SharedPrefs.getInstance().getBoolean(Define.SharedPref.KEY_IS_FIRST, false)) {
                    intent = new Intent(SplashActivity.this, TutorialActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        }, 2000);
    }
}
