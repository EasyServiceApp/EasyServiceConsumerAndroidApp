package com.service.easyservice.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.service.easyservice.MyRequestDetailsActivity;
import com.service.easyservice.R;
import com.service.easyservice.models.Myrequest;
import com.service.easyservice.models.RequestServiceListResponse;
import com.service.easyservice.util.AppPreferences;
import com.service.easyservice.util.CommonFunctions;
import com.service.easyservice.volley.ResponseResult;
import com.service.easyservice.volley.VolleyJSONCaller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.service.easyservice.util.Constants.REQUEST_SERVICE_LIST_URL;

/**
 * Created by Smile on 28-03-2017.
 */

public class MyRequest extends Fragment implements ResponseResult, AdapterView.OnItemClickListener {


    Context activityContext;
    private List<Myrequest> applianceList;
    ListView lvMyService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_my_service, container, false);
        activityContext = getContext();
        lvMyService = (ListView)layout.findViewById(R.id.lvMyService);
        lvMyService.setOnItemClickListener(this);
        applianceList = new ArrayList<>();

        lvMyService.setAdapter(new AppointmentAdapter(activityContext, applianceList));
        getMyRequestDetais();


        return layout;

    }

    private void getMyRequestDetais() {

        if(CommonFunctions.isNetworkAvailable(activityContext)) {
            //request for OTP
            Map<String, String> addApplianceDetailsParameters = new HashMap<>();
            addApplianceDetailsParameters.put("appapi", "yes");
            addApplianceDetailsParameters.put("user_id", new AppPreferences(activityContext).getUserInfo().getUserId());
            new VolleyJSONCaller(this, REQUEST_SERVICE_LIST_URL, addApplianceDetailsParameters, Request.Method.POST, false).execute();
        }
        else
        {
            CommonFunctions.displayDialog(activityContext,getString(R.string.internet_problem));
        }
    }

    @Override
    public void responseResult(Object result) {
        if(result instanceof RequestServiceListResponse)
        {
            RequestServiceListResponse requestServiceListResponse = (RequestServiceListResponse)result;

            if("1".equals(requestServiceListResponse.getStatus()))
            {
                if(requestServiceListResponse.getMyrequest() != null && requestServiceListResponse.getMyrequest().size() > 0) {
                    applianceList.addAll(requestServiceListResponse.getMyrequest());
                    ((AppointmentAdapter) lvMyService.getAdapter()).notifyDataSetChanged();
                }
            }
            else
            {
                CommonFunctions.displayDialog(getContext(),requestServiceListResponse.getMessage());
            }


        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Gson gson = new Gson();
        Type type = new TypeToken<Myrequest>() {}.getType();

        Intent intent = new Intent(activityContext, MyRequestDetailsActivity.class);
        intent.putExtra("myrequest",gson.toJson(lvMyService.getAdapter().getItem(i),type));
        startActivity(intent);

    }

    private class AppointmentAdapter extends ArrayAdapter<Myrequest> {

        public List<Myrequest> alAppointment;
        Context context;
        ViewHolder holder;

        public AppointmentAdapter(Context context, List<Myrequest> alAppointment) {
            super(context, R.layout.my_service_item,alAppointment);
            this.alAppointment = alAppointment;
            this.context = context;
        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater;
            inflater = ((Activity)context).getLayoutInflater();
            View rowView = convertView;

            Myrequest appointment = getItem(position);

            if (rowView != null) {
                holder = (ViewHolder)rowView.getTag();
            }
            else
            {
                rowView = inflater.inflate(R.layout.my_service_item, null, true);
                holder = new ViewHolder();

                holder.tvModel = (TextView)rowView.findViewById(R.id.tvModel);
                holder.tvBrand = (TextView)rowView.findViewById(R.id.tvBrand);
                holder.tvBookingDate = (TextView)rowView.findViewById(R.id.tvBookingDate);

                rowView.setTag(holder);
            }

            holder.tvModel.setText(appointment.getModel());
            holder.tvBrand.setText(appointment.getBrand());
            holder.tvBookingDate.setText("Booking Date \n"+appointment.getRequestDate());

            return rowView;
        }


        private class ViewHolder
        {
            TextView tvModel,tvBrand,tvBookingDate;
        }
    }

}