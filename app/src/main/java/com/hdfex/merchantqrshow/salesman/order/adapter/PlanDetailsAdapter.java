package com.hdfex.merchantqrshow.salesman.order.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.salesman.NameValue;
import com.hdfex.merchantqrshow.bean.salesman.plan.PlanDetail;
import com.hdfex.merchantqrshow.bean.salesman.plan.RepayDetail;
import com.hdfex.merchantqrshow.bean.salesman.plan.RepayPlan;
import com.hdfex.merchantqrshow.net.IntentFlag;
import com.hdfex.merchantqrshow.utils.DialogUtils.Effectstype;
import com.hdfex.merchantqrshow.utils.DialogUtils.effects.BaseEffects;
import com.hdfex.merchantqrshow.utils.log.LogUtil;
import com.hdfex.merchantqrshow.widget.HDAlertDialog;
import com.hdfex.merchantqrshow.widget.ListDialog;
import com.hdfex.merchantqrshow.widget.ListV2Dialog;

import java.util.ArrayList;
import java.util.List;

/**
 * author Created by harrishuang on 2017/5/25.
 * email : huangjinping@hdfex.com
 */

public class PlanDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int HEADER = 111;
    private final int ITEM = 222;
    private final int FOOTER = 333;
    private List<RepayPlan> dataList;
    private PlanDetail planDetail;

    public PlanDetailsAdapter(PlanDetail planDetail) {
        this.planDetail = planDetail;
        dataList = new ArrayList<>();
        dataList.clear();
        if (planDetail.getRepayPlanList() != null) {
            dataList.addAll(planDetail.getRepayPlanList());
        }
    }


    public void setPlanDetail(PlanDetail planDetail) {
        this.planDetail = planDetail;
        dataList.clear();
        if (planDetail.getRepayPlanList() != null) {
            dataList.addAll(planDetail.getRepayPlanList());
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case HEADER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_plan_header, null);
                view.setLayoutParams(lp);
                holder = new ViewHeaderHolder(view);

                break;
            case ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_plan_item, null);
                view.setLayoutParams(lp);

                holder = new ViewHolder(view);

                break;
            case FOOTER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_plan_footer, null);
                view.setLayoutParams(lp);

                holder = new ViewFooterHolder(view);

                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (planDetail == null) {
            return;
        }
        if (position == 0) {
            final ViewHeaderHolder viewHolder = (ViewHeaderHolder) holder;


            if (!TextUtils.isEmpty(planDetail.getDueAmt())) {
                viewHolder.txt_dueAmt.setText(planDetail.getDueAmt());
            }
            viewHolder.layout_dueAmt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    openDialog(v.getContext(), planDetail);
                }
            });

            if (!TextUtils.isEmpty(planDetail.getSettledAmt())) {
                viewHolder.txt_settledAmt.setText(planDetail.getSettledAmt());
            }
            if (!TextUtils.isEmpty(planDetail.getPreAmt())) {
                viewHolder.txt_preAmt.setText(planDetail.getPreAmt());
            }
            if (!TextUtils.isEmpty(planDetail.getFundSource())) {
                viewHolder.txt_fundSource.setText(planDetail.getFundSource());
            }

//            if (!TextUtils.isEmpty(planDetail.getStatus())) {
//                viewHolder.txt_plan_status.setText(planDetail.getStatus());
//            }

//            00-	逾期
//            01-	未结清
//            02-	已结清

            if (!TextUtils.isEmpty(planDetail.getStatus())) {
                if ("00".equals(planDetail.getStatus())) {
                    viewHolder.txt_plan_status.setText("逾期");
                } else if ("01".equals(planDetail.getStatus())) {
                    viewHolder.txt_plan_status.setText("未结清");
                } else if ("02".equals(planDetail.getStatus())) {
                    viewHolder.txt_plan_status.setText("结清");
                }
            }

            if (!TextUtils.isEmpty(planDetail.getBankName())) {
                viewHolder.txt_bankName.setText(planDetail.getBankName() + "(" + planDetail.getCardNo() + ")");
            }

//            00: 未扣款,
//            01: 签约失败
//            02：扣款中
//            03：扣款成功
//            04：扣款失败
            viewHolder.layout_payStatus.setVisibility(View.GONE);

