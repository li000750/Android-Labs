package com.cst2335.li000750;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.Arrays;

public class ChatRoomActivity extends AppCompatActivity {

    private static final String TAG = "ChatRoomActivity";//print log
    MyOpenHelper myOpener;
    SQLiteDatabase theDatabase;

    //constructor Messages
    public static class Messages {
        String inputMessage;
        public Boolean sendOrReceive;
        long id;

        public Messages(String inMessage, Boolean choiceSendOrReceive, long _id) {
            this.inputMessage = inMessage;
            this.sendOrReceive = choiceSendOrReceive;
            this.id = _id;
        }
        public long getId() {
            return id;
        }

        @NonNull
        @Override
        public String toString() {
            return "Message="+ inputMessage+",SendOrReceive="+sendOrReceive+",id="+id;
        }
    }
    ArrayList<Messages> myMessages = new ArrayList<>();

    MyListAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        myOpener = new MyOpenHelper( this );
        //open the database:
        theDatabase = myOpener.getWritableDatabase();
        Cursor results = theDatabase.rawQuery( "Select * from " + MyOpenHelper.TABLE_NAME + ";", null );
        int idIndex = results.getColumnIndex( MyOpenHelper.COL_ID );
        int  messageIndex = results.getColumnIndex( MyOpenHelper.COL_MESSAGE);
        int sOrRIndex = results.getColumnIndex( MyOpenHelper.COL_SEND_RECEIVE);

        //cursor is pointing to row -1
        while( results.moveToNext() ) //returns false if no more data
        { //pointing to row 2
            int id = results.getInt(idIndex);
            String message = results.getString( messageIndex );
            boolean sendOrRecieve = results.getInt(sOrRIndex)==1;

            //add to arrayList:
            myMessages.add( new Messages( message,sendOrRecieve, id ));
        }

        //attach listview to adapter
        ListView myList = findViewById(R.id.listView);
        //ListAdapter myAdapter = new Adapter();
        //myList.setAdapter(myAdapter);
        myList.setAdapter(myAdapter = new MyListAdapter());

        //input text
        EditText editText = findViewById(R.id.editText);

        //send message operations
        Button sendButton = findViewById(R.id.button1);
        sendButton.setOnClickListener( click -> {
            String inputText = editText.getText().toString();
            ContentValues newRow = new ContentValues();
            //Message column:
            newRow.put( MyOpenHelper.COL_MESSAGE , inputText );
            //Send or receive column:
            newRow.put(MyOpenHelper.COL_SEND_RECEIVE, 1);
            //now that columns are full, you insert:
            long id = theDatabase.insert( MyOpenHelper.TABLE_NAME, null, newRow );

            Log.i(TAG, "Adding a sending row");
            myMessages.add(new Messages(inputText,true,id));
            editText.setText("");
            myAdapter.notifyDataSetChanged();
        });
        //receive message operations
        Button receiveButton = findViewById(R.id.button2);
        receiveButton.setOnClickListener( click -> {
            String inputText = editText.getText().toString();
            ContentValues newRow = new ContentValues();
            //Message column:
            newRow.put( MyOpenHelper.COL_MESSAGE , inputText );
            //Send or receive column:
            newRow.put(MyOpenHelper.COL_SEND_RECEIVE, 0);
            //now that columns are full, you insert:
            long id = theDatabase.insert( MyOpenHelper.TABLE_NAME, null, newRow );
            Log.i(TAG, "Adding a receiving row");
            myMessages.add(new Messages(inputText,false,id));
            editText.setText("");
            myAdapter.notifyDataSetChanged();
        });

        myList.setOnItemLongClickListener( (p, b, pos, id) -> {
            Messages whatWasClicked = myMessages.get(pos);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Do you want to delete this?")

                    .setIcon(0)

                    //What is the message:
                    .setMessage("The selected row is:"+ pos+"\n"+"The database id is:"+id)
                    //what the Yes button does:
                    .setPositiveButton("Yes", (click, arg) -> {
                        myMessages.remove(pos);
                        myAdapter.notifyDataSetChanged();
                        theDatabase.delete(MyOpenHelper.TABLE_NAME,MyOpenHelper.COL_ID+"=?",new String[]{Long.toString(whatWasClicked.getId())});
                    })
                    //What the No button does:
                    .setNegativeButton("No", (click, arg) -> { })

                    //An optional third button:
                    //.setNeutralButton("Maybe", (click, arg) -> {  })


                    //You can add extra layout elements:
                    .setView(getLayoutInflater().inflate(R.layout.row_layout_receive, null) )

                    //Show the dialog
                    .create().show();

            return true;
        });
        printCursor(results,1);

    }

    public void printCursor(Cursor c, int inVersion){
        int version = inVersion;
        int columnNumber = c.getColumnCount();
        String[] columnNames = c.getColumnNames();
        int rowNumber = c.getCount();
        ArrayList<Messages> cursorRowValuesList = new ArrayList<>();
        //String cursorRowValues;

        c.moveToFirst();

           while (!c.isAfterLast()){
           int messageColIndex = c.getColumnIndex(MyOpenHelper.COL_MESSAGE);
           int sOrRColIndex = c.getColumnIndex(MyOpenHelper.COL_SEND_RECEIVE);
           int idColIndex = c.getColumnIndex(MyOpenHelper.COL_ID);

            String message = c.getString(messageColIndex);
            int sOrR = c.getInt(sOrRColIndex);
            long id = c.getLong(idColIndex);

            String row=String.format("Message="+message+",SendOrReceive="+sOrR+",id="+id);
            Log.i("ROW VALUES", row);

            c.moveToNext();}


        Log.i("DATABASE VERSION", Integer.toString(version));
        Log.i("NUMBER OF COLUMNS", Integer.toString(columnNumber));
        Log.i("COLUMN NAMES", Arrays.toString(columnNames));
        Log.i("NUMBER OF ROWS", Integer.toString(rowNumber));
        //Log.i("ROW VALUES", cursorRowValues);

    }

    public class MyListAdapter extends BaseAdapter {

        public int getCount() { return myMessages.size();}

        public Object getItem(int position) { return myMessages.get(position).inputMessage; }

        public long getItemId(int position) { return (long) position; }

        public View getView(int position, View old, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();

            //make a new row:
            if (myMessages.get(position).sendOrReceive){
                View newView1 = inflater.inflate(R.layout.row_layout_send, parent, false);
                //set what the text should be for this row:
                EditText editText1 = newView1.findViewById(R.id.sendText);
                editText1.setText( getItem(position).toString() );

                //return it to be put in the table
                return newView1;
            }
            else  {
                View newView2 = inflater.inflate(R.layout.row_layout_receive, parent, false);
                //set what the text should be for this row:
                EditText editText2 = newView2.findViewById(R.id.receiveText);
                editText2.setText(getItem(position).toString());

                //return it to be put in the table
                return newView2;
            }
        }
    }




}