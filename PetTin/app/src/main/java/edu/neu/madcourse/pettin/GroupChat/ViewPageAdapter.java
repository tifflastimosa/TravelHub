package edu.neu.madcourse.pettin.GroupChat;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.security.acl.Group;

import edu.neu.madcourse.pettin.GroupChat.Fragments.ChatsFragment.ChatsFragment;
import edu.neu.madcourse.pettin.GroupChat.Fragments.GroupChatsFragment.GroupChatsFragment;

// this is where data is fed
public class ViewPageAdapter extends FragmentStateAdapter {

    public ViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ChatsFragment();
            case 1:
                return new GroupChatsFragment();
            default:
                return new ChatsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}