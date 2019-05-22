package dev147.com.vn.projectpsychological.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import dev147.com.vn.projectpsychological.di.ViewModelFactory;
import dev147.com.vn.projectpsychological.ui.customer.CustomerViewModel;
import dev147.com.vn.projectpsychological.ui.customer.signin.SignInViewModel;
import dev147.com.vn.projectpsychological.ui.customer.signup.SignUpViewModel;
import dev147.com.vn.projectpsychological.ui.dialog.EditCustomerViewModel;
import dev147.com.vn.projectpsychological.ui.evaluate.EvaluateViewModel;
import dev147.com.vn.projectpsychological.ui.evaluate.charts.ChartsViewModel;
import dev147.com.vn.projectpsychological.ui.evaluate.detail.DetailViewModel;
import dev147.com.vn.projectpsychological.ui.history.HistoryViewModel;
import dev147.com.vn.projectpsychological.ui.home.HomeViewModel;
import dev147.com.vn.projectpsychological.ui.load_data.LoadDataViewModel;
import dev147.com.vn.projectpsychological.ui.main.MainViewModel;
import dev147.com.vn.projectpsychological.ui.question.QuestionViewModel;
import dev147.com.vn.projectpsychological.ui.splash.SplashViewModel;
import dev147.com.vn.projectpsychological.ui.test.TestViewModel;
import dev147.com.vn.projectpsychological.ui.test.test_step_one.TestStepOneViewModel;
import dev147.com.vn.projectpsychological.ui.test.test_step_two.TestStepTwoViewModel;
import dev147.com.vn.projectpsychological.ui.tutorial.TutorialViewModel;

@Module
public abstract class ViewModelModule {
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel.class)
    abstract ViewModel bindSplashViewModel(SplashViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TutorialViewModel.class)
    abstract ViewModel bindTutorialViewModel(TutorialViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel bindHomeViewModel(HomeViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CustomerViewModel.class)
    abstract ViewModel bindCustomerViewModel(CustomerViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel.class)
    abstract ViewModel bindSignInCustomerViewModel(SignInViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel.class)
    abstract ViewModel bindSignUpCustomerViewModel(SignUpViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(EditCustomerViewModel.class)
    abstract ViewModel bindEditCustomerViewModel(EditCustomerViewModel viewModel);


    @Binds
    @IntoMap
    @ViewModelKey(QuestionViewModel.class)
    abstract ViewModel bindQuestionViewModel(QuestionViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoadDataViewModel.class)
    abstract ViewModel bindLoadDataViewModel(LoadDataViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TestViewModel.class)
    abstract ViewModel bindTestViewModel(TestViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TestStepOneViewModel.class)
    abstract ViewModel bindTestStepOneViewModel(TestStepOneViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TestStepTwoViewModel.class)
    abstract ViewModel bindTestStepTwoViewModel(TestStepTwoViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(EvaluateViewModel.class)
    abstract ViewModel bindEvaluateViewModel(EvaluateViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ChartsViewModel.class)
    abstract ViewModel bindChartsViewModel(ChartsViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel.class)
    abstract ViewModel bindDetailViewModel(DetailViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HistoryViewModel.class)
    abstract ViewModel bindHistoryViewModel(HistoryViewModel viewModel);
}
