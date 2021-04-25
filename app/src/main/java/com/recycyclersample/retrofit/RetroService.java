package com.recycyclersample.retrofit;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.recycyclersample.utils.BaseView;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Khemraj on 31/12/2017.
 */
public class RetroService {
    public static final String TAG = "RetroService***";
    private ErrorListener errorListener;

    public interface SuccessListener<T> {
        void onSuccess(T result);
    }

    public interface ErrorListener {
        void onError(ErrorType error);
    }

    public RetroService setErrorListener(ErrorListener errorListener) {
        this.errorListener = errorListener;
        return this;
    }

    public <T> void call(Call<T> call, @Nullable SuccessListener<T> successListener) {
        this.call(null, call, successListener);
    }

    public <T> void call(@Nullable BaseView baseView, Call<T> call, @Nullable SuccessListener<T> successListener) {
        if (baseView != null)
            baseView.showProgressBar();
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (baseView != null) baseView.hideProgressBar();
                if (response.isSuccessful()) {
                    if (successListener != null)
                        successListener.onSuccess(response.body());
                } else {
                    ErrorType error = ErrorType.getErrorTypeByVolleyError(response.code());
                    if (error == null) return;
                    if (errorListener != null) {
                        errorListener.onError(error);
                    } else if (baseView != null) {
                        baseView.exception(new Exception(error.getMessage()));
                    }
                }
                try {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    if (response.isSuccessful())
                        Log.d(TAG, "onResponse: Success---------->" + gson.toJson(response.body()));
                    else if (!TextUtils.isEmpty(response.errorBody().string()))
                        Log.d(TAG, "onResponse: Error---------->" + gson.toJson(new JSONObject(response.errorBody().string())));
                } catch (Exception e) {
                    baseView.exception(e);
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                if (baseView != null) {
                    baseView.hideProgressBar();
                    baseView.exception(new Exception(t));
                }
                ErrorType errorType;
                if (t instanceof JsonSyntaxException) {
                    errorType = ErrorType.ParseError;
                } else {
                    errorType = ErrorType.Error;
                }
                if (errorListener != null)
                    errorListener.onError(errorType);
                else if (baseView != null) {
                    baseView.exception(new Exception(errorType.getMessage()));
                }
            }
        });
    }
}
