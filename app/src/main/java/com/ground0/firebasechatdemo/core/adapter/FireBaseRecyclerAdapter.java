package com.ground0.firebasechatdemo.core.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by zer0 on 5/10/16.
 */

public abstract class FireBaseRecyclerAdapter<T1, T2 extends RecyclerView.ViewHolder>
    extends RecyclerView.Adapter<T2> {

  Class<T1> modelClass;
  List<T1> data;
  List<String> keys;
  Query query;
  Inflater inflater;
  ChildEventListener dataChangeListener;

  public FireBaseRecyclerAdapter(Query query, Class<T1> modelClass) {
    this.modelClass = modelClass;
    this.query = query;
    this.data = new ArrayList<T1>();
    this.keys = new ArrayList<>();

    //Register listener for data set changes in the database
    dataChangeListener = this.query.addChildEventListener(new ChildEventListener() {
      @Override public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {

        T1 dataItem = dataSnapshot.getValue(FireBaseRecyclerAdapter.this.modelClass);
        String key = dataSnapshot.getKey();

        // Insert into the correct location, based on previousChildName
        if (previousChildName == null) {
          data.add(0, dataItem);
          keys.add(0, key);
        } else {
          int previousIndex = keys.indexOf(previousChildName);
          int nextIndex = previousIndex + 1;
          if (nextIndex == data.size()) {
            data.add(dataItem);
            keys.add(key);
          } else {
            data.add(nextIndex, dataItem);
            keys.add(nextIndex, key);
          }
        }

        notifyDataSetChanged();
      }

      @Override public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
        // One of the data  changed. Replace it in our list and name mapping
        String key = dataSnapshot.getKey();
        T1 newDataItem = dataSnapshot.getValue(FireBaseRecyclerAdapter.this.modelClass);
        int index = keys.indexOf(key);
        data.set(index, newDataItem);

        notifyDataSetChanged();
      }

      @Override public void onChildRemoved(DataSnapshot dataSnapshot) {
        //Child removed from the database, identify the key of the item and remove the same from data
        String key = dataSnapshot.getKey();
        int index = keys.indexOf(key);
        data.remove(index);
        keys.remove(index);

        notifyDataSetChanged();
      }

      @Override public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
        //Child's location changed in the db, identify the key of the item move the dataItem to the corresponding location
        String key = dataSnapshot.getKey();
        T1 modifiedDataItem = dataSnapshot.getValue(FireBaseRecyclerAdapter.this.modelClass);
        int index = keys.indexOf(key);
        data.remove(index);
        keys.remove(key);

        if (previousChildName == null) {
          data.add(0, modifiedDataItem);
          keys.add(0, key);
        } else {
          int newIndex = keys.indexOf(previousChildName) + 1;
          if (newIndex == data.size()) {
            data.add(modifiedDataItem);
            data.add(modifiedDataItem);
          } else {
            data.add(newIndex, modifiedDataItem);
            data.add(newIndex, modifiedDataItem);
          }
        }

        notifyDataSetChanged();
      }

      @Override public void onCancelled(FirebaseError firebaseError) {
        Log.e(getClass().getSimpleName(),
            "Listener either failed at the server, or is removed as a result of the security and Firebase rules.");
      }
    });
  }

  @Override public int getItemCount() {
    return data.size();
  }

  @Nullable public T1 getItem(int position) {
    if (position < data.size()) {
      return data.get(position);
    } else {
      return null;
    }
  }

  public void cleanup() {
    query.removeEventListener(dataChangeListener);
    data.clear();
    keys.clear();
  }
}
