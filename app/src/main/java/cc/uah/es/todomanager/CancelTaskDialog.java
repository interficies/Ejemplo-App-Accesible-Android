package cc.uah.es.todomanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import cc.uah.es.todomanager.domain.TaskList;

/**
 * Created by Fjest on 11/09/2017.
 */

public class CancelTaskDialog extends DialogFragment{
    private static final String TAG = "CancelTaskDialog";

    private TaskList.Task task;
    private int position;
    private CancelDialogListener listener;

    public CancelTaskDialog(TaskList.Task task, int position) {
        this.task = task;
        this.position = position;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (CancelDialogListener) activity;
        } catch (ClassCastException cce) {
            Log.e(TAG, "The activity must be a CancelDialogListener instance.");
            throw new ClassCastException(activity.toString());
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setMessage(String.format(getResources().getString(R.string.cancel_task_dialog_message), task.getName()))
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        task.cancel();
                        listener.onCancel(position);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Nothing to do.
                    }
                })
                .create();
    }

    public static interface  CancelDialogListener {
        void onCancel(int position);
    }
}
