package longnd.com.vn.thesis.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import longnd.com.vn.thesis.data.converter.DateConverter;
import longnd.com.vn.thesis.data.dao.CustomerDao;
import longnd.com.vn.thesis.data.dao.QuestionDao;
import longnd.com.vn.thesis.data.dao.ResultDao;
import longnd.com.vn.thesis.data.model.Customer;
import longnd.com.vn.thesis.data.model.Question;
import longnd.com.vn.thesis.data.model.Result;
import longnd.com.vn.thesis.data.model.ResultNeo;
import longnd.com.vn.thesis.data.model.ResultPsychological;
import longnd.com.vn.thesis.data.model.ResultRiasec;
import longnd.com.vn.thesis.utils.Define;

@Database(entities = {
        Customer.class, Question.class, ResultNeo.class,
        Result.class, ResultRiasec.class, ResultPsychological.class
}, version = Define.DATABASE_VERSION, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AbstractAppDatabase extends RoomDatabase {
    public abstract CustomerDao customerDao();

    public abstract QuestionDao questionDao();

    public abstract ResultDao resultDao();

    private static AbstractAppDatabase INSTANCE;

    public static AbstractAppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AbstractAppDatabase.class, Define.DATABASE_NAME).build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
