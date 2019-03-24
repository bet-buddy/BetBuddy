package e.iot.betbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import e.iot.betbuddy.adapters.GroupAdapter;
import e.iot.betbuddy.data.DataHolder;
import e.iot.betbuddy.model.Group;
import e.iot.betbuddy.model.User;

public class GroupFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);

        ListView groupListView = view.findViewById(R.id.group_ListView);
        GroupAdapter groupAdapter  = new GroupAdapter(this.getActivity());
        groupListView.setAdapter(groupAdapter);

        groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = (User)(DataHolder.getInstance().retrieve("user"));
                Group group = user.getGroupList().get(position);
                DataHolder.getInstance().save("group",group);
                startActivity(new Intent(getActivity(), MessageActivity.class));
            }
        });

        return view;
    }
}
