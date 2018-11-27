package com.mujirenben.android.mine.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.arounter.HrzRouter;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.SpUtil;
import com.mujirenben.android.common.util.StatusBarUtil;
import com.mujirenben.android.mine.di.component.DaggerOrderComponent;
import com.mujirenben.android.mine.di.module.OrderModule;
import com.mujirenben.android.mine.mvp.contract.OrderContract;
import com.mujirenben.android.mine.mvp.model.bean.OrderBean;
import com.mujirenben.android.mine.mvp.model.bean.OrderDetail;
import com.mujirenben.android.mine.mvp.model.bean.OrderInfo;
import com.mujirenben.android.mine.mvp.model.bean.SelfRunOrderDetail;
import com.mujirenben.android.mine.mvp.presenter.OrderPresenter;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

/**
 * 自营订单页面
 */
@Route(path = ARouterPaths.SELF_RUN_ORDER_DETAILS)
public class SelfRunOrderDetailsActivity extends BaseActivity<OrderPresenter> implements OrderContract.View, View.OnClickListener {

    @BindView(R2.id.tv_back)
    ImageView backBtn;
    @BindView(R2.id.tv_titlebar)
    TextView titleView;
    @BindView(R2.id.mine_order_ask_question_ll)
    View askQuestionLayout;
    @BindView(R2.id.ll_sub_orders)
    LinearLayout ll_sub_orders;
    @BindView(R2.id.ll_order_ship)
    LinearLayout ll_order_ship; //收货地址
    @BindView(R2.id.mine_order_details_charge_total_tv)
    TextView chargeTotolView;
    @BindView(R2.id.mine_order_details_order_searial_tv)
    TextView walletCodeView;
    @BindView(R2.id.mine_order_details_order_book_time_tv)
    TextView bookTimeView;
    @BindView(R2.id.mine_order_details_charge_pay_tv)
    TextView actualAmountView;
    @BindView(R2.id.mine_order_details_owner_address_tv)
    TextView addressView;
    @BindView(R2.id.mine_order_details_owner_name_tv)
    TextView userNameView;
    @BindView(R2.id.mine_order_details_owner_phone_tv)
    TextView phoneView;
    @BindView(R2.id.mine_order_details_payer_tv)
    TextView consumeAccountView; //消费账户
    @BindView(R2.id.mine_order_details_pay_method_tv)
    TextView payWayView;

    TextView mStatusMsg;
    ImageView mStatusIcon;

