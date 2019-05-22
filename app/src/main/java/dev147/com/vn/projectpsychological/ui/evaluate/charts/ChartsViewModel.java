package dev147.com.vn.projectpsychological.ui.evaluate.charts;

import javax.inject.Inject;

import androidx.lifecycle.ViewModel;
import dev147.com.vn.projectpsychological.utils.EvaluateUtils;

public class ChartsViewModel extends ViewModel {
    @Inject
    ChartsViewModel() {

    }

    public int[] getResultsnNeo(int gender, int[] results) {
        if (gender == 0) {
            return EvaluateUtils.evaluateNeoFemale(results);
        } else if (gender == 1) {
            return EvaluateUtils.evaluateNeoMale(results);
        } else {
            return null;
        }
    }
}
