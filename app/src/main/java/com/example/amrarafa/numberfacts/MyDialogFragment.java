package com.example.amrarafa.numberfacts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


/**
 * Created by amr arafa on 7/20/2016.
 */
public class MyDialogFragment extends DialogFragment{

   public MyDialogFragment(){
   }

    public interface Callback {

        public void onNumberSelected(int choosenNumber);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {



        AlertDialog.Builder alert= new AlertDialog.Builder(getActivity());
        alert.setTitle("Choose Type")
             .setItems(R.array.type_array, new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) {

                     ((Callback) getActivity()).onNumberSelected(i);


                 }
             })   ;



        return alert.create();


    }
}
