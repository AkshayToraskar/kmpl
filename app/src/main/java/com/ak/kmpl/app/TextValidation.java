package com.ak.kmpl.app;

import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

/**
 * Created by WDIPL44 on 3/18/2016.
 */
public class TextValidation {


    Activity mAct;
    public TextValidation(Activity mAct) {
        this.mAct = mAct;
    }
    public boolean validateText(EditText et, String errorMess, boolean email) {
        if (email) {


            if (et.getText().toString().isEmpty() || !isValidEmail(et.getText().toString())) {
                et.setError(errorMess);
                requestFocus(et);
                return false;
            } else {
                //et.setErrorEnabled(false);
            }
            return true;
        } else {
            if (et.getText().toString().isEmpty()) {
                et.setError(errorMess);
                requestFocus(et);
                return false;
            } else {
                //et.setErrorEnabled(false);
            }
            return true;
        }
    }


    public boolean validateTILText(EditText et, TextInputLayout til, String errorMess, boolean email) {
        if (email) {


            if (et.getText().toString().isEmpty() || !isValidEmail(et.getText().toString())) {
                //et.setError(errorMess);
                til.setErrorEnabled(true);
                til.setError(errorMess);
                requestFocus(et);
                return false;
            } else {
                //et.setErrorEnabled(false);
            }
            return true;
        } else {
            if (et.getText().toString().isEmpty()) {
                //et.setError(errorMess);
                til.setErrorEnabled(true);
                til.setError(errorMess);
                requestFocus(et);
                return false;
            } else {
                //et.setErrorEnabled(false);
            }
            return true;
        }
    }


    public boolean validateEmail(EditText etEmailval) {
        String email = etEmailval.getText().toString().trim();
        //for empty user mail
        if (email.isEmpty()) {
            etEmailval.setError("Enter Email");
            requestFocus(etEmailval);
            return false;
        }
        //for valid
        else if (!isValidEmail(email)) {
            etEmailval.setError("Enter valid Email");
            requestFocus(etEmailval);
            return false;
        } else {
            //etEmail.setErrorEnabled(false);

        }

        return true;
    }

    public boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            mAct.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

   /* private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }*/

}

