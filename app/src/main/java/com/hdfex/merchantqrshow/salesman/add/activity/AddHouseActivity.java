package com.hdfex.merchantqrshow.salesman.add.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.resource.Apartment;
import com.hdfex.merchantqrshow.bean.salesman.resource.ApartmentResponse;
import com.hdfex.merchantqrshow.bean.salesman.resource.CreateHouseResult;
import com.hdfex.merchantqrshow.bean.salesman.resource.ItemHouse;
import com.hdfex.merchantqrshow.bean.salesman.resource.ResourceFlag;
import com.hdfex.merchantqrshow.mvp.presenter.AddHousePresenter;
import com.hdfex.merchantqrshow.mvp.view.AddHouseView;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.salesman.add.fragment.ApartmentFragment;
import com.hdfex.merchantqrshow.utils.EventRxBus;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.utils.Utils;
import com.hdfex.merchantqrshow.widget.DeleteEditText;
import com.hdfex.merchantqrshow.widget.picker.AddressInitTask;
import com.hdfex.merchantqrshow.widget.picker.AddressPicker;
import com.hdfex.merchantqrshow.widget.picker.DurationPicker;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * 添加房屋界面
 * Created by harrishuang on 2017/2/8.
 */

public class AddHouseActivity extends BaseActivity implements View.OnClickListener, AddHouseView {

    public ItemHouse itemHouse;
    public ItemHouse preAddress;
    private ImageView img_back;
    private TextView txt_left_name;
    private TextView tb_tv_titile;
    private ImageView iv_setting;
    private TextView tv_home;
    private LinearLayout layout_toolbar;
    private EditText edt_house_address;
    private DeleteEditText edt_court_name;
    private DeleteEditText edt_building_no;
    private DeleteEditText edt_house_floor;
    private DeleteEditText edt_house_no;
    private DeleteEditText edt_room_name;
    private DeleteEditText edt_house_unit;
    private EditText et_lease_mode;
    private TextView btn_submit;
    private Map<TextView, String> previewMap;
    private Map<TextView, String> centraliseMap;
    private TextView text_preview;
    private AddHousePresenter persenter;
    private User user;
    private String addressProvince = "";
    private String addressCity = "";
    private String addressArea = "";
    private LinearLayout layout_dispers;
    private EditText edt_centralise_building_name;
    private DeleteEditText edt_centralise_house_floor;
    private DeleteEditText edt_centralise_house_no;
    private LinearLayout layout_centralise;
    private EditText edt_house_type;
    private FrameLayout layout_house_type;
    private TextView text_centrailse_preview;
    private EditText edt_centralise_address;
    private String currentHouseType = IntentFlag.HOUSE_TYPE_DISPERSE;
    private Apartment apartment;
    private TextView txt_addhouse_alert;

