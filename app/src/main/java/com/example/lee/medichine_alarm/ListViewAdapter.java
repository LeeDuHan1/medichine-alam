package com.example.lee.medichine_alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Calendar;

public class ListViewAdapter extends BaseAdapter{
    private Context mContext;
    static public ArrayList<ListData> mListData = new ArrayList<ListData>();

    public ListViewAdapter(Context context){
        super();
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
      public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_layout, null);

            holder.mIcon = (ImageView) convertView.findViewById(R.id.mImage);
            holder.mText = (TextView) convertView.findViewById(R.id.mText);
            holder.method = (TextView) convertView.findViewById(R.id.method);
            holder.alarmSwitch = (Switch) convertView.findViewById(R.id.alarmSwitch);
            Animation animation = AnimationUtils.loadAnimation(this.mContext, R.anim.left);
            convertView.startAnimation(animation);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.alarmSwitch.setFocusable(false); // 리스트뷰 안눌리는거 해결
        final ListData mData = mListData.get(position);

        if (mData.mIcon != null) {
            holder.mIcon.setVisibility(View.VISIBLE);
            holder.mIcon.setImageDrawable(mData.mIcon);
        } else {
            holder.mIcon.setVisibility(View.VISIBLE);
            holder.mIcon.setImageResource(R.drawable.emty);
        }

        holder.mText.setText(mData.mTitle);
        holder.method.setText(mData.method);
        holder.alarmSwitch.setChecked(mData.alarmSwitch);

        Switch swc = (Switch)convertView.findViewById(R.id.alarmSwitch);
        swc.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton cb, boolean isChecking) {
                String str = mListData.get(position).mTitle;

                // 상태가 on, off인 경우에 알맞게 토스트를 띄움
                if(isChecking) {
                    AlarmActivity.setting = true;
                    Intent i = new Intent(mContext,AlarmActivity.class);
                    i.putExtra("name",mListData.get(position).mTitle+"");
                    i.putExtra("hour", mListData.get(position).hour1 + "");
                    i.putExtra("minute", mListData.get(position).minute1 + "");
                    i.putExtra("hour2", mListData.get(position).hour2 + "");
                    i.putExtra("minute2", mListData.get(position).minute2 + "");
                    i.putExtra("hour3", mListData.get(position).hour3 + "");
                    i.putExtra("minute3", mListData.get(position).minute3 + "");
                    i.putExtra("hour4", mListData.get(position).hour4 + "");
                    i.putExtra("minute4", mListData.get(position).minute4 + "");
                    i.putExtra("hour5", mListData.get(position).hour5 + "");
                    i.putExtra("minute5", mListData.get(position).minute5 + "");
                    mContext.startActivity(i);
                    Toast.makeText(mContext,str+" 알람설정", Toast.LENGTH_SHORT).show();
                }
                else if(!isChecking) {
//                    AlarmActivity.setting = false;
//                    Intent i = new Intent(mContext, AlarmActivity.class);
//                    i.putExtra("hour", mListData.get(position).hour1 + "");
//                    i.putExtra("minute", mListData.get(position).minute1 + "");
//                    i.putExtra("hour2", mListData.get(position).hour2 + "");
//                    i.putExtra("minute2", mListData.get(position).minute2 + "");
//                    i.putExtra("hour3", mListData.get(position).hour3 + "");
//                    i.putExtra("minute3", mListData.get(position).minute3 + "");
//                    i.putExtra("hour4", mListData.get(position).hour4 + "");
//                    i.putExtra("minute4", mListData.get(position).minute4 + "");
//                    i.putExtra("hour5", mListData.get(position).hour5 + "");
//                    i.putExtra("minute5", mListData.get(position).minute5 + "");
//
//                    mContext.startActivity(i);
                    Toast.makeText(mContext, str + " 알람해제", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }

    public void addItem(Drawable icon, String mTitle, String method, Boolean alarmSwitch, Integer hour1, Integer minute1,Integer hour2, Integer minute2,Integer hour3, Integer minute3,Integer hour4, Integer minute4,Integer hour5, Integer minute5,Integer afterMinute){
        ListData addInfo = new ListData();
        addInfo.mIcon = icon;
        addInfo.mTitle = mTitle;
        addInfo.method = method;
        addInfo.alarmSwitch = alarmSwitch;
        addInfo.hour1 = hour1;
        addInfo.minute1 = minute1;
        addInfo.hour2 = hour2;
        addInfo.minute2 = minute2;
        addInfo.hour3 = hour3;
        addInfo.minute3 = minute3;
        addInfo.hour4 = hour4;
        addInfo.minute4 = minute4;
        addInfo.hour5 = hour5;
        addInfo.minute5 = minute5;
        addInfo.afterMinute = afterMinute;
        mListData.add(addInfo);
    }

    public void remove(int position){
        mListData.remove(position);

    }

}
