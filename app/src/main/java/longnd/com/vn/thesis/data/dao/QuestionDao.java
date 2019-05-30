package longnd.com.vn.thesis.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import longnd.com.vn.thesis.data.model.Question;
import longnd.com.vn.thesis.utils.Define;
import io.reactivex.Completable;
import io.reactivex.Flowable;

import java.util.List;

@Dao
public interface QuestionDao {
    @Insert()
    Completable insertListQuestion(List<Question> questions);

    @Query("SELECT * FROM " + Define.Question.TABLE_NAME + " WHERE " + Define.Question.TYPE + " = :type ORDER BY " + Define.Question.NUMBER_ID + " ASC")
    Flowable<List<Question>> getQuestionsByType(int type);
}