    /**
     * @param context
     * @param itemHouse
     */
    public static void start(Context context, ItemHouse itemHouse) {
        Intent intent = new Intent(context, AddHouseActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentFlag.INTENT_NAME, itemHouse);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * @param context
     * @param houseType
     */
    public static void start(Context context, String houseType) {
        Intent intent = new Intent(context, AddHouseActivity.class);
        intent.putExtra(IntentFlag.HOUSE_TYPE, houseType);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addhouse);
        setTheme(R.style.ActionSheetStyle);
        initView();
        user = UserManager.getUser(this);
        persenter = new AddHousePresenter();
        persenter.attachView(this);
        previewMap = new HashMap<>();
        centraliseMap = new HashMap<>();
        Subscriber subscriber = new Subscriber<TextViewTextChangeEvent>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                previewMap.put(textViewTextChangeEvent.view(), textViewTextChangeEvent.text() + "");
                StringBuffer buffter = new StringBuffer();
                buffter.append(previewMap.get(edt_house_address));
                buffter.append(previewMap.get(edt_court_name));
                if (!TextUtils.isEmpty(previewMap.get(edt_court_name))) {
                    buffter.append("<font color='#000000'>小区</font>");
                }
                buffter.append(previewMap.get(edt_building_no));
                if (!TextUtils.isEmpty(previewMap.get(edt_building_no))) {
                    buffter.append("<font color='#000000'>号楼</font>");
                }
                buffter.append(previewMap.get(edt_house_unit));
                if (!TextUtils.isEmpty(previewMap.get(edt_house_unit))) {
                    buffter.append("<font color='#000000'>单元</font>");
                }
                buffter.append(previewMap.get(edt_house_floor));
                if (!TextUtils.isEmpty(previewMap.get(edt_house_floor))) {
                    buffter.append("<font color='#000000'>层</font>");
                }
                buffter.append(previewMap.get(edt_house_no));
                if (!TextUtils.isEmpty(previewMap.get(edt_house_no))) {
                    buffter.append("<font color='#000000'>号</font>");
                }
                buffter.append(previewMap.get(edt_room_name));
                text_preview.setText(Html.fromHtml(buffter.toString()));
            }
        };
        RxTextView.textChangeEvents(edt_house_address).subscribe(subscriber);
        RxTextView.textChangeEvents(edt_court_name).subscribe(subscriber);
        RxTextView.textChangeEvents(edt_building_no).subscribe(subscriber);
        RxTextView.textChangeEvents(edt_house_unit).subscribe(subscriber);
        RxTextView.textChangeEvents(edt_house_floor).subscribe(subscriber);
        RxTextView.textChangeEvents(edt_house_no).subscribe(subscriber);
        RxTextView.textChangeEvents(edt_room_name).subscribe(subscriber);
        RxTextView.textChangeEvents(et_lease_mode).subscribe(subscriber);
        Subscriber centraliseSubscriber = new Subscriber<TextViewTextChangeEvent>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                centraliseMap.put(textViewTextChangeEvent.view(), textViewTextChangeEvent.text() + "");
                StringBuffer buffter = new StringBuffer();
                buffter.append(centraliseMap.get(edt_centralise_address));
                buffter.append(centraliseMap.get(edt_centralise_building_name));
                if (!TextUtils.isEmpty(centraliseMap.get(edt_centralise_building_name))) {
                    buffter.append("<font color='#000000'>小区</font>");
                }
                buffter.append(centraliseMap.get(edt_centralise_house_floor));
                if (!TextUtils.isEmpty(centraliseMap.get(edt_centralise_house_floor))) {
                    buffter.append("<font color='#000000'>层</font>");
                }
                buffter.append(centraliseMap.get(edt_centralise_house_no));
                if (!TextUtils.isEmpty(centraliseMap.get(edt_centralise_house_no))) {
                    buffter.append("<font color='#000000'>号</font>");
                }
                text_centrailse_preview.setText(Html.fromHtml(buffter.toString()));
            }
        };
        RxTextView.textChangeEvents(edt_centralise_building_name).subscribe(centraliseSubscriber);
        RxTextView.textChangeEvents(edt_centralise_address).subscribe(centraliseSubscriber);
        RxTextView.textChangeEvents(edt_centralise_house_floor).subscribe(centraliseSubscriber);
        RxTextView.textChangeEvents(edt_centralise_house_no).subscribe(centraliseSubscriber);
        Observable<Object> register = EventRxBus.getInstance().register(IntentFlag.HOUSE_APARTMENT_EVENT);
        register.subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                apartment = (Apartment) o;
                edt_centralise_building_name.setText(apartment.getCourtName() + "");
                edt_centralise_address.setText(apartment.getApartmentAddress() + "");
            }
        });
        init();
    }

    public void init() {
        Bundle bundle = getIntent().getExtras();
        /**
         * 这个判断出来是修改
         */
        if (bundle != null && bundle.getSerializable(IntentFlag.INTENT_NAME) != null) {
            itemHouse = (ItemHouse) bundle.getSerializable(IntentFlag.INTENT_NAME);
            preAddress = itemHouse;
            addressProvince = itemHouse.getAddrProCode();
            addressCity = itemHouse.getAddrCountyCode();
            addressArea = itemHouse.getAddrAreaCode();
            if (!TextUtils.isEmpty(addressProvince) && !TextUtils.isEmpty(addressCity) && !TextUtils.isEmpty(addressArea)) {
                edt_house_address.setText(itemHouse.getAddrProvince() + "" + itemHouse.getAddrCounty() + itemHouse.getAddrArea());
            }
            currentHouseType = itemHouse.getHouseType();
            tv_home.setVisibility(View.VISIBLE);
            tv_home.setTextSize(Utils.sp2px(this, 15));
            tv_home.setText("···");
            edt_court_name.setText(itemHouse.getCourtName());
            edt_building_no.setText(itemHouse.getBuildingNo());
            edt_house_unit.setText(itemHouse.getUnitNo());
            edt_house_floor.setText(itemHouse.getFloorNo());
            edt_house_no.setText(itemHouse.getHouseNo());
            edt_room_name.setText(itemHouse.getRoomName());
            et_lease_mode.setTag(itemHouse.getRentType());
            if (ResourceFlag.RENTTYPE_RENTS_OTHER.equals(itemHouse.getRentType())) {
                et_lease_mode.setText("合租");
            } else if (ResourceFlag.RENTTYPE_RENTS_BOTH.equals(itemHouse.getRentType())) {
                et_lease_mode.setText("整租");
            }
            if (itemHouse != null) {
                apartment = new Apartment();
                apartment.setAddrProCode(itemHouse.getAddrProCode());
                apartment.setAddrCountyCode(itemHouse.getAddrCountyCode());
                apartment.setAddrAreaCode(itemHouse.getAddrAreaCode());
                apartment.setCourtName(itemHouse.getCourtName());
                apartment.setApartmentAddress(itemHouse.getApartmentAddress());
                apartment.setBuildingNo(itemHouse.getBuildingNo());
                apartment.setRentType(itemHouse.getRentType());
                apartment.setApartmentId(itemHouse.getApartmentId());
                edt_centralise_building_name.setText(itemHouse.getCourtName());
                edt_centralise_address.setText(itemHouse.getApartmentAddress());
                edt_centralise_house_floor.setText(itemHouse.getFloorNo());
                edt_centralise_house_no.setText(itemHouse.getHouseNo());

            }
        } else {
            /**
             * 这个判断出来是添加
             */
            ItemHouse address = UserManager.getAddress(this);
            if (address != null) {
                preAddress = address;
                addressProvince = preAddress.getAddrProCode();
                addressCity = preAddress.getAddrCountyCode();
                addressArea = preAddress.getAddrAreaCode();
                if (!TextUtils.isEmpty(addressProvince) && !TextUtils.isEmpty(addressCity) && !TextUtils.isEmpty(addressArea)) {
                    edt_house_address.setText(preAddress.getAddrProvince() + "" + preAddress.getAddrCounty() + preAddress.getAddrArea());
                }
            } else {
                preAddress = new ItemHouse();
            }
            Apartment apartment = UserManager.getApartment(this);
            if (apartment != null) {
                EventRxBus.getInstance().post(IntentFlag.HOUSE_APARTMENT_EVENT, apartment);
            }
            setDefoultHouseType();
        }
        edt_court_name.clearDrawable();
        edt_building_no.clearDrawable();
        edt_house_unit.clearDrawable();
        edt_house_floor.clearDrawable();
        edt_house_no.clearDrawable();
        edt_room_name.clearDrawable();
        setCurrentHouseType(currentHouseType);
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        txt_left_name = (TextView) findViewById(R.id.txt_left_name);
        tb_tv_titile = (TextView) findViewById(R.id.tb_tv_titile);
        iv_setting = (ImageView) findViewById(R.id.iv_setting);
        tv_home = (TextView) findViewById(R.id.tv_home);
        layout_toolbar = (LinearLayout) findViewById(R.id.layout_toolbar);
        edt_house_address = (EditText) findViewById(R.id.edt_house_address);
        edt_court_name = (DeleteEditText) findViewById(R.id.edt_court_name);
        edt_building_no = (DeleteEditText) findViewById(R.id.edt_building_no);
        edt_house_floor = (DeleteEditText) findViewById(R.id.edt_house_floor);
        txt_addhouse_alert = (TextView) findViewById(R.id.txt_addhouse_alert);
        edt_house_no = (DeleteEditText) findViewById(R.id.edt_house_no);
        edt_room_name = (DeleteEditText) findViewById(R.id.edt_room_name);
        et_lease_mode = (EditText) findViewById(R.id.et_lease_mode);
        et_lease_mode.setTag("");
        text_preview = (TextView) findViewById(R.id.text_preview);
        edt_house_unit = (DeleteEditText) findViewById(R.id.edt_house_unit);
        img_back.setOnClickListener(this);
        et_lease_mode.setOnClickListener(this);
        edt_house_address.setOnClickListener(this);
        tv_home.setOnClickListener(this);
        layout_dispers = (LinearLayout) findViewById(R.id.layout_dispers);
        layout_dispers.setOnClickListener(this);
        edt_centralise_building_name = (EditText) findViewById(R.id.edt_centralise_building_name);
        edt_centralise_building_name.setOnClickListener(this);
        edt_centralise_house_floor = (DeleteEditText) findViewById(R.id.edt_centralise_house_floor);
        edt_centralise_house_floor.setOnClickListener(this);
        edt_centralise_house_no = (DeleteEditText) findViewById(R.id.edt_centralise_house_no);
        edt_centralise_house_no.setOnClickListener(this);
        layout_centralise = (LinearLayout) findViewById(R.id.layout_centralise);
        layout_centralise.setOnClickListener(this);
        edt_house_type = (EditText) findViewById(R.id.edt_house_type);
        edt_house_type.setOnClickListener(this);
        layout_house_type = (FrameLayout) findViewById(R.id.layout_house_type);
        layout_house_type.setOnClickListener(this);
        text_centrailse_preview = (TextView) findViewById(R.id.text_centrailse_preview);
        text_centrailse_preview.setOnClickListener(this);
        edt_centralise_address = (EditText) findViewById(R.id.edt_centralise_address);
        edt_centralise_address.setOnClickListener(this);
        tb_tv_titile.setText("填写详细房间信息");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                break;
            case R.id.et_lease_mode:
                persenter.showLeaseMode(this, getSupportFragmentManager());
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.edt_house_address:
                onAddrProvince();
                break;
            case R.id.tv_home:
                persenter.showDeleteAlert(this, getSupportFragmentManager());
                break;
            case R.id.edt_house_type:
                persenter.showActionSheet(this, getSupportFragmentManager());
                break;
            case R.id.edt_centralise_building_name:
                persenter.selectCentraliseHouse(this, user, 0);
                break;
        }
    }

    private void onAddrProvince() {
        new AddressInitTask(this, new AddressPicker.OnAddressPickListener() {
            @Override
            public void onAddressPicked(String province, String city, String county) {

                if (province.equals(city)) {
                    edt_house_address.setText(province + " " + county);
                } else {
                    edt_house_address.setText(province + " " + city + " " + county);
                }
                preAddress.setAddrProvince(province);
                preAddress.setAddrCounty(city);
                preAddress.setAddrArea(county);
//                createModel.setAddrProvince(province);
//                createModel.setAddrCounty(city);
//                createModel.setAddrArea(county);
//                et_live_addrProvince.clearDrawable();
            }

            @Override
            public void onAddressPickedCode(String provinceCode, String cityCode, String countyCode) {
                addressProvince = provinceCode;
                addressCity = cityCode;
                addressArea = countyCode;
                preAddress.setAddrProCode(provinceCode);
                preAddress.setAddrCountyCode(cityCode);
                preAddress.setAddrAreaCode(countyCode);
            }
        }).execute("北京市", "北京市", "东城区");
    }

    /**
     * 选择租住模式
     */
    private void showLeaseMode() {
        List<String> leaseList = new ArrayList<>();
        leaseList.add("合租");
        leaseList.add("整租");
        DurationPicker picker = new DurationPicker(this, leaseList, new DurationPicker.OnDurationPickerSelectedListener() {
            @Override
            public void selected(int index, String item) {
                et_lease_mode.setTag(index + 1);
                et_lease_mode.setText(item);
            }
        });
        picker.show();
    }

    /**
     * 提交数据
     *
     * @param view
     */
    public void submit(View view) {

        Map<String, String> param = new HashMap<>();
        param.put("token", user.getToken());
        param.put("id", user.getId());
        param.put("bussinessId", user.getBussinessId());

        if (currentHouseType.equals(IntentFlag.HOUSE_TYPE_DISPERSE)) {
            String address = edt_house_address.getText().toString().trim();
            if (TextUtils.isEmpty(address)) {
                ToastUtils.d(this, "选择省/市/区", Toast.LENGTH_SHORT).show();
                return;
            }
            String courtName = edt_court_name.getText().toString().trim();
//        if (TextUtils.isEmpty(courtName)) {
//            ToastUtils.d(this, "请输入小区名称", Toast.LENGTH_SHORT).show();
//            return;
//        }
            String unit = edt_house_unit.getText().toString().trim();
//        if (TextUtils.isEmpty(unit)) {
//            ToastUtils.d(this, "请输入所在单元", Toast.LENGTH_SHORT).show();
//            return;
//        }
            String buildingNo = edt_building_no.getText().toString().trim();
//        if (TextUtils.isEmpty(buildingNo)) {
//            ToastUtils.d(this, "请输入楼号", Toast.LENGTH_SHORT).show();
//            return;
//        }
            String floor = edt_house_floor.getText().toString().trim();
//        if (TextUtils.isEmpty(floor)) {
//            ToastUtils.d(this, "请输入所在楼层", Toast.LENGTH_SHORT).show();
//            return;
//        }
            String houseNo = edt_house_no.getText().toString().trim();
//        if (TextUtils.isEmpty(houseNo)) {
//            ToastUtils.d(this, "请输入门房间号", Toast.LENGTH_SHORT).show();
//            return;
//        }
            String roomName = edt_room_name.getText().toString().trim();
//        if (TextUtils.isEmpty(roomName)) {
//            ToastUtils.d(this, "请输入租住房屋名称", Toast.LENGTH_SHORT).show();
//            return;
//        }
            String mode = et_lease_mode.getText().toString().trim();
//        if (TextUtils.isEmpty(mode)) {
//            ToastUtils.d(this, "整租/合租", Toast.LENGTH_SHORT).show();
//            return;
//        }
            UserManager.saveAddress(this, preAddress);
            param.put("addrProCode", addressProvince);
            param.put("addrCountyCode", addressCity);
            param.put("addrAreaCode", addressArea);
            param.put("courtName", courtName);
            param.put("rentType", et_lease_mode.getTag().toString());
            param.put("buildingNo", buildingNo);
            param.put("unitNo", unit);
            param.put("floorNo", floor);
            param.put("houseNo", houseNo);
            param.put("roomName", roomName);
        } else {
            String floor = edt_centralise_house_floor.getText().toString().trim();
            if (TextUtils.isEmpty(floor)) {
                ToastUtils.d(this, "请填写楼层", Toast.LENGTH_SHORT).show();
                return;
            }
            String no = edt_centralise_house_no.getText().toString().trim();
            if (TextUtils.isEmpty(no)) {
                ToastUtils.d(this, "请填写房号", Toast.LENGTH_SHORT).show();
                return;
            }
            param.put("addrProCode", apartment.getAddrProCode());
            param.put("addrCountyCode", apartment.getAddrCountyCode());
            param.put("addrAreaCode", apartment.getAddrAreaCode());
            param.put("courtName", apartment.getCourtName());
            param.put("rentType", apartment.getRentType());
            param.put("buildingNo", apartment.getBuildingNo());
            param.put("floorNo", floor);
            param.put("houseNo", no);
            param.put("apartmentAddress", apartment.getApartmentAddress());
            param.put("apartmentId", apartment.getApartmentId());
        }
        if (itemHouse == null) {
            UserManager.saveApartment(AddHouseActivity.this, apartment);
            persenter.addHouse(param);
        } else {
            param.put("commodityId", itemHouse.getCommodityId());
            persenter.editHouse(param);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        persenter.detachView();
        EventRxBus.getInstance().unregister(IntentFlag.HOUSE_APARTMENT_EVENT);
    }

    @Override
    public void returnAddCreateHouse(CreateHouseResult result) {
        if (result != null) {
            ItemHouse itemHouse = new ItemHouse();
            if (currentHouseType.equals(IntentFlag.HOUSE_TYPE_DISPERSE)) {
                itemHouse.setDetailAddress(text_preview.getText().toString());
            } else {
                itemHouse.setDetailAddress(text_centrailse_preview.getText().toString());
            }

            if (this.itemHouse == null) {
                itemHouse.setCommodityId(result.getCommodityId());
                EventRxBus.getInstance().post(IntentFlag.INTENT_SELECT_HOUSE, itemHouse);
                EventRxBus.getInstance().post(IntentFlag.INTENT_HOUSE, itemHouse);
            }
        }
        this.finish();
    }

    @Override
    public void returnDeleteHouse(CreateHouseResult result) {
        ToastUtils.d(this, "删除成功！").show();
        this.finish();
    }

    @Override
    public void deleteHouse() {
        persenter.deleteHouse(user, itemHouse.getCommodityId());
    }

    @Override
    public void houseType(String s, int index) {
        if (index == 0) {
            setCurrentHouseType(IntentFlag.HOUSE_TYPE_CONCENTRATE);
        } else if (index == 1) {
            setCurrentHouseType(IntentFlag.HOUSE_TYPE_DISPERSE);
        }
        edt_house_type.setText(s);
    }

    @Override
    public void returnApartment(List<Apartment> result, int type) {

        /**
         * 添加房屋，只有一个公寓的时候就直接显示
         */
        if (result != null && result.size() == 1) {
            Apartment apartment = result.get(0);
            EventRxBus.getInstance().post(IntentFlag.HOUSE_APARTMENT_EVENT, apartment);
            return;
        }

        if (1 == type) {
            return;
        }
        ApartmentFragment fragment = ApartmentFragment.getInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ApartmentResponse response = new ApartmentResponse();
        response.setResult(result);
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentFlag.INTENT_HOUSE, response);
        fragment.setArguments(bundle);
        transaction.setCustomAnimations(
                R.anim.fragment_up_enter,
                R.anim.fragment_down_outer,
                R.anim.fragment_up_enter,
                R.anim.fragment_down_outer);
        transaction.replace(R.id.layout_fragment_content, fragment);
        transaction.addToBackStack("apartmentFragment");
        transaction.commit();
    }

    @Override
    public void returnShowLeaseMode(int index, String s) {
        et_lease_mode.setTag(index + 1);
        et_lease_mode.setText(s);
    }

    /**
     * 设置当前房子类型
     * @param currentHouseType
     */
    private void setCurrentHouseType(String currentHouseType) {
        this.currentHouseType = currentHouseType;
        if (currentHouseType.equals(IntentFlag.HOUSE_TYPE_CONCENTRATE)) {
            layout_centralise.setVisibility(View.VISIBLE);
            layout_dispers.setVisibility(View.GONE);
        } else if (currentHouseType.equals(IntentFlag.HOUSE_TYPE_DISPERSE)) {
            layout_centralise.setVisibility(View.GONE);
            layout_dispers.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置默认详情
     */
    private void setDefoultHouseType() {
        Intent intent = getIntent();
        if (!TextUtils.isEmpty(intent.getStringExtra(IntentFlag.HOUSE_TYPE))) {
            currentHouseType = intent.getStringExtra(IntentFlag.HOUSE_TYPE);
            if (IntentFlag.HOUSE_TYPE_CONCENTRATE.equals(currentHouseType)) {
                txt_addhouse_alert.setVisibility(View.GONE);
                persenter.selectCentraliseHouse(this, user, 1);
            }
        }
    }
}
