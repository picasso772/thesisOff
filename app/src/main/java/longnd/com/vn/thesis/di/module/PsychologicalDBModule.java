package longnd.com.vn.thesis.di.module;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import dagger.Module;
import dagger.Provides;
import longnd.com.vn.thesis.data.AbstractAppDatabase;
import longnd.com.vn.thesis.utils.Define;

import javax.inject.Singleton;
import java.util.concurrent.Executors;

@Module
public class PsychologicalDBModule {
    @Singleton
    @Provides
    AbstractAppDatabase provideRoomDatabase(Context context) {
        return Room.databaseBuilder(context, AbstractAppDatabase.class, Define.DATABASE_NAME)
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadExecutor().execute(() -> {
                            provideRoomDatabase(context).customerDao().existsCustomerByEmail("","").subscribe();
                        });
                    }
                })
                .build();
    }
}
