package dam.pmdm.spyrothedragon.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import dam.pmdm.spyrothedragon.R;
import dam.pmdm.spyrothedragon.databinding.FinalGuideBinding;

public class FinalGuideFragment extends Fragment {

    private FinalGuideBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FinalGuideBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnStartApp.setOnClickListener(this::pasonave);
    }

    private void pasonave(View view) {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.action_finalGuideFragment_to_navigation_characters);
    }
}
