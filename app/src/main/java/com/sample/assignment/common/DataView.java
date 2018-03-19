package com.sample.assignment.common;


import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.TextView;

import com.sample.assignment.utils.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

public interface DataView extends ViewHandler {

    default void setText(View rootView, @IdRes int id, String text) {
        this.<TextView>findById(rootView, id).setText(text);
    }

    default void hideShow(View rootView, @IdRes int hide, @IdRes int show) {
        findById(rootView, hide).setVisibility(View.GONE);
        findById(rootView, show).setVisibility(View.VISIBLE);
    }

    default <ViewModel extends AndroidViewModel> Class<ViewModel> getViewModelClass() {
        return (Class<ViewModel>) genericType(AndroidViewModel.class);
    }

    default Type genericType(Class classType) {
        Class clazz = getClass();
        do {
            while (clazz != null && !(clazz.getGenericSuperclass() instanceof ParameterizedType)) {
                clazz = clazz.getSuperclass();
            }

            if (clazz == null) {
                throw new RuntimeException("Your activity/fragment needs to declare viewModel. If not required then add VoidViewModel");
            }

            Type[] arguments = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments();

            Type type = CollectionUtils.first(Arrays.asList(arguments),
                    argument -> (argument instanceof Class) && (classType.isAssignableFrom((Class) argument)));
            if (type != null) {
                return type;
            }
        } while ((clazz = clazz.getSuperclass()) != null);

        return null;
    }

    void loading(boolean show);

    void onError(Throwable throwable);
}
