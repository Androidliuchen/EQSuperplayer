package com.eq.EQSuperPlayer.fargament;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.eq.EQSuperPlayer.R;
import com.eq.EQSuperPlayer.adapter.EquitAdapter;
import com.eq.EQSuperPlayer.adapter.ProgramAdapter;
import com.eq.EQSuperPlayer.bean.Areabean;
import com.eq.EQSuperPlayer.communication.FindScreenThread;
import com.eq.EQSuperPlayer.communication.InterfaceConnect;
import com.eq.EQSuperPlayer.dao.AreabeanDao;

import java.util.ArrayList;
import java.util.List;

public class EquitmentFragment extends Fragment {
    private ListView mListView;
    private EquitAdapter equitAdapter;
    private List<Areabean> areabeans = new ArrayList<>();
    private ProgressDialog proDialog; // 进度条
    public static boolean indexChang = false;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            equitAdapter = new EquitAdapter(getActivity(), areabeans);
            mListView.setAdapter(equitAdapter);
            proDialog.dismiss();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equitment, container, false);
        proDialog = android.app.ProgressDialog.show(getActivity(), null,
                getResources().getString(R.string.hint_start_equit));
        FindScreenThread findScreenThread = new FindScreenThread(new InterfaceConnect() {
            @Override
            public void success(byte[] result) {
                List<Areabean> areabeanList = new AreabeanDao(getActivity()).getListAll();
                if (areabeanList.size() <= 0) {
                    mHandler.sendEmptyMessage(0);
                } else {
                    indexChang = true;
                    areabeans.add(areabeanList.get(0));
                    mHandler.sendEmptyMessage(0);
                }
            }

            @Override
            public void failure(int stateCode) {

            }

            @Override
            public void dataSuccess(String str) {

            }
        }, getActivity());
        mListView = (ListView) view.findViewById(R.id.equit_itme);
        new Thread(findScreenThread).start();
//            new AlertDialog.Builder(getActivity())
//                    .setTitle("列表框")
//                    .setItems(new String[]{"直连", "集合"}, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            switch (which){
//                                case 0:
//                                    intent = new Intent();
//                                    intent.setClass(getActivity(), WifiActivity.class);
//                                    startActivity(intent);
//                                    break;
//                                case 1:
//                                    intent = new Intent();
//                                    intent.setClass(getActivity(), RadioActivity.class);
//                                    startActivity(intent);
//                                    break;
//                            }
//                        }
//                    }).show();
        return view;
    }

}