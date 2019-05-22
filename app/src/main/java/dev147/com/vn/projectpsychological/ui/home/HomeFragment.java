package dev147.com.vn.projectpsychological.ui.home;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import dev147.com.vn.projectpsychological.R;
import dev147.com.vn.projectpsychological.databinding.FragmentHomeBinding;
import dev147.com.vn.projectpsychological.ui.base.BaseFragment;
import dev147.com.vn.projectpsychological.ui.custom.ViewPagerCustom;
import dev147.com.vn.projectpsychological.ui.test.TestActivity;
import dev147.com.vn.projectpsychological.utils.DataUtils;
import dev147.com.vn.projectpsychological.utils.Define;
import dev147.com.vn.projectpsychological.utils.Fields;

public class HomeFragment extends BaseFragment<HomeViewModel, FragmentHomeBinding> {
    private ViewPagerCustom mPager;

    @Override
    public Class<HomeViewModel> getModelClass() {
        return HomeViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initListenerOnClick() {
        binding.ivNeo.setOnClickListener(this);
        binding.layoutNeo.setOnClickListener(this);
        binding.ivRiasec.setOnClickListener(this);
        binding.layoutRiasec.setOnClickListener(this);
        binding.ivPsy.setOnClickListener(this);
        binding.layoutPsy.setOnClickListener(this);
    }

    @Override
    protected void initObserve() {

    }

    @Override
    protected void initView() {
        binding.titleNeo.setSelected(true);
        binding.titleRiasec.setSelected(true);
        binding.titlePsycho.setSelected(true);

        ArrayList<Drawable> array = new ArrayList<>();
        array.add(getContext().getDrawable(R.drawable.flipper_one));
        array.add(getContext().getDrawable(R.drawable.flipper_two));
        array.add(getContext().getDrawable(R.drawable.flipper_three));
        for (int i = 0; i < array.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            Glide.with(getContext())
                    .load(array.get(i))
                    .into(imageView);
            binding.viewFlipper.addView(imageView);
        }

        binding.viewFlipper.setAutoStart(true);
        binding.viewFlipper.setFlipInterval(2000);
        Animation animation_in = AnimationUtils.loadAnimation(getContext(), R.anim.image_in);
        Animation animation_out = AnimationUtils.loadAnimation(getContext(), R.anim.image_out);
        binding.viewFlipper.setInAnimation(animation_in);
        binding.viewFlipper.setOutAnimation(animation_out);

    }

    @Override
    protected void initData() {
        mainViewModel.setNumberBack(Fields.ON_BACK);
    }

    @Override
    public void onClick(View v) {
        if (DataUtils.getInstance().getCustomer() == null) {
            Snackbar.make(getView(), "Cần đăng nhập để sử dụng chức năng!", Snackbar.LENGTH_SHORT).show();
        } else {
            mainViewModel.setmCustomer(DataUtils.getInstance().getCustomer());
            switch (v.getId()) {
                case R.id.ivNeo:
                case R.id.layoutNeo:
                    Intent intentNeo = new Intent(getContext(), TestActivity.class);
                    intentNeo.putExtra(Define.TYPE_QUESTION, Define.Question.TYPE_NEO);
                    startActivity(intentNeo);
                    break;
                case R.id.ivRiasec:
                case R.id.layoutRiasec:
                    Intent intentRisec = new Intent(getContext(), TestActivity.class);
                    intentRisec.putExtra(Define.TYPE_QUESTION, Define.Question.TYPE_RIASEC);
                    startActivity(intentRisec);
                    break;
                case R.id.ivPsy:
                case R.id.layoutPsy:
                    Intent intentPsy = new Intent(getContext(), TestActivity.class);
                    intentPsy.putExtra(Define.TYPE_QUESTION, Define.Question.TYPE_PSY_POCHOLIGICAL);
                    startActivity(intentPsy);
                    break;
                default:
                    break;
            }
        }
    }
}
