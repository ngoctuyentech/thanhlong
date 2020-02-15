package vn.techlifegroup.thanhlong.ui.menufragment2;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.techlifegroup.thanhlong.R;

public class MenuFragment2 extends Fragment {

    private MenuFragment2ViewModel mViewModel;

    public static MenuFragment2 newInstance() {
        return new MenuFragment2();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.menu_fragment2_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MenuFragment2ViewModel.class);
        // TODO: Use the ViewModel
    }

}
