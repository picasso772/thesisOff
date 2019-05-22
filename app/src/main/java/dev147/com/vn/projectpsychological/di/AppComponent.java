package dev147.com.vn.projectpsychological.di;

import android.app.Application;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import dev147.com.vn.projectpsychological.MainApplication;
import dev147.com.vn.projectpsychological.di.module.ActivityBuildersModule;
import dev147.com.vn.projectpsychological.di.module.AppModule;
import dev147.com.vn.projectpsychological.di.module.PsychologicalDBModule;
import dev147.com.vn.projectpsychological.di.module.ViewModelModule;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityBuildersModule.class,
        PsychologicalDBModule.class,
        ViewModelModule.class
})
public interface AppComponent {

    void inject(MainApplication tvMazeApplication);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