    private String orderId="1194026403309802";

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOrderComponent
                .builder()
                .appComponent(appComponent)
                .orderModule(new OrderModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
//        StatusBarUtil.setStatusBarWhite(this);
        if (SpUtil.getIsMIUI(this)){
            StatusBarUtil.getStatusBarHelper(this,R.color.white).into(this);
        }else {
            StatusBarUtil.setStatusBarWhite(this);
        }
        return R.layout.mine_order_details_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        orderId=getIntent().getStringExtra("orderId");
        showOrderDetail();
        showAwardOnly();
        initViews();
        setListener();
        RetrofitUrlManager.getInstance().putDomain("server_order_detail", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
        if (mPresenter == null){
            ArmsUtils.makeText(this,"请重新进入页面");
            finish();
        }
        mPresenter.getSelfRunOrderDetailById(orderId);
    }

    /**
     *  显示收货地址
     */
    private void showOrderDetail() {
        findViewById(R.id.ll_pay_way).setVisibility(View.VISIBLE);
        findViewById(R.id.ll_pay_time).setVisibility(View.VISIBLE);
        findViewById(R.id.ll_payer).setVisibility(View.VISIBLE);
    }

    /**
     * 只显示消费金额栏
     */
    private void showAwardOnly(){
        findViewById(R.id.ll_award).setVisibility(View.VISIBLE);
        findViewById(R.id.ll_discounts).setVisibility(View.GONE);
        findViewById(R.id.ll_reward_time).setVisibility(View.GONE);
    }

    private void initViews(){
        mStatusMsg = findViewById(R.id.tv_status_msg);
        mStatusIcon = findViewById(R.id.iv_status_icon);
        titleView.setText(R.string.mine_order_details_page_title);
    }

    private void setListener(){
        backBtn.setOnClickListener(this);
        askQuestionLayout.setOnClickListener(this);
    }

    @Override
    public Activity getActivity() {
        return null;
    }

    @Override
    public void showOrderList(OrderInfo orderInfo) {

    }

    @Override
    public void showOrderDetails(OrderDetail orderItem) {

    }

    @Override
    public void showOrders(List<OrderBean.DataBean> orderBean) {

    }

    @Override
    public void showRequestError(String errorMsg) {
        ArmsUtils.makeText(this,errorMsg);
    }

    @Override
    public void requestInitDataEmpty() {

    }

    @Override
    public void requestMoreDataEmpty() {

    }

    @Override
    public void showOrderDetailEmpty() {

    }

    @Override
    public void getOrderListByUserIdFail() {

    }

    @Override
    public void getOrdersByType() {

    }

    /**
     *  是否显示收货地址
     *
     */
    @Override
    public void showAddress(SelfRunOrderDetail detail) {
        userNameView.setText(detail.getUserName());
        phoneView.setText(detail.getData().getPhone());
        addressView.setText(detail.getOrderAddress());
        ll_order_ship.setVisibility(View.VISIBLE);
    }

    /**
     * 显示消费账户
     * @param detail
     */
    @Override
    public void showConsumeAccount(SelfRunOrderDetail detail) {
//        ((TextView)findViewById(R.id.mine_order_details_payer_tv)).setText(detail.getData().getPayer());
//        ((TextView)findViewById(R.id.mine_order_details_pay_method_tv)).setText(detail.getData().getPayWay() == 0 ? "微信" : "支付宝");
        consumeAccountView.setText(detail.getData().getPayer());
        payWayView.setText(detail.getData().getPayWay() == 0 ? "微信" : "支付宝");
    }

    @Override
    public void hideAddress() {
        ll_order_ship.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_back) {
            finish();
        } else if (id == R.id.mine_order_ask_question_ll) {
            HrzRouter.getsInstance(getActivity()).navigation(Consts.getHrzRouterUrl(Consts.HRZ_FAQ_URL,"常见问题"));
        }
    }

    //请求成功回调的方法
    @Override
    public void onSelfRunOrderDetailLoaded(SelfRunOrderDetail detail) {
        chargeTotolView.setText(detail.getAmount());
        actualAmountView.setText(detail.getActualAmount());
        walletCodeView.setText(detail.getWalletCode());
        bookTimeView.setText(formatTimestamp(detail.getData().getCreateTime() * 1000));
        if (detail.getData().getPayTime() != 0) {
            ((TextView)findViewById(R.id.mine_order_details_pay_time_tv)).setText(formatTimestamp(detail.getData().getPayTime() * 1000));
            ((TextView)findViewById(R.id.mine_order_details_tran_time_tv)).setText(formatTimestamp(detail.getData().getPayTime() * 1000));
        } else {
            findViewById(R.id.ll_pay_time).setVisibility(View.GONE);
            findViewById(R.id.ll_transaction_time).setVisibility(View.GONE);
        }

        showOrderStatus(detail);
    }

    private void showOrderStatus(SelfRunOrderDetail orderDetail) {
        String msg = "支付失败";
        int iconId = R.drawable.order_status_pay_success;
        switch (orderDetail.getData().getStatus()) {
            case 0:
                msg = "支付中";
                iconId = R.drawable.order_status_ing;
                break;
            case 1:
                msg = "已支付";
                iconId = R.drawable.order_status_pay_success;
                break;
            case 2:
                msg = "支付失败";
                iconId = R.drawable.order_status_close;
                break;
        }

        mStatusMsg.setText(msg);
        mStatusIcon.setImageResource(iconId);
    }

    private String formatTimestamp(long timestamp) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(timestamp));
    }

    @Override
    public void onSelfRunOrderDetailFailed(Throwable error) {
        ArmsUtils.makeText(this,error.getMessage());
    }
}
