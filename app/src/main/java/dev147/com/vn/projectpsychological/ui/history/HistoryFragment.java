package dev147.com.vn.projectpsychological.ui.history;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import dev147.com.vn.projectpsychological.R;
import dev147.com.vn.projectpsychological.adapter.HistoryAdapter;
import dev147.com.vn.projectpsychological.data.base.ListResponse;
import dev147.com.vn.projectpsychological.data.base.ObjectResponse;
import dev147.com.vn.projectpsychological.data.model.Result;
import dev147.com.vn.projectpsychological.data.model.ResultNeo;
import dev147.com.vn.projectpsychological.data.model.ResultRiasec;
import dev147.com.vn.projectpsychological.databinding.FragmentHistoryBinding;
import dev147.com.vn.projectpsychological.ui.base.BaseFragment;
import dev147.com.vn.projectpsychological.ui.evaluate.EvaluateActivity;
import dev147.com.vn.projectpsychological.utils.DataUtils;
import dev147.com.vn.projectpsychological.utils.Define;
import dev147.com.vn.projectpsychological.utils.Fields;
import dev147.com.vn.projectpsychological.utils.ToastUtils;
import dev147.com.vn.projectpsychological.utils.Utils;

public class HistoryFragment extends BaseFragment<HistoryViewModel, FragmentHistoryBinding> implements HistoryAdapter.OpenHistoryEvaluate {
    private Handler handler;
    private HistoryAdapter adapter;
    private Intent intent;
    private Bundle bundle;

    @Override
    protected void initListenerOnClick() {

    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void initView() {
        if (DataUtils.getInstance().getCustomer() != null) {
            binding.tvNotifiNoSignin.setVisibility(View.GONE);
            binding.layoutLoading.setVisibility(View.VISIBLE);
            binding.tvSubLoading.setTypeface(Utils.getTypeFace(getContext(), Fields.FONT_TIMES));
            binding.tvLoading.setTypeface(Utils.getTypeFace(getContext(), Fields.FONT_TIMES));
            if (handler == null) {
                handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 0:
                                binding.tvLoading.setText("Xin chờ.");
                                break;
                            case 1:
                                binding.tvLoading.setText("Xin chờ..");
                                break;
                            case 2:
                                binding.tvLoading.setText("Xin chờ...");
                                break;
                            case 3:
                                binding.layoutLoading.setVisibility(View.GONE);
                                binding.layoutHistory.setVisibility(View.VISIBLE);
                                break;
                        }
                    }
                };
            }
            runThread();

            binding.listHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.getResults().observe(getViewLifecycleOwner(), this::observeGetResults);
    }

    @Override
    protected void initObserve() {
        viewModel.getResultNeo().observe(getViewLifecycleOwner(), this::observeGetNeo);
        viewModel.getResultRiasec().observe(getViewLifecycleOwner(), this::observeGetRíaec);
    }

    private void observeGetRíaec(ObjectResponse<ResultRiasec> resultRiasecObjectResponse) {
        if (resultRiasecObjectResponse == null) {
            return;
        }
        switch (resultRiasecObjectResponse.getStatus()) {
            case Define.ResponseStatus.LOADING:
                break;
            case Define.ResponseStatus.SUCCESS:
                viewModel.getResultRiasec().removeObservers(getViewLifecycleOwner());
                viewModel.setResultRiasec(null);
                intent.putExtra(Fields.KEY_TYPE, Define.Question.TYPE_RIASEC);
                bundle.putSerializable(Fields.KEY_VALUE, resultRiasecObjectResponse.getData());
                intent.putExtra("QUA", bundle);
                startActivity(intent);
                break;
            case Define.ResponseStatus.ERROR:
                viewModel.getResultRiasec().removeObservers(getViewLifecycleOwner());
                viewModel.setResultRiasec(null);
                ToastUtils.showToastError(getContext());
                break;
        }
    }

    private void observeGetNeo(ObjectResponse<ResultNeo> resultNeoObjectResponse) {
        if (resultNeoObjectResponse == null) {
            return;
        }
        switch (resultNeoObjectResponse.getStatus()) {
            case Define.ResponseStatus.LOADING:
                break;
            case Define.ResponseStatus.SUCCESS:
                viewModel.getResultNeo().removeObservers(getViewLifecycleOwner());
                viewModel.setResultNeo(null);
                intent.putExtra(Fields.KEY_TYPE, Define.Question.TYPE_NEO);
                bundle.putSerializable(Fields.KEY_VALUE, resultNeoObjectResponse.getData());
                intent.putExtra("QUA", bundle);
                startActivity(intent);
                break;
            case Define.ResponseStatus.ERROR:
                viewModel.getResultNeo().removeObservers(getViewLifecycleOwner());
                viewModel.setResultNeo(null);
                ToastUtils.showToastError(getContext());
                break;
        }
    }

    private void observeGetResults(ListResponse<Result> resultListResponse) {
        if (resultListResponse == null) {
            return;
        }
        switch (resultListResponse.getStatus()) {
            case Define.ResponseStatus.LOADING:
                break;
            case Define.ResponseStatus.SUCCESS:
                viewModel.getResults().removeObservers(getViewLifecycleOwner());
                viewModel.setResults(null);
                adapter = new HistoryAdapter(getContext(), resultListResponse.getData());
                adapter.setOpenHistoryEvaluate(this);
                binding.listHistory.setAdapter(adapter);
                break;
            case Define.ResponseStatus.ERROR:
                viewModel.getResults().removeObservers(getViewLifecycleOwner());
                viewModel.setResults(null);
                break;
        }
    }

    private void runThread() {
        new Thread() {
            @Override
            public void run() {
                int i = 0;
                while (i < 3) {
                    switch (i % 3) {
                        case 0:
                            handler.sendEmptyMessage(0);
                            break;
                        case 1:
                            handler.sendEmptyMessage(1);
                            break;
                        case 2:
                            handler.sendEmptyMessage(2);
                            break;
                    }
                    i++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Log.e("AppLog", "runThread: ", e);
                    }
                }
                handler.sendEmptyMessage(3);
            }
        }.start();
    }

    @Override
    protected void initData() {
        if (DataUtils.getInstance().getCustomer() != null) {
            viewModel.getListResult(DataUtils.getInstance().getCustomer().getId());
        }

        intent = new Intent(getActivity(), EvaluateActivity.class);
        bundle = new Bundle();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public Class<HistoryViewModel> getModelClass() {
        return HistoryViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_history;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onOpenEvaluate(int type, int id) {
        switch (type) {
            case Define.Question.TYPE_NEO:
                viewModel.getNeo(id);
                break;
            case Define.Question.TYPE_RIASEC:
                viewModel.getRiasec(id);
                break;
            case Define.Question.TYPE_PSY_POCHOLIGICAL:
                break;
        }
    }
}
