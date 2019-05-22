package dev147.com.vn.projectpsychological.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;
import dev147.com.vn.projectpsychological.R;
import dev147.com.vn.projectpsychological.directional.SettingTest;
import dev147.com.vn.projectpsychological.utils.Define;
import dev147.com.vn.projectpsychological.utils.SharedPrefs;

public class DialogSetting extends DialogFragment {
    private SettingTest settingTest;
    private SwitchCompat mSwitchNextTap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_setting, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mSwitchNextTap = view.findViewById(R.id.switchNextTap);
        mSwitchNextTap.setChecked(SharedPrefs.getInstance().getBoolean(Define.SharedPref.KEY_NEXT_TAP, false));
        mSwitchNextTap.setOnCheckedChangeListener(
                (buttonView, isChecked) ->
                        settingTest.setNextTap(isChecked)
        );
    }

    public void setSettingTest(SettingTest settingTest) {
        this.settingTest = settingTest;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }
}