//            if (!TextUtils.isEmpty(planDetail.getPayStatus())) {
//                viewHolder.layout_payStatus.setVisibility(View.VISIBLE);
//                if ("00".equals(planDetail.getPayStatus())) {
//                    viewHolder.txt_payStatus.setText("未扣款");
//                } else if ("01".equals(planDetail.getPayStatus())) {
//                    viewHolder.txt_payStatus.setText("签约失败");
//                } else if ("02".equals(planDetail.getPayStatus())) {
//                    viewHolder.txt_payStatus.setText("扣款中");
//                } else if ("03".equals(planDetail.getPayStatus())) {
//                    viewHolder.txt_payStatus.setText("扣款成功");
//                } else if ("04".equals(planDetail.getPayStatus())) {
//                    viewHolder.txt_payStatus.setText("扣款失败");
//                }
//            }

            if (!TextUtils.isEmpty(planDetail.getStatus())) {
                if ("00".equals(planDetail.getStatus())) {
                    viewHolder.txt_plan_status.setText("逾期");
                } else if ("01".equals(planDetail.getStatus())) {
                    viewHolder.txt_plan_status.setText("未结清");
                } else if ("02".equals(planDetail.getStatus())) {
                    viewHolder.txt_plan_status.setText("结清");
                }
            }

