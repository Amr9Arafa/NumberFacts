package com.example.amrarafa.numberfacts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Created by amr arafa on 7/20/2016.
 */
public class MyDialogFragment extends DialogFragment{

   public MyDialogFragment(){
   }

    public interface Callback {

        public void onNumberSelected(String choosenNumber);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alert= new AlertDialog.Builder(getActivity());
        final EditText editText=new EditText(getActivity());
        alert.setTitle("Choose Number")
                .setView(editText)
                .setMessage("Choose Number")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        String choosenNumber=editText.getText().toString();

                        ((Callback) getActivity()).onNumberSelected(choosenNumber);

                        getDialog().dismiss();
                    }
                });


        return alert.create();


    }
}
