package com.mujirenben.android.mine.di.component;

import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.dagger.scope.FragmentScope;
import com.mujirenben.android.mine.di.module.MateriaModule;
import com.mujirenben.android.mine.mvp.ui.fragment.MateriaWxCirclesFragment;
import com.mujirenben.android.mine.mvp.ui.fragment.MateriaWxFriendsFragment;

import dagger.Component;

@FragmentScope
@Component(modules = MateriaModule.class, dependencies = AppComponent.class)
public interface MateriaComponent {
    void inject(MateriaWxFriendsFragment friendsFragment);
    void inject(MateriaWxCirclesFragment wxCirclesFragment);
}