//            if (!TextUtils.isEmpty(planDetail.getFailedReason())) {
//                viewHolder.layout_failedReason.setVisibility(View.VISIBLE);
//                viewHolder.txt_failedReason.setText(planDetail.getFailedReason());
//            } else {
//                viewHolder.layout_failedReason.setVisibility(View.GONE);
//            }

            viewHolder.layout_lastRepayDate.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(planDetail.getLastRepayDate())) {
                viewHolder.layout_lastRepayDate.setVisibility(View.VISIBLE);
                viewHolder.txt_lastRepayDate.setText(planDetail.getLastRepayDate());
            }

            if (IntentFlag.LEFT == (Integer) viewHolder.layout_plan_root.getTag()) {
                viewHolder.layout_root_right.setVisibility(View.GONE);
                viewHolder.layout_root_left.setVisibility(View.VISIBLE);
            } else {
                viewHolder.layout_root_right.setVisibility(View.VISIBLE);
                viewHolder.layout_root_left.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(planDetail.getRepaymentNotice())) {
                viewHolder.txt_instructions.setText(planDetail.getRepaymentNotice());
            }
            viewHolder.txt_repayment_instructions.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    start(Effectstype.RotateLeft, viewHolder.layout_plan_root);
                    viewHolder.layout_root_right.setVisibility(View.VISIBLE);
                    viewHolder.layout_root_left.setVisibility(View.GONE);
                    viewHolder.layout_plan_root.setTag(IntentFlag.RIGHT);
                }
            });

            viewHolder.txt_repayment_message.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    start(Effectstype.RotateLeft, viewHolder.layout_plan_root);
                    viewHolder.layout_root_right.setVisibility(View.GONE);
                    viewHolder.layout_root_left.setVisibility(View.VISIBLE);
                    viewHolder.layout_plan_root.setTag(IntentFlag.LEFT);
                }
            });
        } else if (position == dataList.size() + 1) {
            ViewFooterHolder viewholder = (ViewFooterHolder) holder;
            viewholder.txt_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity = (Activity) v.getContext();
                    activity.finish();
                }
            });

        } else {
            int pos = position - 1;
            final RepayPlan repayPlan = dataList.get(pos);
            ViewHolder viewholder = (ViewHolder) holder;
            if (!TextUtils.isEmpty(repayPlan.getDueDate())) {
                viewholder.txt_dueDate.setText(repayPlan.getDueDate());
            }
            if (!TextUtils.isEmpty(repayPlan.getInStmAmt())) {
                viewholder.txt_inStmAmt.setText(repayPlan.getInStmAmt());
            }
            Context context = viewholder.txt_plan_status.getContext();
            if (!TextUtils.isEmpty(repayPlan.getSetlInd())) {
                if ("01".equals(repayPlan.getSetlInd())) {
                    viewholder.txt_dueDate.setTextColor(context.getResources().getColor(R.color.black));
                    viewholder.txt_dueDate.setTextColor(context.getResources().getColor(R.color.black));
                    viewholder.txt_plan_status.setTextColor(context.getResources().getColor(R.color.btn_back_green));
                    viewholder.txt_plan_status.setText("已结清");
                } else if ("02".equals(repayPlan.getSetlInd())) {
                    viewholder.txt_dueDate.setTextColor(context.getResources().getColor(R.color.black));
                    viewholder.txt_dueDate.setTextColor(context.getResources().getColor(R.color.black));
                    viewholder.txt_plan_status.setTextColor(context.getResources().getColor(R.color.black));
//                    viewholder.txt_plan_status.setText("未结清");
                    viewholder.txt_plan_status.setText("待还款");
                } else if ("03".equals(repayPlan.getSetlInd())) {
                    viewholder.txt_dueDate.setTextColor(context.getResources().getColor(R.color.gray));
                    viewholder.txt_dueDate.setTextColor(context.getResources().getColor(R.color.gray));
                    viewholder.txt_plan_status.setTextColor(context.getResources().getColor(R.color.gray));
                    viewholder.txt_plan_status.setText("未出账");
                }
            }


            if (!TextUtils.isEmpty(repayPlan.getPsOdInd()) && ("Y".equals(repayPlan.getPsOdInd().toUpperCase()))) {
                viewholder.txt_plan_status.setText("已逾期" + repayPlan.getOvdueDays() + "天");
                viewholder.txt_plan_status.setTextColor(context.getResources().getColor(R.color.red_light));
                viewholder.img_list.setVisibility(View.VISIBLE);
            } else {
                viewholder.img_list.setVisibility(View.GONE);
            }

//            planDetail.setFailedReason("扣款失败问题坚强数据");
            if (!TextUtils.isEmpty(planDetail.getFailedReason()) && pos == 0) {
                viewholder.txt_fail_reason.setVisibility(View.VISIBLE);
            } else {
                viewholder.txt_fail_reason.setVisibility(View.GONE);
            }
            viewholder.txt_id.setText(String.valueOf(position));
            viewholder.txt_fail_reason.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAlert(v.getContext(), "扣款失败原因", planDetail.getFailedReason());
                }
            });

            viewholder.layout_detail_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(repayPlan.getPsOdInd()) && ("Y".equals(repayPlan.getPsOdInd().toUpperCase()))) {
                        openDialogV2(v.getContext(), repayPlan);
                    }
                }
            });
        }
    }


    /**
     * 提交数据
     *
     * @param
     * @param smsBody
     */
    protected void showAlert(Context context, String title, final String smsBody) {
        LogUtil.d("okhttp", smsBody);
        final HDAlertDialog dialog1 = HDAlertDialog.getInstance(context)
                .withTitle(title)
                .withHtmlMsg(smsBody);
        dialog1.withButton1Onclick("我知道了", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();
                    }
                }
        );
        dialog1.show();
    }

    private ListDialog dialog;

    private void openDialog(Context context, PlanDetail planDetail) {
        List<RepayDetail> repayDetailList = planDetail.getRepayDetailList();
//        if (repayDetailList == null || repayDetailList.size() == 0) {
//            return;
//        }

//        for (int i = 0; i < repayDetailList.size(); i++) {
//            RepayDetail repayDetail = repayDetailList.get(i);
//            dataList.add(repayDetail.getDetailPeriod() + "　　　" + repayDetail.getLateFee());
//        }
        dialog = new ListDialog(context, repayDetailList, "应还金额明细", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private ListV2Dialog dialogV2;

    private void openDialogV2(Context context, RepayPlan repayPlan) {
        List<NameValue> dataList = new ArrayList<>();
        String yuan = context.getResources().getString(R.string.yuan);
        dataList.add(new NameValue("本金", yuan + repayPlan.getInStmAmt()));
        dataList.add(new NameValue("利息", yuan + repayPlan.getPsFeeAmt()));
        dataList.add(new NameValue("逾期费用", yuan + repayPlan.getLateFee()));
        dialogV2 = new ListV2Dialog(context, dataList, "明细", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogV2.dismiss();
            }
        });
        dialogV2.show();
    }

    private void start(Effectstype type, View view) {

        BaseEffects animator = type.getAnimator();
        animator.setDuration(Math.abs(1000));
        animator.start(view);
    }

    @Override
    public int getItemCount() {
        return dataList.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER;
        } else if (position == dataList.size() + 1) {
            return FOOTER;
        } else {
            return ITEM;
        }
    }

    public static class ViewHeaderHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView txt_dueAmt;
        public TextView txt_settledAmt;
        public TextView txt_preAmt;
        public TextView txt_fundSource;
        public TextView txt_plan_status;
        public TextView txt_bankName;
        public TextView txt_payStatus;
        public TextView txt_failedReason;

        public TextView txt_lastRepayDate;
        public LinearLayout layout_lastRepayDate;


        public LinearLayout layout_dueAmt;
        public TextView txt_repayment_instructions;
        public LinearLayout layout_plan_root;

        public LinearLayout layout_root_right;
        public TextView txt_instructions;
        public TextView txt_repayment_message;
        public LinearLayout layout_root_left;
        public LinearLayout layout_failedReason;
        public LinearLayout layout_payStatus;


        public ViewHeaderHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.txt_repayment_instructions = (TextView) rootView.findViewById(R.id.txt_repayment_instructions);
            this.layout_dueAmt = (LinearLayout) rootView.findViewById(R.id.layout_dueAmt);
            this.txt_dueAmt = (TextView) rootView.findViewById(R.id.txt_dueAmt);
            this.txt_settledAmt = (TextView) rootView.findViewById(R.id.txt_settledAmt);
            this.txt_preAmt = (TextView) rootView.findViewById(R.id.txt_preAmt);
            this.txt_fundSource = (TextView) rootView.findViewById(R.id.txt_fundSource);
            this.txt_plan_status = (TextView) rootView.findViewById(R.id.txt_plan_status);
            this.txt_bankName = (TextView) rootView.findViewById(R.id.txt_bankName);
            this.txt_payStatus = (TextView) rootView.findViewById(R.id.txt_payStatus);
            this.txt_failedReason = (TextView) rootView.findViewById(R.id.txt_failedReason);
            this.layout_plan_root = (LinearLayout) rootView.findViewById(R.id.layout_plan_root);

            this.layout_plan_root.setTag(IntentFlag.LEFT);
            this.layout_root_left = (LinearLayout) rootView.findViewById(R.id.layout_root_left);
            this.layout_root_right = (LinearLayout) rootView.findViewById(R.id.layout_root_right);
            this.txt_instructions = (TextView) rootView.findViewById(R.id.txt_instructions);
            this.txt_repayment_message = (TextView) rootView.findViewById(R.id.txt_repayment_message);
            this.layout_failedReason = (LinearLayout) rootView.findViewById(R.id.layout_failedReason);
            this.layout_payStatus = (LinearLayout) rootView.findViewById(R.id.layout_payStatus);
            this.txt_lastRepayDate = (TextView) rootView.findViewById(R.id.txt_lastRepayDate);
            this.layout_lastRepayDate = (LinearLayout) rootView.findViewById(R.id.layout_lastRepayDate);
        }


    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView txt_id;
        public TextView txt_dueDate;
        public TextView txt_inStmAmt;
        public TextView txt_plan_status;
        public TextView txt_fail_reason;
        public ImageView img_list;
        public LinearLayout layout_detail_list;


        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.txt_id = (TextView) rootView.findViewById(R.id.txt_id);
            this.txt_dueDate = (TextView) rootView.findViewById(R.id.txt_dueDate);
            this.txt_inStmAmt = (TextView) rootView.findViewById(R.id.txt_inStmAmt);
            this.txt_plan_status = (TextView) rootView.findViewById(R.id.txt_plan_status);
            this.txt_fail_reason = (TextView) rootView.findViewById(R.id.txt_fail_reason);
            this.img_list = (ImageView) rootView.findViewById(R.id.img_list);
            this.layout_detail_list = (LinearLayout) rootView.findViewById(R.id.layout_detail_list);
        }

    }

    public static class ViewFooterHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView txt_submit;

        public ViewFooterHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.txt_submit = (TextView) rootView.findViewById(R.id.txt_submit);
        }

    }


}
