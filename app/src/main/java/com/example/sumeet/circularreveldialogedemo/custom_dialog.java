package com.example.sumeet.circularreveldialogedemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Sumeet on 02-04-2017.
 */

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class custom_dialog extends DialogFragment {
    TextView heading,text;
    Button ok;
    Dialog dialog;
    LinearLayout mylayout;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final View dialogView = View.inflate(getActivity(), R.layout.custom_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView);

        init(dialogView);
        dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                revealShow(dialogView, true, null);
            }
        });
        dialogView.findViewById(R.id.button_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revealShow(dialogView, false, dialog);
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        setCancelable(false);
        return dialog;
    }
    public void init(View view)
    {
        heading=(TextView)view.findViewById(R.id.textview_heading);
        text=(TextView)view.findViewById(R.id.textview_text);
        ok=(Button)view.findViewById(R.id.button_ok);
        mylayout=(LinearLayout)view.findViewById(R.id.circular_reveal_dialog);

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void revealShow(View rootView, boolean reveal, final Dialog dialog) {
        final View view = rootView.findViewById(R.id.circular_reveal_dialog);
        int w = view.getWidth();
        int h = view.getHeight();
        //float maxRadius = (float) Math.sqrt(w * w / 4 + h * h / 4);
        int radius = Math.max(view.getWidth(), view.getHeight());

        if(reveal){
            Animator revealAnimator = ViewAnimationUtils.createCircularReveal(view,
                    w/2 , h/2 , 0, radius);
            view.setVisibility(View.VISIBLE);
            revealAnimator.start();
        } else {
            Animator anim = ViewAnimationUtils.createCircularReveal(view, w/2 , h/2 , radius, 0);

            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    dialog.dismiss();
                    view.setVisibility(View.INVISIBLE);
                }
            });
            anim.start();
        }
    }

}
