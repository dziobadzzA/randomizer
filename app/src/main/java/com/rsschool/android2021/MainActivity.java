package com.rsschool.android2021;

import android.os.Bundle;
import android.os.PersistableBundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.rsschool.android2021.model.Data;
import com.rsschool.android2021.model.StateFragment;

import org.jetbrains.annotations.NotNull;


public class MainActivity extends AppCompatActivity implements SecondFragment.GetMessageResult, FirstFragment.SendMessageResult {


   private StateFragment firstGlobalFragment = new StateFragment("firstFragment");
   public static StateFragment secondGlobalFragment = new StateFragment("secondFragment");

   private Data data = new Data(0, 0);
   private int result;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openFirstFragment(0);
    }

    public void openFirstFragment(int previousNumber) {
        final Fragment firstFragment = FirstFragment.newInstance(previousNumber);
        runOpenFragment(firstFragment, firstGlobalFragment);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void openSecondFragment(int min, int max) {
            final Fragment secondFragment = SecondFragment.newInstance(min, max);
            runOpenFragment(secondFragment, secondGlobalFragment);
    }

    private void runOpenFragment(Fragment fragment, StateFragment stateFragment) {

        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (!stateFragment.checkStateFragment()) {
            transaction.add(R.id.container, fragment, stateFragment.getName());
            stateFragment.updateStateFragment();
        }

        if (stateFragment.checkStateFragment()) {
            transaction.replace(R.id.container, fragment, stateFragment.getName());
        }

        transaction.commit();
    }

    @Override
    public void onBackPressed() {

        if (firstGlobalFragment.checkStateFragment()) {

            FirstFragment myFragment = (FirstFragment) getSupportFragmentManager().findFragmentByTag(firstGlobalFragment.getName());

            if (myFragment == null)
                openFirstFragment(result);
            else if (!myFragment.isVisible())
                openFirstFragment(result);
        }


    }

    @Override
    public void onGetMessageResult(int result) {
        this.result = result;
    }


    @Override
    public int onSendMessageResult() {
        return this.result;
    }

    @Override
    public void onGetMessageData(@NotNull Data data) {
        this.data.setMin(data.getMin());
        this.data.setMax(data.getMax());
    }

    @NotNull
    @Override
    public Data onSendMessageData() {
        return new Data(this.data.getMin(), this.data.getMax());
    }

}


