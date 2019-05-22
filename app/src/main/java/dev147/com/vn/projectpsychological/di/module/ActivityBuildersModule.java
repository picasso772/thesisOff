package dev147.com.vn.projectpsychological.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dev147.com.vn.projectpsychological.ui.customer.CustomerFragment;
import dev147.com.vn.projectpsychological.ui.customer.signin.SignInFragment;
import dev147.com.vn.projectpsychological.ui.customer.signup.SignUpFragment;
import dev147.com.vn.projectpsychological.ui.dialog.DialogEditCustomer;
import dev147.com.vn.projectpsychological.ui.evaluate.EvaluateActivity;
import dev147.com.vn.projectpsychological.ui.evaluate.charts.ChartsFragment;
import dev147.com.vn.projectpsychological.ui.evaluate.detail.DetailFragment;
import dev147.com.vn.projectpsychological.ui.history.HistoryFragment;
import dev147.com.vn.projectpsychological.ui.home.HomeFragment;
import dev147.com.vn.projectpsychological.ui.load_data.LoadDataFragment;
import dev147.com.vn.projectpsychological.ui.main.MainActivity;
import dev147.com.vn.projectpsychological.ui.question.QuestionFragment;
import dev147.com.vn.projectpsychological.ui.splash.SplashActivity;
import dev147.com.vn.projectpsychological.ui.test.TestActivity;
import dev147.com.vn.projectpsychological.ui.test.test_step_one.TestStepOneFragment;
import dev147.com.vn.projectpsychological.ui.test.test_step_two.TestStepTwoFragment;
import dev147.com.vn.projectpsychological.ui.tutorial.TutorialActivity;

@Module
public abstract class ActivityBuildersModule {
    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector
    abstract TutorialActivity bindTutorialActivity();

    @ContributesAndroidInjector
    abstract HomeFragment bindHomeFragment();

    @ContributesAndroidInjector
    abstract CustomerFragment bindCustomerFragment();

    @ContributesAndroidInjector
    abstract DialogEditCustomer bindEditCustomerDialog();

    @ContributesAndroidInjector
    abstract SignInFragment bindSignInCustomerFragment();

    @ContributesAndroidInjector
    abstract SignUpFragment bindSignUpCustomerFragment();

    @ContributesAndroidInjector
    abstract QuestionFragment bindQuestionFragment();

    @ContributesAndroidInjector
    abstract LoadDataFragment bindLoadDataFragment();

    @ContributesAndroidInjector
    abstract TestActivity bindTestActivity();

    @ContributesAndroidInjector
    abstract TestStepOneFragment bindTestStepOneFragment();

    @ContributesAndroidInjector
    abstract TestStepTwoFragment bindTestStepTwoFragment();

    @ContributesAndroidInjector
    abstract EvaluateActivity bindEvaluateActivity();

    @ContributesAndroidInjector
    abstract ChartsFragment bindChartsFragment();

    @ContributesAndroidInjector
    abstract DetailFragment bindDetailFragment();

    @ContributesAndroidInjector
    abstract HistoryFragment bindHistoryFragment();
}
