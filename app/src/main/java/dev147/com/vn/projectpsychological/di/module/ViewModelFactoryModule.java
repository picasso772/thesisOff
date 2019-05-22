package dev147.com.vn.projectpsychological.di.module;

import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dev147.com.vn.projectpsychological.di.ViewModelFactory;

@Module
public abstract class ViewModelFactoryModule {
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory viewModelFactory);
}
