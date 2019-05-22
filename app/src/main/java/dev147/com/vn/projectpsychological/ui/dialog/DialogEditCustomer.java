package dev147.com.vn.projectpsychological.ui.dialog;


import android.app.DatePickerDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import dev147.com.vn.projectpsychological.R;
import dev147.com.vn.projectpsychological.data.base.ObjectResponse;
import dev147.com.vn.projectpsychological.data.model.Customer;
import dev147.com.vn.projectpsychological.databinding.DialogEditBinding;
import dev147.com.vn.projectpsychological.ui.base.BaseDialog;
import dev147.com.vn.projectpsychological.utils.DataUtils;
import dev147.com.vn.projectpsychological.utils.Define;
import dev147.com.vn.projectpsychological.utils.PsyLoading;
import dev147.com.vn.projectpsychological.utils.ToastUtils;
import dev147.com.vn.projectpsychological.utils.Utils;

public class DialogEditCustomer extends BaseDialog<EditCustomerViewModel, DialogEditBinding>
        implements AdapterView.OnItemSelectedListener {
    private Customer customer;
    private DatePickerDialog datePickerDialog;
    private OnDoneUpdate onDoneUpdate;
    private String school;
    private String specialized;
    private String gender;
    private String birthday;
    private int yyyy;
    private int dd;
    private int mm;

    private ArrayAdapter<CharSequence> spin_school;
    private ArrayAdapter<CharSequence> spin_specialized;
    private ArrayAdapter<CharSequence> spin_gender;

    protected void initView(View view) {
    }

    @Override
    protected Class<EditCustomerViewModel> getModelClass() {
        return EditCustomerViewModel.class;
    }

    @Override
    protected void initOnClick() {
        binding.ivClose.setOnClickListener(this);
        binding.btnUpdate.setOnClickListener(this);
        binding.tvBirthday.setOnClickListener(this);

        binding.spinnerSchool.setOnItemSelectedListener(this);
        binding.spinnerSpecialized.setOnItemSelectedListener(this);
        binding.spinnerGender.setOnItemSelectedListener(this);
    }

    @Override
    protected void initData() {
        spin_school = ArrayAdapter.createFromResource(getContext(),
                R.array.school_array, android.R.layout.simple_spinner_item);
        spin_specialized = ArrayAdapter.createFromResource(getContext(),
                R.array.specialized_array, android.R.layout.simple_spinner_item);
        spin_gender = ArrayAdapter.createFromResource(getContext(),
                R.array.gender_array, android.R.layout.simple_spinner_item);

        spin_school.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_specialized.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_gender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.spinnerSchool.setAdapter(spin_school);
        binding.spinnerSpecialized.setAdapter(spin_specialized);
        binding.spinnerGender.setAdapter(spin_gender);

        customer = DataUtils.getInstance().getCustomer();
        if (customer == null) {
            return;
        }
        if (customer.getLastName() != null) {
            binding.editFullName.setText(customer.getLastName());
            binding.editFullName.setSelection(customer.getLastName().length());
        }
        if (customer.getSchool() != null) {
            String sc = customer.getSchool();
            if (sc.contains("Bách Khoa")) {
                binding.spinnerSchool.setSelection(1);
            } else {
                binding.spinnerSchool.setSelection(2);
            }
        }
        if (customer.getSpecialized() != null) {
            String sp = customer.getSpecialized();
            if (sp.contains("Công nghệ")) {
                binding.spinnerSpecialized.setSelection(1);
            } else {
                binding.spinnerSpecialized.setSelection(2);
            }
        }
        if (customer.getPhone() != null) {
            binding.editPhone.setText(customer.getPhone());
        }
        if (customer.getGender() == 0) {
            binding.spinnerGender.setSelection(1);
        }
        if (customer.getBirthdate() != null) {
            birthday = customer.getBirthdate();
            binding.tvBirthday.setText(birthday);
            yyyy = viewModel.getYear(birthday);
            dd = viewModel.getDay(birthday);
            mm = viewModel.getMonth(birthday);
        }
        viewModel.getIsUpdateCustomer().observe(getViewLifecycleOwner(), this::observeUpdateCustomer);
    }

    private void observeUpdateCustomer(ObjectResponse<Boolean> booleanObjectResponse) {
        if (booleanObjectResponse == null) {
            return;
        }
        switch (booleanObjectResponse.getStatus()) {
            case Define.ResponseStatus.LOADING:

                break;
            case Define.ResponseStatus.SUCCESS:
                viewModel.getIsUpdateCustomer().removeObservers(getViewLifecycleOwner());
                viewModel.setIsUpdateCustomer(null);
                PsyLoading.getInstance(getContext()).hidden();
                DataUtils.getInstance().setCustomer(customer);
                // TODO: update ui
                onDoneUpdate.successUpdate();
                dismiss();
                break;
            case Define.ResponseStatus.ERROR:
                viewModel.getIsUpdateCustomer().removeObservers(getViewLifecycleOwner());
                viewModel.setIsUpdateCustomer(null);
                PsyLoading.getInstance(getContext()).hidden();
                // error
                onDoneUpdate.faildUpdate();
                break;
            default:
                break;
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_edit;
    }

    @Override
    public void onClick(View v) {
        avoidDulicateClick(v);
        Utils.hideKeyboardFrom(getContext(), v);
        switch (v.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.btnUpdate:
                String fullname = binding.editFullName.getText().toString().trim();
                String phone = binding.editPhone.getText().toString().trim();
                if (school.contains("Chọn trường...")) {
                    ToastUtils.showToastNotification(getContext(), "Chọn thông tin trường");
                    return;
                }
                if (specialized.contains("Chọn chuyên ngành")) {
                    ToastUtils.showToastNotification(getContext(), "Chọn thông tin chuyên ngành");
                    return;
                }
                if (phone.isEmpty()) {
                    ToastUtils.showToastNotification(getContext(), "Thiếu thông tin số điện thoại");
                    return;
                }
                if (birthday == null || birthday.isEmpty()) {
                    ToastUtils.showToastNotification(getContext(), "Thiếu thông tin ngày sinh");
                    return;
                }
                PsyLoading.getInstance(getContext()).show();
                if (viewModel.aValidData(fullname, school, specialized, phone, gender, birthday)) {
                    customer.setLastName(fullname);
                    customer.setSchool(school);
                    customer.setSpecialized(specialized);
                    customer.setPhone(phone);
                    if (gender.equals("Nam")) {
                        customer.setGender(1);
                    } else {
                        customer.setGender(0);
                    }
                    customer.setBirthdate(birthday);
                }
                viewModel.updateCustoemr(customer);
                break;
            case R.id.tvBirthday:
                PsyLoading.getInstance(getContext()).show();
                openDialogDatePicker();
                break;
        }
    }

    private void openDialogDatePicker() {
        if (datePickerDialog == null) {
            datePickerDialog = new DatePickerDialog(getActivity(), (view, year, month, dayOfMonth) -> {
                birthday = "";
                if (dayOfMonth < 10) {
                    birthday = birthday.concat("0" + dayOfMonth + "/");
                } else {
                    birthday = birthday.concat(String.valueOf(dayOfMonth + "/"));
                }
                month++;
                if (month < 10) {
                    birthday = birthday.concat("0" + month + "/");
                } else {
                    birthday = birthday.concat(String.valueOf(month) + "/");
                }
                birthday = birthday.concat(String.valueOf(year));
                binding.tvBirthday.setText(birthday);
            }, yyyy, mm - 1, dd);
        }
        datePickerDialog.show();
        PsyLoading.getInstance(getContext()).hidden();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinnerSchool:
                school = spin_school.getItem(position).toString();
                break;
            case R.id.spinnerSpecialized:
                specialized = spin_specialized.getItem(position).toString();
                break;
            case R.id.spinnerGender:
                gender = spin_gender.getItem(position).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void setOnDoneUpdate(OnDoneUpdate onDoneUpdate) {
        this.onDoneUpdate = onDoneUpdate;
    }

    public interface OnDoneUpdate {
        void successUpdate();

        void faildUpdate();
    }

}
