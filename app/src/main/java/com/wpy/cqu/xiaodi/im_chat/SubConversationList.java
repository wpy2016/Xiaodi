package com.wpy.cqu.xiaodi.im_chat;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.wpy.cqu.xiaodi.R;

import io.rong.imkit.fragment.SubConversationListFragment;

public class SubConversationList extends FragmentActivity {

    private SubConversationListFragment subConversationListFragment;

    private TextView mtvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_conversation_list);

        subConversationListFragment = (SubConversationListFragment) getSupportFragmentManager().findFragmentById(R.id.subconversationlist);
        mtvContent = subConversationListFragment.getView().findViewById(R.id.id_top_tv_content);

        mtvContent.setText(getResources().getString(R.string.system_msg));
    }
}
