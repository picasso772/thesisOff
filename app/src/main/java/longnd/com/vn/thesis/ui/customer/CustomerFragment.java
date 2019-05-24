package longnd.com.vn.thesis.ui.customer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import dev147.com.vn.projectpsychological.R;
import longnd.com.vn.thesis.data.base.ObjectResponse;
import longnd.com.vn.thesis.data.model.Customer;
import dev147.com.vn.projectpsychological.databinding.FragmentCustomerBinding;
import longnd.com.vn.thesis.di.OnOpenCustomer;
import longnd.com.vn.thesis.ui.base.BaseFragment;
import longnd.com.vn.thesis.ui.dialog.DialogEditCustomer;
import longnd.com.vn.thesis.ui.dialog.DialogSelectImageSource;
import longnd.com.vn.thesis.utils.DataUtils;
import longnd.com.vn.thesis.utils.Define;
import longnd.com.vn.thesis.utils.Fields;
import longnd.com.vn.thesis.utils.PsyLoading;
import longnd.com.vn.thesis.utils.SharedPrefs;
import longnd.com.vn.thesis.utils.Utils;

public class CustomerFragment extends BaseFragment<CustomerViewModel, FragmentCustomerBinding>
        implements DialogSelectImageSource.OnSelectImage, DialogEditCustomer.OnDoneUpdate {
    private String email;
    private String pass;
    private Handler handler;
    private Customer customer;
    private OnOpenCustomer onOpenCustomer;

    @Override
    protected void initListenerOnClick() {
        binding.btnExit.setOnClickListener(this);
        binding.ivLogo.setOnClickListener(this);
        binding.btnEdit.setOnClickListener(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        customer = DataUtils.getInstance().getCustomer();
        initObserve();
        handler = new Handler();
        mainViewModel.setNumberBack(Fields.DONT_BACK);

        binding.tvNameCustomer.setText(customer.getLastName());

        if (customer.getSchool() != null && !customer.getSchool().isEmpty()) {
            binding.tvSchool.setText(customer.getSchool());
        }
        if (customer.getSpecialized() != null && !customer.getSpecialized().isEmpty()) {
            binding.tvSpecialized.setText(customer.getSpecialized());
        }
        if (customer.getEmail() != null && !customer.getEmail().isEmpty()) {
            binding.tvEmail.setText(customer.getEmail());
        }
        if (customer.getPhone() != null && !customer.getPhone().isEmpty()) {
            binding.tvPhone.setText(customer.getPhone());
        }
        if (customer.getGender() == 0) {
            binding.tvGender.setText("Nữ");
        } else if (customer.getGender() == 1) {
            binding.tvGender.setText("Nam");
        }
        if (customer.getBirthdate() != null && !customer.getBirthdate().isEmpty()) {
            binding.tvBirthDay.setText(customer.getBirthdate());
        }
        if (customer.getImage() != null && !customer.getImage().isEmpty()) {
            String path = Fields.ROOT_FOLDER + File.separator + customer.getImage();
            if (Utils.existsPathImage(path)) {
                Glide.with(getContext())
                        .load(new File(Fields.ROOT_FOLDER + File.separator + customer.getImage()))
                        .into(binding.ivLogo);
            } else {
                Toast.makeText(getContext(), "Đường dẫn hình ảnh có lỗi!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void initObserve() {
        viewModel.getIsUpdateCustomer().observe(this, this::observeUpdateCustomer);
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    private void observeUpdateCustomer(ObjectResponse<Boolean> booleanObjectResponse) {
        if (booleanObjectResponse == null) {
            return;
        }
        switch (booleanObjectResponse.getStatus()) {
            case Define.ResponseStatus.LOADING:
                break;
            case Define.ResponseStatus.SUCCESS:
                viewModel.setIsUpdateCustomer(null);
                viewModel.getIsUpdateCustomer().removeObservers(this);
                if (booleanObjectResponse.getData()) {
                    PsyLoading.getInstance(getContext()).hidden();
                    viewModel.getCustomerByEmail(email, pass);
                    Glide.with(getContext())
                            .load(new File(Fields.ROOT_FOLDER + File.separator + customer.getImage()))
                            .into(binding.ivLogo);
                }
                break;
            case Define.ResponseStatus.ERROR:
                break;
            default:
                break;
        }
    }

    @Override
    public Class<CustomerViewModel> getModelClass() {
        return CustomerViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_customer;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnExit:
                onExitCustomer();
                break;
            case R.id.ivLogo:
                if (!requestPermision()) {
                    showDialogSelect();
                }
                break;
            case R.id.btnEdit:
                DialogEditCustomer editCustomer = new DialogEditCustomer();
                editCustomer.setOnDoneUpdate(this);
                editCustomer.show(getFragmentManager(), DialogEditCustomer.class.getName());
                break;
            default:
                break;
        }
    }

    private void showDialogSelect() {
        DialogSelectImageSource selectImageSource = new DialogSelectImageSource();
        selectImageSource.setOnSelectImage(this);
        selectImageSource.show(getFragmentManager(), DialogSelectImageSource.class.getName());
    }

    private boolean requestPermision() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        } else {
            if (isGranted(Manifest.permission.READ_EXTERNAL_STORAGE) && isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                return false;
            } else {
                requestPermissions(
                        new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        102
                );
                return true;
            }
        }
    }

    private boolean requestPermisionCamera() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        } else {
            if (isGranted(Manifest.permission.CAMERA)) {
                return false;
            } else {
                requestPermissions(
                        new String[]{
                                Manifest.permission.CAMERA
                        },
                        103
                );
                return true;
            }
        }
    }

    private boolean isGranted(String permison) {
        return ActivityCompat.checkSelfPermission(getContext(), permison) == PackageManager.PERMISSION_GRANTED;
    }


    private void onExitCustomer() {
        SharedPrefs.getInstance().putString(Fields.KEY_EMAIL, Fields.DEFAULT_VALUE);
        SharedPrefs.getInstance().putString(Fields.KEY_PASS, Fields.DEFAULT_VALUE);
        DataUtils.getInstance().setCustomer(null);
        onOpenCustomer.openSignInCustomer();
    }

    @Override
    public void onSelectFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, Fields.PICK_IMAGE);
    }

    @Override
    public void onSelectTakePicture() {
        if (!requestPermisionCamera()) {
            openCamera();
        }
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, Fields.TAKE_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (Activity.RESULT_OK == resultCode && requestCode == Fields.PICK_IMAGE) {
            if (!Utils.existsPsyFolder()) {
                return;
            }
            if (data == null) {
                return;
            }
            PsyLoading.getInstance(getContext()).show();
            if (viewModel.saveImageToMemory(getActivity(), data.getData())) {
                customer.setImage(viewModel.pathImage);
                viewModel.updateCustoemr(customer);
            }
        }

        if (Activity.RESULT_OK == resultCode && requestCode == Fields.TAKE_IMAGE) {
            if (!Utils.existsPsyFolder()) {
                return;
            }
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            String filePath = "";
            String path = System.currentTimeMillis() + Fields.FORMAT_IMAGE;
            filePath = Fields.ROOT_FOLDER + "/" + path;
            File file = new File(filePath);
            try {
                FileOutputStream fOut = new FileOutputStream(file);
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut);
                fOut.flush();
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            viewModel.pathImage = filePath;
            customer.setImage(path);
            viewModel.updateCustoemr(customer);
        }
    }

    public void setOnOpenCustomer(OnOpenCustomer onOpenCustomer) {
        this.onOpenCustomer = onOpenCustomer;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 102) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                showDialogSelect();
            }
        }
        if (requestCode == 103) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            }
        }
    }

    @Override
    public void successUpdate() {
        Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
        initData();
    }

    @Override
    public void faildUpdate() {
        Toast.makeText(getContext(), "Có lỗi cập nhật thông tin người dùng", Toast.LENGTH_SHORT).show();
    }
}
